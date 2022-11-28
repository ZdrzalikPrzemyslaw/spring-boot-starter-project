package tech.beetwin.stereoscopy.dto.response;

import java.sql.Timestamp;

public class ErrorResponseDTO extends BasicMessageResponseDTO {
    private Timestamp timestamp;
    private int status;
    private String error;
    private String path;

    public ErrorResponseDTO() {
        super();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public ErrorResponseDTO setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ErrorResponseDTO setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public ErrorResponseDTO setError(String error) {
        this.error = error;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ErrorResponseDTO setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public ErrorResponseDTO setMessage(String message) {
         super.setMessage(message);
         return this;
    }
}
