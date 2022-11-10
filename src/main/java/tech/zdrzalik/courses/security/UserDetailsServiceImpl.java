package tech.zdrzalik.courses.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tech.zdrzalik.courses.common.I18nCodes;
import tech.zdrzalik.courses.exceptions.AccountInfoException;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoEntity;
import tech.zdrzalik.courses.model.AccountInfo.AccountInfoRepository;

import java.awt.font.ShapeGraphicAttribute;
import java.util.ArrayList;
import java.util.Collection;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserDetailsServiceImpl implements UserDetailsService {

    AccountInfoRepository repository;

    public UserDetailsServiceImpl(AccountInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountInfoEntity userInfo = repository.findAccountInfoEntityByEmail(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException(I18nCodes.ACCOUNT_NOT_FOUND);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userInfo.getAccessLevels().forEach(level -> {
            authorities.add(new SimpleGrantedAuthority(level.getLevel().getLevel()));
        });
        return new UserDetailsImpl(authorities, userInfo.getEmail(), userInfo.getPassword(), userInfo.getUserInfoEntity().getFirstName(),userInfo.getUserInfoEntity().getLastName(),userInfo.isEnabled(),userInfo.isConfirmed());
    }
}
