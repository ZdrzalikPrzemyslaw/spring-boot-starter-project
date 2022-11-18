package tech.zdrzalik.courses.DTO.Response;

public class MessageResponseDTO {
    private String message;

    public MessageResponseDTO() {

    }

    public String getMessage() {
        return message;
    }

    public MessageResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

}

