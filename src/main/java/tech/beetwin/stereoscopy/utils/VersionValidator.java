package tech.beetwin.stereoscopy.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VersionValidator implements ConstraintValidator<VersionValidation, String> {
    @Override
    public boolean isValid(String token, ConstraintValidatorContext constraintValidatorContext) {

        return ApplicationContextUtils.getVersionJWTUtils().validateToken(token);
    }
}
