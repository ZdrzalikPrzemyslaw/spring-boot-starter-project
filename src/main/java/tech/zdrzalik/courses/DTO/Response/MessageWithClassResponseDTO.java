package tech.zdrzalik.courses.DTO.Response;

public class MessageWithClassResponseDTO {

    String message;
    String className;
    Long id;

    public MessageWithClassResponseDTO() {
    }

    public String getClassName() {
        return className;
    }

    public MessageWithClassResponseDTO setClassName(String className) {
        this.className = className;
        return this;
    }

    public Long getId() {
        return id;
    }

    public MessageWithClassResponseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageWithClassResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }


}
