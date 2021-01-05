package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.List;

@Service
public class NotifyCommittee implements JavaDelegate {
    @Autowired
    private IEmailService emailService;

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> usernames = (List<String>) delegateExecution.getVariable("reviewers");
        for (String username : usernames) {
            User user = userService.findByUsername(username);
            emailService.sendCustomerEmail(user, "You have new books to review.");
        }
    }
}
