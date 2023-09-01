package tech.beetwin.template.utils;

import tech.beetwin.template.common.I18nCodes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VersionValidator.class)
public @interface VersionValidation {
    String message() default I18nCodes.VERSION_MISMATCH;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
