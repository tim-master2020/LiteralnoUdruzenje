package tim22.upp.LiteralnoUdruzenje.service.camundaServices.Load;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.EnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumType;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoadEditors implements TaskListener {

    @Autowired
    private IUserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<String> editorsUsernames = (List<String>)delegateTask.getExecution().getVariable("editorsFromDatabase");
        HashMap<String, String> dict = new HashMap<>();

        for (String username : editorsUsernames) {

            User user = userService.findByUsername(username);
            dict.put(username, user.getId());
        }

        for (FormField field : taskFormFields.getFormFields()) {
            if (field.getId().equals("editors")) {
                EnumFormType formType = null;
                if(field.getType().getName().contains("multi")) {
                    formType = (MultiEnumType) field.getType();
                }else{
                    formType = (EnumType) field.getType();
                }

                for (Map.Entry<String, String> entry : dict.entrySet()) {
                    formType.getValues().put(entry.getValue(), entry.getKey());
                }
            }
        }
    }
}
