package tech.zdrzalik.courses.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.DTO.Request.EditUserInfoDTO;
import tech.zdrzalik.courses.DTO.Request.AuthenticationRequestDTO;
import tech.zdrzalik.courses.DTO.Request.RegisterAccountDTO;
import tech.zdrzalik.courses.DTO.Response.AuthenticationResponseDTO;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.exceptions.AccountInfoException;
import tech.zdrzalik.courses.exceptions.AuthorizationErrorException;
import tech.zdrzalik.courses.model.AbstractJpaRepository;
import tech.zdrzalik.courses.model.AccessLevel.AccessLevel;
import tech.zdrzalik.courses.model.AccessLevel.AccessLevelsEntity;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoEntity;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoRepository;
import tech.zdrzalik.courses.model.TableMetadata.TableMetadataEntity;
import tech.zdrzalik.courses.model.UserInfo.UserInfoEntity;
import tech.zdrzalik.courses.security.UserDetailsImpl;
import tech.zdrzalik.courses.security.UserDetailsServiceImpl;
import tech.zdrzalik.courses.utils.JWTUtils;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AccountService extends AbstractService<AccountInfoEntity> {
    private final AccountInfoRepository accountInfoRepository;
    private final PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    @Value("${jwt.validity:0}")
    private Long validDuration;

    private JWTUtils jwtTokenUtil;

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(JWTUtils jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public AccountService(AccountInfoRepository accountInfoRepository, PasswordEncoder passwordEncoder) {
        this.accountInfoRepository = accountInfoRepository;
        this.passwordEncoder = passwordEncoder;
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
    public void registerAccount(String email, String password, String firstName, String lastname){
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

    @Override
    protected AbstractJpaRepository<AccountInfoEntity> getRepository() {
        return accountInfoRepository;
    }
}
