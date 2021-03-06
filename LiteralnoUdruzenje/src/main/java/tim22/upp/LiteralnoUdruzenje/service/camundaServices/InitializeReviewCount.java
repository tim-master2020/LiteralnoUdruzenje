package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class InitializeReviewCount implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("reviewCount", 0);
    }
}
