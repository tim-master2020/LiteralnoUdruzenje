package tim22.upp.LiteralnoUdruzenje.service.camundaServices.plagiatorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitializeEditors implements JavaDelegate {

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List editors = new ArrayList<>();
        for(User editor: userService.findByRole(Role.EDITOR)){
            editors.add(editor.getUsername());
        }
        delegateExecution.setVariable("allEditors",editors);
        delegateExecution.setVariable("remaningEditors",editors);
    }
}
