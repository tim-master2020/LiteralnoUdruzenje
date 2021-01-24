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
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumTypeSpecial;
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

        List<String> editorsUsernames = (List<String>)delegateTask.getExecution().getVariable("remaningEditors");

        for (FormField field : taskFormFields.getFormFields()) {
            if (field.getId().contains("editors")) {
                EnumFormType formType = (EnumFormType) field.getType();
                formType.getValues().clear();
                for (String entry : editorsUsernames) {
                    formType.getValues().put(entry, entry);
                }
            }
        }
    }
}
