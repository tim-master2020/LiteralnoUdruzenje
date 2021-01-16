package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

@Service
public class GetLector implements JavaDelegate {
    @Autowired
    IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User lector = userService.findByRole(Role.LECTOR).get(0);
        delegateExecution.setVariable("lector", lector.getUsername());
    }
}
