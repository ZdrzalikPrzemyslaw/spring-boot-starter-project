package tech.zdrzalik.courses.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import tech.zdrzalik.courses.interceptors.LocaleAttributeChangeInterceptor;
import tech.zdrzalik.courses.utils.BreadcrumbsHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Implementation of the {@link WebMvcConfigurer} class.
 * Also contains custom beans defined in methods annotated with {@link Bean}, eg. {@link #getLocaleResolver()}
 * @see WebMvcConfigurer
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.locale.default:pl}")
    private String defaultLocale;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleAttributeChangeInterceptor());
    }

    /**
     * This {@link Bean} sets the {@link LocaleResolver} used in this application to be the {@link CookieLocaleResolver}.
     * The default locale is loaded from application properties from the property app.locale.default.
     * @return locale resolver used in {@link LocaleAttributeChangeInterceptor} to set locale for a request.
     */
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale(defaultLocale));
        return cookieLocaleResolver;
    }
}