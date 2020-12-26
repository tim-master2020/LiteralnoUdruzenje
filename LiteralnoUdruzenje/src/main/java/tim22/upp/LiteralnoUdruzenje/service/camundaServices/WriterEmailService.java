package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;

import java.util.UUID;

@Service
public class WriterEmailService implements JavaDelegate {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Writer writer = (Writer) delegateExecution.getVariable("registeredWriter");
        final String message = "Hello " + writer.getUsername() + ",\n\nYour request for registration has been accepted. Click the following link to verify your account:\n\n" + "http://localhost:8081/api/users/confirm-account/" + delegateExecution.getProcessInstanceId()+"/"+writer.getUsername()+ "\n\n\neBook Team";

        emailService.sendWriterEmail(writer, message);
        this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "verifed", false);
    }
}
