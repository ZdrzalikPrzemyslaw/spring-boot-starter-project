package tech.zdrzalik.courses.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.DTO.Request.RegisterAccountDTO;
import tech.zdrzalik.courses.exceptions.AccountInfoException;
import tech.zdrzalik.courses.model.AbstractJpaRepository;
import tech.zdrzalik.courses.model.AccessLevel.AccessLevel;
import tech.zdrzalik.courses.model.AccessLevel.AccessLevelsEntity;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoEntity;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoRepository;
import tech.zdrzalik.courses.model.TableMetadata.TableMetadataEntity;
import tech.zdrzalik.courses.model.UserInfo.UserInfoEntity;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AccountService extends AbstractService<AccountInfoEntity> {
    private final AccountInfoRepository accountInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountInfoRepository accountInfoRepository, PasswordEncoder passwordEncoder) {
        this.accountInfoRepository = accountInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerAccount(String email, String password, String firstName, String lastname) throws AccountInfoException {
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
    public void registerAccount(RegisterAccountDTO dto) throws AccountInfoException {
        registerAccount(dto.getEmail(), dto.getPassword(), dto.getFirstName(), dto.getLastName());

    }

    @Override
    protected AbstractJpaRepository<AccountInfoEntity> getRepository() {
        return accountInfoRepository;
    }
}
