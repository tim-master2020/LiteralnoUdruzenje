package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;

import java.util.ArrayList;
import java.util.List;

public class CheckPenaltyPoints implements JavaDelegate {

    @Autowired
    private IReaderService readerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List haventCommented = (ArrayList<String>)delegateExecution.getVariable("haventCommented");
        List losingBetaStatus = new ArrayList();
        for(Object username : haventCommented){
            Reader reader = readerService.findByUsername(username.toString());
            if(reader.getPenaltyPoints() >= 5){
                losingBetaStatus.add(username);
            }
        }
        delegateExecution.setVariable("losingBetaStatus",losingBetaStatus);
        delegateExecution.setVariable("losingBetaStatusSUM",losingBetaStatus.size());
    }
}
