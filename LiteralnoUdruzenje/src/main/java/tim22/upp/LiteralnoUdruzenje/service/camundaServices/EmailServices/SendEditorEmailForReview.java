package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

@Service
public class SendEditorEmailForReview implements JavaDelegate {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Sending mail to random editor");
        User editor = userService.findByUsername(delegateExecution.getVariable("editorsUsername").toString());
        final String message = "Hello " + editor.getUsername() + ",\n\nYou have new books to review. Please login to eBook to continue:\n\n" + "http://localhost:3000/" + "\n\n\neBook Team";
        emailService.sendMail(editor,message);
    }
}
