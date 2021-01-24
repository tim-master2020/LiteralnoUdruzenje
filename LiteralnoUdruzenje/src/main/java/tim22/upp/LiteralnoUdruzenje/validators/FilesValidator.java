package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import tim22.upp.LiteralnoUdruzenje.helper.ServiceHelper;

import java.util.List;

public class FilesValidator implements FormFieldValidator {

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        formService = ServiceHelper.getFormService();
        taskService = ServiceHelper.getTaskService();
        List submittedValues = (List) o;
        int min=0;
        Task task = taskService.createTaskQuery().processInstanceId(formFieldValidatorContext.getExecution().getProcessInstanceId()).singleResult();
        List<FormField> fields = formService.getTaskFormData(task.getId()).getFormFields();
        for(FormField field : fields){
            if(field.getTypeName().contains("string_input_file")){
                String[] splitArray = field.getType().getName().split("_");
                min = Integer.parseInt(splitArray[3]);
                break;
            }
        }
        if(submittedValues.size() < min){
            return false;
        }else{
            return true;
        }
    }
}
