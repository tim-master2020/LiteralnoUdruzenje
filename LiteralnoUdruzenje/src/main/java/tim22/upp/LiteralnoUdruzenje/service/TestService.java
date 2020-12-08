package tim22.upp.LiteralnoUdruzenje.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.model.Reader;

@Service
public class TestService implements JavaDelegate{

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDTO> registration = (List<FormSubmissionDTO>)execution.getVariable("registration");
        System.out.println(registration);
        User reader = (Reader)identityService.newUser("user1");

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
                ((Reader) reader).setCountry(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("city")) {
                ((Reader) reader).setCity(formField.getFieldValue());
            }
            if(formField.getFieldId().equals("betaReader")) {
                ((Reader) reader).setBetaReader(Boolean.parseBoolean(formField.getFieldValue()));
            }
        }

        identityService.saveUser((User)reader);
        User user = identityService.createUserQuery().userId(reader.getId()).singleResult();
    }
}
