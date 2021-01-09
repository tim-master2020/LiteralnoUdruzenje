package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

@Service
public class MailOfExpiration implements JavaDelegate{
    @Autowired
    private IEmailService emailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IWriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String)delegateExecution.getVariable("writer");
        Writer writer = writerService.findByUsername(username);

        emailService.sendCustomerEmail(writer, "Period of 7 days for uploading more material expired today.\n" +
                " Your request for registration has now been denied.");
    }
}
