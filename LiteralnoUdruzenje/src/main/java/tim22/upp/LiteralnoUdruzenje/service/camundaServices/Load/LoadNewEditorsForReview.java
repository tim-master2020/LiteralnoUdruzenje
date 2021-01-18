package tim22.upp.LiteralnoUdruzenje.service.camundaServices.Load;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.EnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumType;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoadNewEditorsForReview implements TaskListener {

    @Autowired
    private IUserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {

        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<String> selectedEditors = (List<String>)delegateTask.getExecution().getVariable("selectedEditors");
        List<User> editors = userService.findByRole(Role.EDITOR);

        HashMap<String, String> replacementEditors = new HashMap<>();
        for (User editor : editors) {
            if(!selectedEditors.contains(editor.getUsername())) {
                User user = userService.findByUsername(editor.getUsername());
                replacementEditors.put(editor.getUsername(), user.getId());
            }
        }

        for (FormField field : taskFormFields.getFormFields()) {
            if (field.getId().equals("replacmentEditors")) {
                EnumFormType formType = null;
                if(field.getType().getName().contains("multi")) {
                    formType = (MultiEnumType) field.getType();
                }else{
                    formType = (EnumType) field.getType();
                }

                formType.getValues().clear();

                for (Map.Entry<String, String> entry : replacementEditors.entrySet()) {
                    formType.getValues().put(entry.getValue(), entry.getKey());
                }
            }
        }
    }
}

