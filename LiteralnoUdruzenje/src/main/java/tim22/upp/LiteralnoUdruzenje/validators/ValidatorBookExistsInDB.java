package tim22.upp.LiteralnoUdruzenje.validators;

import org.camunda.bpm.engine.impl.form.handler.FormFieldHandler;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidator;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import tim22.upp.LiteralnoUdruzenje.helper.ServiceHelper;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidatorBookExistsInDB implements FormFieldValidator {

    @Autowired
    private IBookService bookService;

    @Autowired
    private IWriterService writerService;

    @Override
    public boolean validate(Object submittedValue, FormFieldValidatorContext formFieldValidatorContext) {
        bookService = ServiceHelper.getBookService();
        writerService = ServiceHelper.getWriterService();
        Book book = bookService.findBookByName(submittedValue.toString());

        Map otherValuesSubmited = (Map<String,Object>) formFieldValidatorContext.getSubmittedValues();
        FormFieldHandler formFieldHandler = formFieldValidatorContext.getFormFieldHandler();
        Writer writer = null;
        if(formFieldHandler.getId().equals("myBook")){
            writer = writerService.findByUsername(formFieldValidatorContext.getExecution().getVariable("loggedInWriter").toString());
        }else{
            String firstName = otherValuesSubmited.get("writerFullName").toString().split(" ")[0];
            String lastName = otherValuesSubmited.get("writerFullName").toString().split(" ")[1];
            writer = writerService.findByFullname(firstName,lastName);
        }

        boolean existsAndHasWritten = false;
        if(book == null){
            existsAndHasWritten = false;
        }else{
            Set<Writer> writers = book.getWriters();
            for(Writer w : writers){
                if(w.getUsername().equals(writer.getUsername())){
                    existsAndHasWritten = true;
                }
            }
        }
        return  existsAndHasWritten;
    }
}
