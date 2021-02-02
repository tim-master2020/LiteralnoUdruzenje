package tim22.upp.LiteralnoUdruzenje.service.camundaServices.plagiatorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.enums.Vote;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResetVotes implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<Vote> votes = new ArrayList<>();
        delegateExecution.setVariable("votes", votes);
    }
}
