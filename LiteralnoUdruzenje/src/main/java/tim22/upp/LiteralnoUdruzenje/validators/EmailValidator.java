package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;

public class EmailValidator implements FormFieldValidator {
    @Override
    public boolean validate(Object o, FormFieldValidatorContext formFieldValidatorContext) {
        return o.toString().matches("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");
    }
}
