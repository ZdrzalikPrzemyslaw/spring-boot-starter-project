package tech.beetwin.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.BodyFilters;
import org.zalando.logbook.HeaderFilter;
import org.zalando.logbook.HeaderFilters;
import org.zalando.logbook.json.JsonBodyFilters;

import java.util.Collections;

@Configuration
public class LogbookConfig {

    @Bean
    public BodyFilter bodyFilter() {
        BodyFilter filter = BodyFilter.merge(
                BodyFilters.replaceFormUrlEncodedProperty(
                        Collections.singleton("password"), "<password-secret>"),
                JsonBodyFilters.replaceJsonStringProperty(
                        Collections.singleton("password"),
                        "<password-secret>"));
        filter = BodyFilter.merge(
                filter,
                JsonBodyFilters
                        .replaceJsonStringProperty(
                                Collections.singleton(
                                        "token"),
                                "<secret>"));
        filter = BodyFilter.merge(
                filter,
                (contentType, body) ->
                {
                    if (contentType != null) {
                        if (contentType.contains("text/html")) {
                            return "html-body-omitted";
                        } else if (contentType.contains("text/css")) {
                            return "css-body-omitted";
                        }
                    }
                    return body;
                });
        return filter;
    }

    @Bean
    public HeaderFilter headerFilter() {
        return HeaderFilter.merge(
                HeaderFilters.defaultValue(),
                HeaderFilters.replaceCookies("bearer-token"::equals, "<bearer-token-secret>"));
    }

}
