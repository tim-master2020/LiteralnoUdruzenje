package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BetaStatusLoss implements JavaDelegate {

    @Autowired
    IReaderService readerService;

    @Autowired
    TaskService taskService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List losingBetaStatus = (ArrayList<String>) delegateExecution.getVariable("losingBetaStatus");
        for(Object username: losingBetaStatus){
            Reader reader = readerService.findByUsername(username.toString());
            reader.setBetaReader(false);
            reader.setBetaGenres(new HashSet<>());
        }
    }
}
