package tim22.upp.LiteralnoUdruzenje.dto;

import java.io.Serializable;

public class FormSubmissionDTO implements Serializable {

    String fieldId;
    String fieldValue;


    public FormSubmissionDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FormSubmissionDTO(String fieldId, String fieldValue) {
        super();
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

}
