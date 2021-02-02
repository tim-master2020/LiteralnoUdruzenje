package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplacementEditorsSum implements FormFieldValidator {

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext formFieldValidatorContext) {

        Map chosenEditors = (Map) formFieldValidatorContext.getSubmittedValues();
        String maxReplacement = (String) formFieldValidatorContext.getExecution().getVariable("maxReplacment");
        if(chosenEditors.values().size() == Integer.parseInt(maxReplacement)){
            return  true;
        }else{
            return false;
        }
    }
}
