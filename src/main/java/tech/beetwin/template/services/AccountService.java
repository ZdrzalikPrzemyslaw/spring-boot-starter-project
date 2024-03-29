package tech.beetwin.template.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tech.beetwin.template.dto.request.AuthenticationRequestDTO;
import tech.beetwin.template.dto.request.RefreshTokenRequestDTO;
import tech.beetwin.template.dto.response.AuthenticationResponseDTO;
import tech.beetwin.template.common.I18nCodes;
import tech.beetwin.template.exceptions.TokenException;
import tech.beetwin.template.security.UserDetailsImpl;
import tech.beetwin.template.security.UserDetailsServiceImpl;
import tech.beetwin.template.utils.AuthJWTUtils;
import tech.beetwin.template.dto.request.EditUserInfoDTO;
import tech.beetwin.template.dto.request.RegisterAccountDTO;
import tech.beetwin.template.exceptions.AccountInfoException;
import tech.beetwin.template.exceptions.AuthorizationErrorException;
import tech.beetwin.template.model.AbstractJpaRepository;
import tech.beetwin.template.model.AccessLevel.AccessLevel;
import tech.beetwin.template.model.AccessLevel.AccessLevelsEntity;
import tech.beetwin.template.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.template.model.AccountInfo.AccountInfoRepository;
import tech.beetwin.template.model.TableMetadata.TableMetadataEntity;
import tech.beetwin.template.model.UserInfo.UserInfoEntity;
import tech.beetwin.template.utils.RefreshAuthJWTUtils;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AccountService extends AbstractService<AccountInfoEntity> {
    private final AccountInfoRepository accountInfoRepository;
    private final TableMetadataService tableMetadataService;
    private final PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private RefreshAuthJWTUtils refreshAuthJWTUtils;
    @Value("${jwt.validity.auth-token:0}")
    private Long validDuration;

    private AuthJWTUtils jwtTokenUtil;

    private UserDetailsServiceImpl userDetailsService;

    public AccountService(AccountInfoRepository accountInfoRepository, TableMetadataService tableMetadataService, PasswordEncoder passwordEncoder) {
        this.accountInfoRepository = accountInfoRepository;
        this.tableMetadataService = tableMetadataService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public AccountService setRefreshAuthJWTUtils(RefreshAuthJWTUtils refreshAuthJWTUtils) {
        this.refreshAuthJWTUtils = refreshAuthJWTUtils;
        return this;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(AuthJWTUtils jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PreAuthorize("hasAuthority('admin')")
    public AccountInfoEntity findByEmail(String email) {
        return accountInfoRepository.findAccountInfoEntityByEmail(email)
                .orElseThrow(AccountInfoException::accountNotFound);
    }

    @PreAuthorize("hasAuthority('admin')")
    public void setAccountAdminRole(Long id, boolean isAdmin) {
        var accountInfo = accountInfoRepository.findById(id).orElseThrow(AccountInfoException::accountNotFound);
        for (AccessLevelsEntity accessLevel : accountInfo.getAccessLevels()) {
            if (accessLevel.getLevel().toString().equals("admin")) {
                accessLevel.setEnabled(isAdmin);
                tableMetadataService.updateMetadata(new TableMetadataService.UpdateEntityEvent(accessLevel).setUpdateCreated(false));
                return;
            }
        }

        AccessLevelsEntity accessLevelsEntity = new AccessLevelsEntity()
                .setLevel(AccessLevel.ADMIN)
                .setEnabled(isAdmin)
                .setAccountInfoId(accountInfo)
                .setTableMetadata(new TableMetadataEntity());
        accountInfo.getAccessLevels().add(accessLevelsEntity);
        accountInfoRepository.save(accountInfo);
        tableMetadataService.updateMetadata(new TableMetadataService.UpdateEntityEvent(accessLevelsEntity).setUpdateCreated(true));
    }

    @PreAuthorize("hasAuthority('admin')")
    public void setAccountAdminRole(String email, boolean isAdmin) {
        this.setAccountAdminRole(
                accountInfoRepository.findAccountInfoEntityByEmail(email)
                        .orElseThrow(AccountInfoException::accountNotFound)
                        .getId(),
                isAdmin);
    }

    @PreAuthorize("hasAuthority('admin')")
    public void setAccountEnabled(Long id, boolean enabled) {
        var accountInfo = accountInfoRepository.findById(id).orElseThrow(AccountInfoException::accountNotFound);
        accountInfo.setEnabled(enabled);
        accountInfoRepository.save(accountInfo);
        tableMetadataService.updateMetadata(new TableMetadataService.UpdateEntityEvent(accountInfo));
    }

    @PreAuthorize("hasAuthority('admin')")
    public void setAccountEnabled(String email, boolean enabled) {
        this.setAccountEnabled(
                accountInfoRepository.findAccountInfoEntityByEmail(email)
                        .orElseThrow(AccountInfoException::accountNotFound)
                        .getId(),
                enabled);
    }


    @PreAuthorize("permitAll()")
    public void registerAccount(String email, String password, String firstName, String lastname) {
        List<AccountInfoEntity> accounts = accountInfoRepository.findAccountInfoEntitiesByEmail(email);
        if (!accounts.isEmpty()) {
            throw AccountInfoException.emailAlreadyExists();
        }

        TableMetadataEntity userMetadata = new TableMetadataEntity();
        TableMetadataEntity accountMetadata = new TableMetadataEntity();
        TableMetadataEntity accesslevelMetadata = new TableMetadataEntity();
        UserInfoEntity userInfo = new UserInfoEntity().setFirstName(firstName).setLastName(lastname).setTableMetadata(userMetadata);
        AccessLevelsEntity accessLevels = new AccessLevelsEntity().setLevel(AccessLevel.USER).setTableMetadata(accesslevelMetadata);
        password = passwordEncoder.encode(password);
        AccountInfoEntity accountInfo = new AccountInfoEntity().setEmail(email).setPassword(password).setUserInfoEntity(userInfo).setTableMetadata(accountMetadata);
        accountInfo.getAccessLevels().add(accessLevels);
        accessLevels.setAccountInfoId(accountInfo);
        userInfo.setAccountInfoId(accountInfo);

        accountInfoRepository.save(accountInfo);

        boolean isAnon = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        var event = new TableMetadataService.UpdateEntityEvent(accountInfo).setUpdateCreated(true).setEditor(isAnon ? accountInfo : null);
        tableMetadataService.updateMetadata(event);
        event = new TableMetadataService.UpdateEntityEvent(userInfo).setUpdateCreated(true).setEditor(isAnon ? accountInfo : null);
        tableMetadataService.updateMetadata(event);
        event = new TableMetadataService.UpdateEntityEvent(accessLevels).setUpdateCreated(true).setEditor(isAnon ? accountInfo : null);
        tableMetadataService.updateMetadata(event);
    }

    @PreAuthorize("permitAll()")
    public void registerAccount(RegisterAccountDTO dto) {
        registerAccount(dto.getEmail(), dto.getPassword(), dto.getFirstName(), dto.getLastName());
    }

    @PreAuthorize("hasAuthority('admin')")
    public void editAccount(String accountEmail, String newEmail, boolean enabled, String firstName, String lastname) {
        editAccount(accountInfoRepository.findAccountInfoEntityByEmail(accountEmail)
                        .orElseThrow(AccountInfoException::accountNotFound).getId(),
                newEmail,
                enabled,
                firstName,
                lastname);
    }

    @PreAuthorize("hasAuthority('admin')")
    public void editAccount(Long id, String email, boolean enabled, String firstName, String lastname) {
        AccountInfoEntity accountInfoEntity = this.findById(id);
        if (!Objects.equals(email, accountInfoEntity.getEmail()) && (accountInfoRepository.existsAccountInfoEntitiesByEmailEquals(email))) {
            throw AccountInfoException.emailAlreadyExists();
        }
        accountInfoEntity.setEmail(email).setEnabled(enabled).getUserInfoEntity().setFirstName(firstName).setLastName(lastname);
        accountInfoRepository.save(accountInfoEntity);
        tableMetadataService.updateMetadata(new TableMetadataService.UpdateEntityEvent(accountInfoEntity));
    }

    @PreAuthorize("hasAuthority('admin')")
    public void editAccount(String email, EditUserInfoDTO dto) {
        editAccount(accountInfoRepository.findAccountInfoEntityByEmail(email)
                        .orElseThrow(AccountInfoException::accountNotFound).getId(),
                dto.getEmail(),
                dto.getEnabled(),
                dto.getFirstName(),
                dto.getLastName());
    }

    @PreAuthorize("hasAuthority('admin')")
    public void editAccount(Long id, EditUserInfoDTO dto) {
        editAccount(id, dto.getEmail(), dto.getEnabled(), dto.getFirstName(), dto.getLastName());
    }

    private AuthenticationResponseDTO generateAuthenticationResponseDTO(String userEmail) {
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(userEmail);
        String token = jwtTokenUtil.generateToken(userDetails);
        String refreshToken = refreshAuthJWTUtils.generateToken(userDetails);

        return new AuthenticationResponseDTO()
                .setEmail(userDetails.getUsername())
                .setId(userDetails.getId())
                .setFirstName(userDetails.getName())
                .setLastName(userDetails.getSurname())
                .setValidDuration(validDuration)
                .setAuthToken(token)
                .setRefreshToken(refreshToken)
                .setMessage(I18nCodes.AUTHENTICATION_SUCCESS);
    }

    private String getCurrentRequestIp() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            return request.getRemoteAddr();
        }
        return null;
    }

    @PreAuthorize("permitAll()")
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO dto) {
        boolean thrown = true;
        AccountInfoEntity entity = null;
        try {
            entity = accountInfoRepository.findAccountInfoEntityByEmail(dto.getEmail()).orElseThrow(AccountInfoException::accountNotFound);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
            thrown = false;
        } catch (DisabledException e) {
            throw AuthorizationErrorException.accountNotConfirmed(e);
        } catch (BadCredentialsException | AccountInfoException e) {
            throw AuthorizationErrorException.invalidCredentials(e);
        } catch (LockedException e) {
            throw AuthorizationErrorException.accountDisabled(e);
        } finally {
            if (thrown && entity != null) {
                entity
                        .setLastFailLogin(LocalDateTime.now())
                        .setLastFailLoginIp(getCurrentRequestIp())
                        .setLoginFailuresSinceLastLogin(entity.getLoginFailuresSinceLastLogin() + 1);
                accountInfoRepository.save(entity);
            }
        }

        entity
                .setLastLogin(LocalDateTime.now())
                .setLastLoginIp(getCurrentRequestIp());
        accountInfoRepository.save(entity);

        return this.generateAuthenticationResponseDTO(dto.getEmail());
    }

    @PreAuthorize("!hasAuthority('ROLE_ANONYMOUS')")
    public AuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO dto) {

        if (!refreshAuthJWTUtils.validateToken(dto.getRefreshToken())) {
            throw TokenException.invalidRefreshToken();
        }
        if (!refreshAuthJWTUtils.getSubjectFromToken(dto.getRefreshToken()).equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            throw TokenException.notCurrentUserRefreshToken();
        }

        return this.generateAuthenticationResponseDTO(refreshAuthJWTUtils.getSubjectFromToken(dto.getRefreshToken()));
    }

    @Override
    protected AbstractJpaRepository<AccountInfoEntity> getRepository() {
        return accountInfoRepository;
    }
}
