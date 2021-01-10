package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.util.List;

@Service
public class EmailNotification implements JavaDelegate {
    @Autowired
    private IEmailService emailService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IWriterService writerService;

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String taskId = delegateExecution.getCurrentActivityId();

        if (taskId.equals("EmailRegistrationValidation")) {
            User user = (User) delegateExecution.getVariable("registeredUser");
            final String msg = "Hello " + user.getUsername() + ",\n\nYour request for registration has been accepted. " +
                    "Click the following link to verify your account:\n\n" + "http://localhost:8081/api/users/confirm-account/" +
                    delegateExecution.getProcessInstanceId() + "/" + user.getUsername() + "\n\n\neBook Team";

            emailService.sendCustomerEmail(user, msg);
            this.runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "verifed", false);

        } else if (taskId.equals("NotifyReviewers")) {
            List<String> usernames = (List<String>) delegateExecution.getVariable("reviewers");
            for (String username : usernames) {
                User user = userService.findByUsername(username);
                emailService.sendCustomerEmail(user, "You have new books to review.");
            }

        } else {
            String username = (String) delegateExecution.getVariable("writer");
            Writer writer = writerService.findByUsername(username);

            if (taskId.equals("EmailAboutDenial")) {
                emailService.sendCustomerEmail(writer, "You have been asked for more material more than 3 times. Your registration " +
                        "request is denied now.");
            } else if (taskId.equals("MailAboutDenial")){
                emailService.sendCustomerEmail(writer, "Your registration request has been declined based on votes for your documents.");
            } else if (taskId.equals("MailAboutApproval")){
                emailService.sendCustomerEmail(writer, "Your registration request has been approved based on votes for your documents.");
            } else if (taskId.equals("MailAboutMoreWork")){
                emailService.sendCustomerEmail(writer, "You need to upload more documents. You have 7 days to provide more material.");
            } else if (taskId.equals("MailAboutPaymentExpiration")) {
                emailService.sendCustomerEmail(writer, "Period for payment has expired today. You are not registered. Try again.");
            } else if (taskId.equals("MailAboutUploadExpiration")) {
                emailService.sendCustomerEmail(writer, "Period of 7 days for uploading more material expired today.\n" +
                        " Your request for registration has now been denied.");
            } else if (taskId.equals("RegistrationFinalMail")) {
                emailService.sendCustomerEmail(writer, "You paid your membership and you are now officially registered on EBook.");
            }

        }

    }
}