package tim22.upp.LiteralnoUdruzenje.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.service.ReaderService;

@Service
public class TestService implements JavaDelegate{

    @Autowired
    private IdentityService identityService;

    @Autowired
    private  ReaderService readerService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDTO> registration = (List<FormSubmissionDTO>)execution.getVariable("registration");
        Reader reader = new Reader();

        for (FormSubmissionDTO formField : registration) {
            if(formField.getFieldId().equals("username")) {
                reader.setId(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("password")) {
                reader.setPassword(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("email")) {
                reader.setEmail(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("firstname")) {
                reader.setFirstName(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("lastname")) {
                reader.setLastName(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("country")) {
                reader.setCountry(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("city")) {
                reader.setCity(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("betaReader")) {
                reader.setBetaReader(Boolean.parseBoolean(formField.getFieldValue()));
            }
        }

        readerService.saveReader(reader);
    }
}
