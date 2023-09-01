package tech.beetwin.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import tech.beetwin.template.interceptors.LocaleAttributeChangeInterceptor;
import tech.beetwin.template.utils.ApplicationContextUtils;

import java.util.Locale;

/**
 * Implementation of the {@link WebMvcConfigurer} class.
 * Also contains custom beans defined in methods annotated with {@link Bean}, eg. {@link #getLocaleResolver()}
 *
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("HEAD", "PUT", "GET", "POST", "DELETE", "PATCH");
    }


    /**
     * This {@link Bean} sets the {@link LocaleResolver} used in this application to be the {@link CookieLocaleResolver}.
     * The default locale is loaded from application properties from the property app.locale.default.
     *
     * @return locale resolver used in {@link LocaleAttributeChangeInterceptor} to set locale for a request.
     */
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale(defaultLocale));
        return cookieLocaleResolver;
    }

    @Bean(name = "applicationContextUtils")
    public ApplicationContextUtils getApplicationContextUtils(@Autowired ApplicationContext context) {
        ApplicationContextUtils applicationContextUtils = new ApplicationContextUtils();
        applicationContextUtils.setApplicationContext(context);
        return applicationContextUtils;
    }
}