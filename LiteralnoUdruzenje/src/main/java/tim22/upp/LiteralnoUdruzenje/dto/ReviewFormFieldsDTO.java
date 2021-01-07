package tim22.upp.LiteralnoUdruzenje.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class ReviewFormFieldsDTO extends FormFieldsDTO {
    private List<String> names;
    private String writer;

    ReviewFormFieldsDTO(){
        super();
    }

    public ReviewFormFieldsDTO(String taskId, String processInstanceId, List<FormField> formFields, List<String> names, String writer) {
        super();
        this.taskId = taskId;
        this.formFields = formFields;
        this.processInstanceId = processInstanceId;
        this.names = names;
        this.writer = writer;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
