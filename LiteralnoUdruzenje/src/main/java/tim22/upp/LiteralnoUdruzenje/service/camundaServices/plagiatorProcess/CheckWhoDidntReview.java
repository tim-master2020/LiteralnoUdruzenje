package tim22.upp.LiteralnoUdruzenje.service.camundaServices.plagiatorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CheckWhoDidntReview implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List allSelectedEditors = (List<String>) delegateExecution.getVariable("selectedEditors");
        HashMap<String,String> editorsThatReviewed = (HashMap<String, String>) delegateExecution.getVariable("editorsThatReviewed");

        List editorsThatHaventReviewed = new ArrayList();

        for(Object username : allSelectedEditors){
            if(editorsThatReviewed.get(username.toString()) == null){
                editorsThatHaventReviewed.add(username);
            }
        }
        delegateExecution.setVariable("editorsThatHaventReviewed",editorsThatHaventReviewed);
    }
}

