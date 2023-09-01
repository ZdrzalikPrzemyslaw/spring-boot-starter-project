package tech.beetwin.template.dto.response;

public class BasicMessageResponseDTO extends AbstractMessageResponseDTO<BasicMessageResponseDTO> {

    public BasicMessageResponseDTO() {
        super();
    }

    @Override
    public BasicMessageResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

}

