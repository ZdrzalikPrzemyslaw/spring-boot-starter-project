package tech.beetwin.stereoscopy.dto.response;

public abstract class AbstractMessageResponseDTO<T> {
    protected String message;

    public String getMessage() {
        return message;
    }

    public abstract T setMessage(String message);
}
