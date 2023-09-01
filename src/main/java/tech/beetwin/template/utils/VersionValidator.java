package tech.beetwin.template.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VersionValidator implements ConstraintValidator<VersionValidation, String> {
    private final Logger logger = LoggerFactory.getLogger(VersionValidator.class);
    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {
        try {
            ApplicationContextUtils.getVersionJWTUtils();
        } catch (BeansException e) {
            logger.debug("Bean cant be obtained with error", e);
            throw new IllegalStateException("VersionJWTUtils bean unavailable");
        } catch (Exception e) {
            throw new IllegalStateException("Application context unavailable");
        }
        return ApplicationContextUtils.getVersionJWTUtils().validateToken(token);
    }
}
