package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;

import java.util.UUID;

@Service
public class EmailService implements JavaDelegate {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private RuntimeService runtimeService;

    //verifed

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User user = (User) delegateExecution.getVariable("registeredUser");

        final String msg = "Hello " + user.getUsername() + ",\n\nYour request for registration has been accepted. " +
                "Click the following link to verify your account:\n\n" + "http://localhost:8081/api/users/confirm-account/" +
                delegateExecution.getProcessInstanceId()+"/"+user.getUsername()+ "\n\n\neBook Team";

        emailService.sendEmail(user, msg);
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "verifed", false);
    }
}
