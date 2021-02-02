package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PenaltyPoint implements JavaDelegate {

    @Autowired
    private IReaderService readerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("got a penalty point");
        Map betasThatCommented = (HashMap<String,String>)delegateExecution.getVariable("betasThatCommented");
        List selecteBetaReaders = (ArrayList<String>)delegateExecution.getVariable("selectedBetaReaders");
        List betasThatHaventCommented = new ArrayList();

        for(Object username : selecteBetaReaders){
            if(betasThatCommented.get(username) == null){
               Reader reader = readerService.findByUsername(username.toString());
               reader.setPenaltyPoints(reader.getPenaltyPoints()+1);
               readerService.updateReader(reader);
                betasThatHaventCommented.add(username);
            }
        }
        delegateExecution.setVariable("haventCommented",betasThatHaventCommented);
    }
}
