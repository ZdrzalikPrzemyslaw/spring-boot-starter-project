package tech.beetwin.stereoscopy.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import tech.beetwin.stereoscopy.utils.AuthJWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsServiceImpl userDetailsService;
    private AuthJWTUtils jwtUtils;

    public AppAuthenticationFilter(UserDetailsServiceImpl userDetailsService, AuthJWTUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;

        Cookie cookie = WebUtils.getCookie(request, "bearer-token");
        if (cookie != null) {
            token = cookie.getValue();
        }
        if (!StringUtils.hasText(token)) {
            token = request.getHeader("authorization");
            if (!(StringUtils.hasText(token) && token.startsWith("Bearer "))) {
                filterChain.doFilter(request, response);
                return;
            }
            token = token.substring(7);
        }

        if(!jwtUtils.validateToken(token))
        {
            filterChain.doFilter(request,response);
            return;
        }
        String subject = jwtUtils.getSubjectFromToken(token);

        if (subject == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(subject);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);



        filterChain.doFilter(request, response);
    }
}
