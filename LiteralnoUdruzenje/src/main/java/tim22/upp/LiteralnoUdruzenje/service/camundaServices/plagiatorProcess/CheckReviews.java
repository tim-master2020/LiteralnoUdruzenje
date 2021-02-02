package tim22.upp.LiteralnoUdruzenje.service.camundaServices.plagiatorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.enums.Vote;

import java.util.List;

@Service
public class CheckReviews  implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String result = "";
        List<String> votesFromProcess = (List<String>) delegateExecution.getVariable("votes");

        if(isAllEqual(votesFromProcess)) {
            if(votesFromProcess.get(0).contains("plagiat"))
                result="plagiat";
            else
                result="notplagiat";
        } else {
            result="voteAgain";
        }

        delegateExecution.setVariable("decisionMade", result);
    }

    public  boolean isAllEqual(List<String> votes){
        for(int i=1; i<votes.size(); i++){
            if(!votes.get(0).equals(votes.get(i))){
                return false;
            }
        }
        return true;
    }
}
