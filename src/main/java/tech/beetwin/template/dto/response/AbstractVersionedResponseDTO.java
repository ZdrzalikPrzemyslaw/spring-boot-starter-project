package tech.beetwin.template.dto.response;

import tech.beetwin.template.utils.VersionValidation;

public abstract class AbstractVersionedResponseDTO<T> extends AbstractMessageResponseDTO<T>{
    @VersionValidation
    protected String token;

    public String getToken() {
        return token;
    }

    public abstract T setToken(String token);
}
