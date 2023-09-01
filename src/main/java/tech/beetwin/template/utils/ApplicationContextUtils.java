package tech.beetwin.template.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext ctx;

    public static final String VERSION_JWT_COMPONENT = "versionJWTUtils";

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        ctx = appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    public static VersionJWTUtils getVersionJWTUtils() {
        return (VersionJWTUtils) ctx.getBean(VERSION_JWT_COMPONENT);
    }
}
