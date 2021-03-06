package tim22.upp.LiteralnoUdruzenje.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class FormFieldsDTO {

    String taskId;
    List<FormField> formFields;
    String processInstanceId;
    String taskName;

    public FormFieldsDTO(String taskId, String processInstanceId, List<FormField> formFields,String name) {
        super();
        this.taskId = taskId;
        this.formFields = formFields;
        this.processInstanceId = processInstanceId;
        this.taskName = name;
    }

    public FormFieldsDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
