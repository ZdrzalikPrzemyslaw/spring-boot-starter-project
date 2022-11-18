package tech.zdrzalik.courses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

/**
 * Configuration class handling the customization of {@link org.springframework.data.domain.Pageable} objects in this application.
 */
@Configuration
public class PageableConfig {
    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer pageableResolverCustomizer() {
        return pageableResolver -> {
            pageableResolver.setMaxPageSize(80);
            pageableResolver.setPageParameterName("page");
        };
    }
}
