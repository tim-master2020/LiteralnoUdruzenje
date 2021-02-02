package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CamundaEnumValidator implements FormFieldValidator {

    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {

        if(o == null){
            return false;
        }else{
            return  true;
        }
    }
}
