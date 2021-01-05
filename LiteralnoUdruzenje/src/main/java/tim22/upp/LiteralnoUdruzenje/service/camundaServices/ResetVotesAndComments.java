package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Vote;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResetVotesAndComments implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<String> comments = new ArrayList<>();
        List<Vote> votes = new ArrayList<>();

        delegateExecution.setVariable("comments", comments);
        delegateExecution.setVariable("votes", votes);
    }
}
