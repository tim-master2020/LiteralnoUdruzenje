package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class IncrementReviewCounter implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int count = (int) delegateExecution.getVariable("reviewCount");
        count += 1;
        delegateExecution.setVariable("reviewCount", count);
    }
}
