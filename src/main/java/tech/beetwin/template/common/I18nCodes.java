package tech.beetwin.template.common;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

/**
 * This class contains all keys for internationalization for messages that can be sent by backend.
 * A view layer which displays errors and other information to the user should have translation keys prepared for all these keys.
 * This class should be split into multiple classes depending on the purpose of the message.
 */
public class I18nCodes {
    public static final String EMAIL_EXIST = "email_exist";
    public static final String LOCALE_ATTRIBUTE_NAME = "locale_internal";
    public static final String AUTHENTICATION_SUCCESS = "authentication_successful";
    public static final String ENTITY_NOT_FOUND = "entity_not_found";
    public static final String ACCOUNT_CREATED_SUCCESSFULLY = "account_created_successfully";
    public static final String ID_NULL = "id_null";
    public static final String PASSWORD_NULL = "password_null";
    public static final String PASSWORD_INVALID_SIZE = "password_invalid_size";
    public static final String EMAIL_NULL = "email_null";
    public static final String EMAIL_INVALID_SIZE = "email_invalid_size";
    public static final String NOT_AN_EMAIL = "not_an_email";
    public static final String FIRST_NAME_NULL = "first_name_null";
    public static final String FIRST_NAME_INVALID_SIZE = "first_name_invalid_size";
    public static final String LAST_NAME_NULL = "last_name_null";
    public static final String ENABLED_NULL = "enabled_null";
    public static final String LAST_NAME_INVALID_SIZE = "last_name_invalid_size";
    public static final String VERSION_MISMATCH = "version_mismatch";
    public static final String INVALID_REFRESH_TOKEN = "invalid_refresh_token";
    public static final String ACCOUNT_NOT_FOUND = "account_not_found";
    public static final String INVALID_TOKEN = "invalid_token";
    public static final String NOT_CURRENT_USER_TOKEN = "not_current_user_token";
    public static final String NOT_CURRENT_USER_REFRESH_TOKEN = "not_current_user_refresh_token";
    public static final String ACCOUNT_DISABLED = "account_disabled";
    public static final String INVALID_CREDENTIALS = "invalid_credentials";
    public static final String REQUEST_NULL = "request_null";
    public static final String ACCOUNT_NOT_CONFIRMED = "account_not_confirmed";

    public static final String REFRESH_TOKEN_NULL = "refresh_token_null";


    private I18nCodes() {
        throw new UnsupportedOperationException(I18nCodes.class + " should not be instantiated");
    }

    public static String getCodeByStatus(@NotNull HttpStatus status) {
        return status.name().toLowerCase();
    }

}
