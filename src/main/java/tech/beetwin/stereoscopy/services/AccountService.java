package tech.beetwin.stereoscopy.services;

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
import tech.beetwin.stereoscopy.dto.request.AuthenticationRequestDTO;
import tech.beetwin.stereoscopy.dto.request.RefreshTokenRequestDTO;
import tech.beetwin.stereoscopy.dto.response.AuthenticationResponseDTO;
import tech.beetwin.stereoscopy.common.I18nCodes;
import tech.beetwin.stereoscopy.security.UserDetailsImpl;
import tech.beetwin.stereoscopy.security.UserDetailsServiceImpl;
import tech.beetwin.stereoscopy.utils.AuthJWTUtils;
import tech.beetwin.stereoscopy.dto.request.EditUserInfoDTO;
import tech.beetwin.stereoscopy.dto.request.RegisterAccountDTO;
import tech.beetwin.stereoscopy.exceptions.AccountInfoException;
import tech.beetwin.stereoscopy.exceptions.AuthorizationErrorException;
import tech.beetwin.stereoscopy.model.AbstractJpaRepository;
import tech.beetwin.stereoscopy.model.AccessLevel.AccessLevel;
import tech.beetwin.stereoscopy.model.AccessLevel.AccessLevelsEntity;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.stereoscopy.model.AccountInfo.AccountInfoRepository;
import tech.beetwin.stereoscopy.model.TableMetadata.TableMetadataEntity;
import tech.beetwin.stereoscopy.model.UserInfo.UserInfoEntity;
import tech.beetwin.stereoscopy.utils.RefreshAuthJWTUtils;


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
                return;
            }
        }

        // TODO: 12/11/2022 Ustawić pola created by, ip etc.
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
        //TODO wysyłanie maili aktywacyjnych, walidacja danych
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

    @PreAuthorize("permitAll()")
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO dto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (DisabledException e) {
            throw AuthorizationErrorException.accountNotConfirmed(e);
        } catch (BadCredentialsException e) {
            throw AuthorizationErrorException.invalidCredentials(e);
        } catch (LockedException e) {
            throw AuthorizationErrorException.accountDisabled(e);
        }
        // TODO: 29/11/2022  handle exception when jwt outdated & update last login info
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
        String token = jwtTokenUtil.generateToken(userDetails);

        return new AuthenticationResponseDTO()
                .setEmail(userDetails.getUsername())
                .setId(userDetails.getId())
                .setFirstName(userDetails.getName())
                .setLastName(userDetails.getSurname())
                .setValidDuration(validDuration)
                .setToken(token)
                .setMessage(I18nCodes.AUTHENTICATION_SUCCESS);
    }

    @PreAuthorize("!hasAuthority('ROLE_ANONYMOUS')")
    public AuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO dto) {

            if (!refreshAuthJWTUtils.validateToken(dto.getRefreshToken())) {
        //                throw
            }
            if (!refreshAuthJWTUtils.getSubjectFromToken(dto.getRefreshToken()).equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
        //                throw
            }

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(refreshAuthJWTUtils.getSubjectFromToken(dto.getRefreshToken()));
        String token = jwtTokenUtil.generateToken(userDetails);

        return new AuthenticationResponseDTO()
                .setEmail(userDetails.getUsername())
                .setId(userDetails.getId())
                .setFirstName(userDetails.getName())
                .setLastName(userDetails.getSurname())
                .setValidDuration(validDuration)
                .setToken(token)
                .setMessage(I18nCodes.AUTHENTICATION_SUCCESS);
    }

    @Override
    protected AbstractJpaRepository<AccountInfoEntity> getRepository() {
        return accountInfoRepository;
    }
}
