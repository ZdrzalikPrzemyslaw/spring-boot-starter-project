package tech.zdrzalik.courses.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.zdrzalik.courses.utils.JWTUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,jsr250Enabled = true,prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    UserDetailsServiceImpl userDetails;
    JWTUtils jwtUtils;

    @Autowired
    public void setJwtAuthenticationEntryPoint(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Autowired
    public void setUserDetails(UserDetailsServiceImpl userDetails) {
        this.userDetails = userDetails;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/account/register","/auth").permitAll()
//                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
        http.addFilterBefore(new AppAuthenticationFilter(userDetails,jwtUtils),UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
