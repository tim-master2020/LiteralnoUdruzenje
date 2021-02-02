package tim22.upp.LiteralnoUdruzenje.service.camundaServices.plagiatorProcess;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormType;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumTypeSpecial;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CheckWhoDidntReview implements JavaDelegate {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List selectedEditors = (List<String>) delegateExecution.getVariable("selectedEditors");
        HashMap editorsThatReviewed = (HashMap<String, String>) delegateExecution.getVariable("editorsThatReviewed");
        int maxToReplace = selectedEditors.size() - editorsThatReviewed.size();

        List editorsThatCantReviewAgain = (List) delegateExecution.getVariable("editorsThatCantReviewAgain");
        for(Object username: selectedEditors){
            editorsThatCantReviewAgain.add(username.toString());
        }
        delegateExecution.setVariable("editorsThatCantReviewAgain",editorsThatCantReviewAgain);

        List allEditors = (List<String>) delegateExecution.getVariable("allEditors");
        List remaningEditors = new ArrayList<>();

        for (Object username : allEditors) {
            if (!editorsThatCantReviewAgain.contains(username.toString())) {
                remaningEditors.add(username);
            }
        }

        delegateExecution.setVariable("remaningEditors", remaningEditors);
        delegateExecution.setVariable("remaningEditorsSize", remaningEditors.size());
        delegateExecution.setVariable("maxReplacment", String.valueOf(maxToReplace));
    }

}

