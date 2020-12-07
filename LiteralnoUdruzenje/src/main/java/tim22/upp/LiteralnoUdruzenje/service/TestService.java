package tim22.upp.LiteralnoUdruzenje.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService implements JavaDelegate{

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String var = "Pera";
        execution.setVariable("input", var);
        System.out.println("registracija pere perica");
        User user = identityService.newUser("");
        user.setId("pera");
        user.setPassword("123");
        identityService.saveUser(user);
    }
}
