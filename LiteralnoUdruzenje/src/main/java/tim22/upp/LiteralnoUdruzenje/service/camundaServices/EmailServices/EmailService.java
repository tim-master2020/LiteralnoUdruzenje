package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;

import java.util.UUID;

@Service
public class EmailService implements JavaDelegate {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IReaderService IReaderService;

    //verifed

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Reader reader = (Reader) delegateExecution.getVariable("registeredReader");
        String activationCode = UUID.randomUUID().toString();
        final String message = "Hello " + reader.getUsername() + ",<br><br>"
                + "To active your account please "
                + "<a href=\"" + "http://localhost:8081/api/users/confirm-account/"+delegateExecution.getProcessInstanceId()+"/"+reader.getUsername() + "\">click here.</a><br><br>"
                + "<br>"
                + "Yours, <br><br>"
                + "<i>EBook app</i>";
        final String poruka = "Hello " + reader.getUsername() + ",\n\nYour request for registration has been accepted. Click the following link to verify your account:\n\n" + "http://localhost:8081/api/users/confirm-account/" + delegateExecution.getProcessInstanceId()+"/"+reader.getUsername()+ "\n\n\neBook Team";

        emailService.sendCustomerEmail(reader, poruka);
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "verifed", false);
    }

//"Hello " + patient.getName() + ",\n\nYour request for registration has been accepted. Click the following link to activate your account:\n\n" + "http://localhost:8081/api/patients/confirm-account/" + patient.getVerificationCode() + "\n\n\nClinical System Team"

}
