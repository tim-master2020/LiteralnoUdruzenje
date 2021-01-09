package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Review;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.model.enums.Vote;
import tim22.upp.LiteralnoUdruzenje.service.IReviewService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckRates implements JavaDelegate {

    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IWriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String writerUsername = (String) delegateExecution.getVariable("writer");
        //Writer writer = writerService.findByUsername(writerUsername.toString());
        List<Review> reviews = reviewService.findByWriter(writerUsername);
        String result = "";
        List<Vote> votesFromProcess = (List<Vote>) delegateExecution.getVariable("votes");

        if(votesFromProcess.contains(Vote.MOREMATERIAL)){
            result = "moreMaterial";
        } else if (!votesFromProcess.contains(Vote.DECLINE)) {
            result = "approved";
        } else {
            int sum = votesFromProcess.size();
            int declined = (int) votesFromProcess.stream().filter(v -> v.equals(Vote.DECLINE)).count();

            if ((float)declined/sum > 0.5){
                result = "declined";
            } else {
                result = "voteAgain";
            }
        }

        delegateExecution.setVariable("result", result);
    }
}
