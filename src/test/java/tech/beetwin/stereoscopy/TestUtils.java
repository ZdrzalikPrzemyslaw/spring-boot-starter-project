package tech.beetwin.stereoscopy;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;

public class TestUtils {
    private TestUtils() {
        throw new UnsupportedOperationException(TestUtils.class + " should not be instantiated");
    }

    public static void setAnonymousAuth(SecurityContext securityContext) {
        securityContext.setAuthentication(new AnonymousAuthenticationToken("anonymousUser", "key", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
    }

    public static void setAllRolesAuth(SecurityContext securityContext) {
        securityContext.setAuthentication(new AnonymousAuthenticationToken("anonymousUser", "key", AuthorityUtils.createAuthorityList("user", "admin")));
    }

    public static void wipeAuth(SecurityContext securityContext) {
        securityContext.setAuthentication(null);
    }

}
