package tim22.upp.LiteralnoUdruzenje.dto;

import java.io.Serializable;

public class FormSubmissionDTO {

    String fieldId;
    Object fieldValue;


    public FormSubmissionDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public FormSubmissionDTO(String fieldId, Object fieldValue) {
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

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

}
