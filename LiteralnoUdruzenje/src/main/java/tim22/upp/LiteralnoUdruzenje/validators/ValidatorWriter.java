package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.helper.ServiceHelper;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;


public class ValidatorWriter implements FormFieldValidator {

    @Autowired
    private IWriterService writerService;

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext formFieldValidatorContext) {
        writerService = ServiceHelper.getWriterService();
        String firstName = submittedValue.toString().split(" ")[0];
        String lastName = submittedValue.toString().split(" ")[1];
        if(writerService.findByFullname(firstName,lastName) == null){
            return false;
        }else{
            return true;
        }
    }
}
