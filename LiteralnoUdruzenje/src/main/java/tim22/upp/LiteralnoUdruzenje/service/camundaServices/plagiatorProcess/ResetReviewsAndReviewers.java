package tim22.upp.LiteralnoUdruzenje.service.camundaServices.plagiatorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ResetReviewsAndReviewers implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        delegateExecution.setVariable("editorsThatReviewed", new HashMap<String,String>());
    }
}
