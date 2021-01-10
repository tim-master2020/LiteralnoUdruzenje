package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

@Service
public class DeclineMembership implements JavaDelegate {

    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String writer = (String)delegateExecution.getVariable("writer");
        userService.removeUser(writer);
    }
}
