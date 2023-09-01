package tech.beetwin.template.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.beetwin.template.common.I18nCodes;
import tech.beetwin.template.model.AccountInfo.AccountInfoEntity;
import tech.beetwin.template.model.AccountInfo.AccountInfoRepository;

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
        // TODO: 01/09/2023 If this happens, the user should be either treated as not logged in, or maybe prompted somehow. His JWT should be invalidated. Doesn't work as of now, to be fixed
        AccountInfoEntity accountInfoEntity = repository.findAccountInfoEntityByEmail(username).orElseThrow((() -> new UsernameNotFoundException(I18nCodes.ACCOUNT_NOT_FOUND)));
        Collection<SimpleGrantedAuthority> authorities = accountInfoEntity
                .getAccessLevels()
                .stream()
                .map(level -> new SimpleGrantedAuthority(level.getLevel().toString().toLowerCase()))
                .toList();
        return new UserDetailsImpl(
                authorities,
                accountInfoEntity.getEmail(),
                accountInfoEntity.getPassword(),
                accountInfoEntity.getUserInfoEntity().getFirstName(),
                accountInfoEntity.getUserInfoEntity().getLastName(),
                accountInfoEntity.isEnabled(),
                accountInfoEntity.isConfirmed(),
                accountInfoEntity.getId());
    }
}
