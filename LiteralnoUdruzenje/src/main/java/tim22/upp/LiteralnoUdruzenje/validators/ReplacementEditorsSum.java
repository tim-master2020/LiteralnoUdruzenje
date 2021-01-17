package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.List;
import java.util.Map;

public class ReplacementEditorsSum implements FormFieldValidator {

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext formFieldValidatorContext) {
        List editorsThatHaventReviewed = (List) formFieldValidatorContext.getExecution().getVariable("editorsThatHaventReviewed");
        Map enteredValues = (Map<String,String>)formFieldValidatorContext.getSubmittedValues().get("replacmentEditors");
        if(enteredValues.values().size() > editorsThatHaventReviewed.size()){
            return  false;
        }else{
            return true;
        }
    }
}
