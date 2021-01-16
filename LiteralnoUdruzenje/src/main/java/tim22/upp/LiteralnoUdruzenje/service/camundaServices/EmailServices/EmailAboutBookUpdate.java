package tim22.upp.LiteralnoUdruzenje.service.camundaServices.EmailServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IEmailService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailAboutBookUpdate implements JavaDelegate {
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
        User user = userService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString());

        String msg = "";
        if (taskId.equals("CommentsToWriter")) {
            Map betasThatCommented = (HashMap<String,String>)delegateExecution.getVariable("betasThatCommented");

            String comments = "Comments: \n\n";
            for(Object comment : betasThatCommented.values()){
                comments = comments + comment + ("\n\n");
            }

            msg = "Hello " + user.getUsername() + ",\n\nBeta readers have left comments about your book. "
                    + "You can update your book and upload it again for further reviews.\n\n" + comments +
                    "\n\n\neBook Team";

            delegateExecution.setVariable("lectorViewed", false);

        } else if (taskId.equals("EditorChanges")) {
            String editorsComment = (String)delegateExecution.getVariable("editorsComment");

            msg = "Hello " + user.getUsername() + ",\n\nEditor has left comment about your book. "
                    + "You can update your book and upload it again for further reviews.\n\nEditor's comment:\n" + editorsComment +
                    "\n\n\neBook Team";
        } else if (taskId.equals("EditorNoChanges")) {
            String editorsComment = (String)delegateExecution.getVariable("editorsComment");

            msg = "Hello " + user.getUsername() + ",\n\nEditor has decided that your book does not need further changes! "
                    + "\n\nEditor's comment:\n" + editorsComment +
                    "\n\n\neBook Team";
        }
        else if (taskId.equals("LectorChanges")) {
            String lectorsComment = (String)delegateExecution.getVariable("lectorsComment");

            msg = "Hello " + user.getUsername() + ",\n\nLector has commented your book. You can read comment and upload" +
                    " updated book for further actions."
                    + "\n\nLector's comment:\n" + lectorsComment +
                    "\n\n\neBook Team";
        } else if (taskId.equals("LectorNoChanges")) {
            String lectorsComment = (String)delegateExecution.getVariable("lectorsComment");

            msg = "Hello " + user.getUsername() + ",\n\nLector has decided that your book does not need further changes!"
                    + "\n\nLector's comment:\n" + lectorsComment +
                    "\n\n\neBook Team";
        } else if (taskId.equals("MainEditorChanges")) {
            String editorsComment = (String)delegateExecution.getVariable("mainEditorsComment");

            msg = "Hello " + user.getUsername() + ",\n\nMain editor has commented your book. You can read comment and upload." +
                    " You have time limit of 3 days for uploading, hurry up!"
                    + "\n\nEditor's comment:\n" + editorsComment +
                    "\n\n\neBook Team";
        }
        emailService.sendCustomerEmail(user, msg);

    }
}
