package tech.beetwin.stereoscopy.dto.response;

import tech.beetwin.stereoscopy.utils.VersionValidation;

public abstract class AbstractVersionedResponseDTO<T> extends AbstractMessageResponseDTO<T>{
    @VersionValidation
    protected String token;

    public String getToken() {
        return token;
    }

    public abstract T setToken(String token);
}
