package tim22.upp.LiteralnoUdruzenje.dto;

public class ValidationErrorDTO {

    private String message = "Validation failed,try again";

    public ValidationErrorDTO(String message) {
        this.message = message;
    }

    public ValidationErrorDTO(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
