package tech.zdrzalik.courses.utils;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component("localeContextHolderWrapper")
public class LocaleContextHolderWrapper {
    public LocaleContext getLocaleContext() {
        return LocaleContextHolder.getLocaleContext();
    }
}