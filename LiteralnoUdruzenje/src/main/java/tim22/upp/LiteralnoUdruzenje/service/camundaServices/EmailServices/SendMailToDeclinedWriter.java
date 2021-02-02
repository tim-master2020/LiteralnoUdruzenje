package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

@Service
public class SendMailToDeclinedWriter implements JavaDelegate {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Sending mail to declined writer");
        delegateExecution.setVariable("timeExceeded",true);

        Object explanation = delegateExecution.getVariable("explanation");
        if(explanation == "" || explanation == null){
            if(delegateExecution.getCurrentActivityId().equals("PlagiatEmail")){
                explanation = "Your book has been detected as plagiarism.";
            } else if (delegateExecution.getCurrentActivityId().equals("NotifyApproval")) {
                explanation = "Your book has been denied by editor.";
            } else {
                explanation = "You failed to upload your work in given time";
            }
        }
        User writer = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
        final String message = "Hello " + writer.getUsername() + ",\n\nYour request to publish book has been declined. " +
                "Explanation from our editor: "+explanation+"\n\n\neBook Team";
        emailService.sendMail(writer,message);
    }
}
