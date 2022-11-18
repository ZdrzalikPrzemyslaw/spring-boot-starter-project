package tech.zdrzalik.courses.DTO.Response;

public class BasicMessageResponseDTO implements MessageResponseDTO {
    private String message;

    public BasicMessageResponseDTO() {

    }

    public String getMessage() {
        return message;
    }

    public BasicMessageResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

}

