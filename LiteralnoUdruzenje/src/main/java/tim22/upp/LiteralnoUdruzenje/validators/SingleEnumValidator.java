package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import tim22.upp.LiteralnoUdruzenje.helper.ServiceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SingleEnumValidator implements FormFieldValidator {

    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;


    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {

        HashMap<String,String> submittedValues = (HashMap<String, String>) o;

        if(submittedValues.get("value") != null){
            return true;
        }else{
            return false;
        }
    }
}
