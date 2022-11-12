package tech.zdrzalik.courses.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import tech.zdrzalik.courses.interceptors.LocaleAttributeChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private HttpServletRequest httpServletRequest;
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

    @Bean(name = "localeContextHolderWrapper")
    public localeContextHolderWrapper getLocaleContextHolderWrapper() {
        return new localeContextHolderWrapper();
    }
    public static class localeContextHolderWrapper {
        public LocaleContext getLocaleContext() {
            return LocaleContextHolder.getLocaleContext();
        }
    }

}