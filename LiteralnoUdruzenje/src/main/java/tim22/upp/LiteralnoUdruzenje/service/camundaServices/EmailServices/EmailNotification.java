package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.util.ArrayList;
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

        } else if (taskId.equals("NotifySelectedBetaReaders")) {
            List<String> usernames = (List<String>) delegateExecution.getVariable("selectedBetaReaders");
            for (String username : usernames) {
                User user = userService.findByUsername(username);
                emailService.sendCustomerEmail(user, "Dear" + user.getFirstName() + "\n," + "you have new book to read and comment on. You have five days to do so.");
            }
        } else if (taskId.equals("EmailUpdatedBookForEditor")) {
            User writer = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
            User editor = userService.findByUsername(delegateExecution.getVariable("editorsUsername").toString());
            emailService.sendCustomerEmail(editor, "Hello,  \n\n" + writer.getUsername()
                    + " has updated his/hers book. You can download new version and review it.");
        }else if (taskId.equals("NotifyLector")) {
            User writer = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
            User lector = userService.findByUsername(delegateExecution.getVariable("lector").toString());
            emailService.sendCustomerEmail(lector, "Hello,  \n\n" + lector.getUsername()
                    + "You can download and review " + writer.getUsername() + "'s book. ");
        }else if (taskId.equals("NotifyMainEditor")) {
            User writer = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
            User editor = userService.findByUsername(delegateExecution.getVariable("mainEditor").toString());
            emailService.sendCustomerEmail(editor, "Hello, " + editor.getUsername()+  "\n\n"
                    + "You can download and review " + writer.getUsername() + "'s book. ");
        }else if (taskId.equals("NotifyWriterNoTimeMainEditor")) {
            User writer = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
            emailService.sendCustomerEmail(writer, "Hello,  \n\n"
                    + "Time for uploading updated book has expired. You can upload book" +
                    " and get reviewed again from the beginning.\n");
        }else if (taskId.equals("NotifyWriterPrint")) {
            User writer = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());
            emailService.sendCustomerEmail(writer, "Hello,  \n\n"
                    + "Congratulations! \n\n" + "Your book has been approved by everyone in the committee." +
                    " It is sent to printing and indexing now.\n");
        } else if (taskId.equals("NotifyMainEditorAboutPlagiarism")) {
            User editor = userService.findByUsername(delegateExecution.getVariable("mainEditor").toString());
            emailService.sendCustomerEmail(editor, "Hello, " + editor.getUsername()+  "\n\n"
                    + "There's been a complaint about plagiarism. You have to select minimum of two editors to review it.");
        } else if(taskId.equals("NotifySelectedEditors")) {
            List<String> usernames = (List<String>) delegateExecution.getVariable("selectedEditors");
            for (String username : usernames) {
                User user = userService.findByUsername(username);
                emailService.sendCustomerEmail(user, "Dear" + user.getFirstName() + "\n," + "you have new book to read and comment on. You have five days to do so.");
            }
        } else if (taskId.equals("NotifyInvalidPlagiarism")) {
            User writer = userService.findByUsername(delegateExecution.getVariable("writerWithComplaint").toString());
            emailService.sendCustomerEmail(writer, "Hello, " + writer.getUsername()+  "\n\n"
                    + "The complaint about plagiarism was invalid. We're sorry.");
        } else if (taskId.equals("NotifyComplanee")) {
            User writer = userService.findByUsername(delegateExecution.getVariable("writerWithComplaint").toString());
            emailService.sendCustomerEmail(writer, "Hello, " + writer.getUsername()+  "\n\n"
                    + "The complaint about plagiarism was correct.");
        } else if(taskId.equals("")) {
            User writer = userService.findByUsername(delegateExecution.getVariable("plagiatorWriter").toString());
            emailService.sendCustomerEmail(writer, "Hello, " + writer.getUsername()+  "\n\n"
                    + "The complaint about plagiarism was correct. You are a plagiator.");
        }
        else
         {
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
            } else if (taskId.equals("LossOfPenaltyEmail")) {
                List losingBetaStatus = (ArrayList<String>) delegateExecution.getVariable("losingBetaStatus");
                for(Object betaReader : losingBetaStatus){
                    User reader = userService.findByUsername(betaReader.toString());
                    emailService.sendCustomerEmail(reader, "Dear beta reader, you failed to leave a comment in given time.Your beta status has been revoked.");
                }
            }

        }

    }
}
