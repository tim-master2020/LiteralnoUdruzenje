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
        String explanation = delegateExecution.getVariable("explanation").toString();
        User editor = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
        final String message = "Hello " + editor.getUsername() + ",\n\nYour request to publish book has been declined." +
                "Explanation from our editor:"+explanation+"\n\n\neBook Team";
        emailService.sendMail(editor,message);
    }
}
