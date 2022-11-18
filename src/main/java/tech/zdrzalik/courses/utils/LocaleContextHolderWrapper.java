package tech.zdrzalik.courses.utils;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component("localeContextHolderWrapper")
public class LocaleContextHolderWrapper {
    /**
     * Wrapping the {@link LocaleContextHolder} with this method allows it to be used in thymeleaf templates to make Locale aware URIs.
     * @return {@link LocaleContext} from {@link LocaleContextHolder#getLocaleContext()}
     */
    public LocaleContext getLocaleContext() {
        return LocaleContextHolder.getLocaleContext();
    }
}