package tech.zdrzalik.courses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import tech.zdrzalik.courses.interceptors.LocaleAttributeChangeInterceptor;

import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleAttributeChangeInterceptor());
    }

    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale("pl", "PL"));
        return cookieLocaleResolver;
    }

}