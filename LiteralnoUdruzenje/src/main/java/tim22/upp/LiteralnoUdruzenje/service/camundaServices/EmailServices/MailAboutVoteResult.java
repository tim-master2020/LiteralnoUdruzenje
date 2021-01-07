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
public class MailAboutVoteResult implements JavaDelegate {
    @Autowired
    private IEmailService emailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IWriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String taskId = delegateExecution.getCurrentActivityId();
        String username = (String)delegateExecution.getVariable("writer");
        Writer writer = writerService.findByUsername(username);

        if (taskId.equals("MailAboutDenial")){
            emailService.sendCustomerEmail(writer, "Your registration request has been declined based on votes for your documents.");
        } else if (taskId.equals("MailAboutApproval")){
            emailService.sendCustomerEmail(writer, "Your registration request has been approved based on votes for your documents.");
        } else if (taskId.equals("MailAboutMoreWork")){
            emailService.sendCustomerEmail(writer, "You need to upload more documents. You have 7 days to provide more material.");
            delegateExecution.setVariable("deadlineForExtaWorkUpload", 168);
        }
    }
}
