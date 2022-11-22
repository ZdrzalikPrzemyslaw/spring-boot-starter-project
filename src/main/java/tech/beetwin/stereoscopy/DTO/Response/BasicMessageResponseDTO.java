package tech.beetwin.stereoscopy.DTO.Response;

public class BasicMessageResponseDTO implements MessageResponseDTO {
    private String message;

    public BasicMessageResponseDTO() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public BasicMessageResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

}

