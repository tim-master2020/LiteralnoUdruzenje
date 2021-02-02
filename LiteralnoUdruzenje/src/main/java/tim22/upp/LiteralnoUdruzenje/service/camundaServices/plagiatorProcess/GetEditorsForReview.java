package tim22.upp.LiteralnoUdruzenje.service.camundaServices.plagiatorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GetEditorsForReview  implements JavaDelegate {

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> editors = userService.findByRole(Role.EDITOR);
        List<String> usernames = new ArrayList<>();
        for (User editor : editors) {
            usernames.add(editor.getUsername());
        }
        delegateExecution.setVariable("editorsFromDatabase", usernames);
        delegateExecution.setVariable("allEditors",usernames);
        delegateExecution.setVariable("remaningEditors",usernames);
    }
}
