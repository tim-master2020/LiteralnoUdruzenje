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
public class GetComittee implements JavaDelegate {
    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> committee = userService.findByRole(Role.COMMITTEE);
        List<String> usernames = new ArrayList<>();
        for (User c : committee) {
            usernames.add(c.getUsername());
        }
        delegateExecution.setVariable("comettee", usernames);
    }
}
