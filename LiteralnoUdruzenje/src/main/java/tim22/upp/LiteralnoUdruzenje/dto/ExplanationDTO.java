package tim22.upp.LiteralnoUdruzenje.dto;

public class ExplanationDTO {

    private String message;

    public ExplanationDTO(){}

    public ExplanationDTO(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
