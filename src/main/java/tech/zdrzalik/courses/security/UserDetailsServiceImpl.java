package tech.zdrzalik.courses.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoEntity;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoRepository;

import java.util.Collection;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class UserDetailsServiceImpl implements UserDetailsService {

    AccountInfoRepository repository;

    public UserDetailsServiceImpl(AccountInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: 11/11/2022 Dowiedziec sie co sie w tym przypadku dzieje
        AccountInfoEntity userInfo = repository.findAccountInfoEntityByEmail(username).orElseThrow((() -> new UsernameNotFoundException(I18nCodes.ACCOUNT_NOT_FOUND)));
        Collection<SimpleGrantedAuthority> authorities = userInfo
                .getAccessLevels()
                .stream()
                .map(level -> new SimpleGrantedAuthority(level.getLevel().toString().toLowerCase()))
                .toList();
        return new UserDetailsImpl(
                authorities,
                userInfo.getEmail(),
                userInfo.getPassword(),
                userInfo.getUserInfoEntity().getFirstName(),
                userInfo.getUserInfoEntity().getLastName(),
                userInfo.isEnabled(),
                userInfo.isConfirmed());
    }
}
