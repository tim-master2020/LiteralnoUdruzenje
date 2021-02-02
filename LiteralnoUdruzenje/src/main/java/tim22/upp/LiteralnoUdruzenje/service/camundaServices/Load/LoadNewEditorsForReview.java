package tim22.upp.LiteralnoUdruzenje.service.camundaServices.Load;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormProperty;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.EnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumType;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.util.*;

@Service
public class LoadNewEditorsForReview implements TaskListener {

    @Autowired
    private IUserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {

        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<String> selectedEditors = (List<String>)delegateTask.getExecution().getVariable("selectedEditors");
        List<User> editors = userService.findByRole(Role.EDITOR);

        List replacementEditors = new ArrayList<String>();
        for (User editor : editors) {
            if(!selectedEditors.contains(editor.getUsername())) {
                User user = userService.findByUsername(editor.getUsername());
                replacementEditors.add(editor.getUsername());
            }
        }

        for (FormField field : taskFormFields.getFormFields()) {
            if (field.getId().equals("editors_x")) {

                EnumFormType formType = (EnumFormType) field.getType();
                formType.getValues().clear();

                for (Object entry : replacementEditors) {
                    formType.getValues().put(entry.toString(),entry.toString());
                }
            }
        }
    }
}

