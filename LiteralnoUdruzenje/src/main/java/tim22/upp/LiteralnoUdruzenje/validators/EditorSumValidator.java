package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.HashMap;
import java.util.Map;

public class EditorSumValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext formFieldValidatorContext) {
        Map chosenEditors = (Map) formFieldValidatorContext.getSubmittedValues();
        if(chosenEditors.values().size() < 2){
            return false;
        }else{
            return true;
        }
    }
}
