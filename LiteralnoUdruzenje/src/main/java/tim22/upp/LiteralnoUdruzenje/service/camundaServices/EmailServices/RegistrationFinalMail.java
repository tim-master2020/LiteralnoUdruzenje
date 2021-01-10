package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

public class RegistrationFinalMail implements JavaDelegate {
    @Autowired
    private IEmailService emailService;

    @Autowired
    private IWriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String)delegateExecution.getVariable("writer");
        Writer writer = writerService.findByUsername(username);

        emailService.sendCustomerEmail(writer, "You paid your membership and you are now officially registered on EBook.");
    }
}
