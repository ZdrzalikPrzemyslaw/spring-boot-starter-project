package tech.zdrzalik.courses.security;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppAuthenticationFilter extends OncePerRequestFilter {

    UserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authentication");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);

            UserDetailsImpl userDetails = userDetailsService.loadUserByUsername("test");

        }


        filterChain.doFilter(request,response);
    }
}
