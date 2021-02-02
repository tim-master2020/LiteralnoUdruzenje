package tim22.upp.LiteralnoUdruzenje.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class ReviewFormFieldsDTO extends FormFieldsDTO {
    private String writer;

    ReviewFormFieldsDTO(){
        super();
    }

    public ReviewFormFieldsDTO(String taskId, String processInstanceId, List<FormField> formFields, String taskName, String writer) {
        super();
        this.taskId = taskId;
        this.formFields = formFields;
        this.processInstanceId = processInstanceId;
        this.taskName = taskName;
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
