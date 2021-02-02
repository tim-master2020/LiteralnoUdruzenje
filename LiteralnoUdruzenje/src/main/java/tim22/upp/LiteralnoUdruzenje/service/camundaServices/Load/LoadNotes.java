package tim22.upp.LiteralnoUdruzenje.service.camundaServices.Load;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.EnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultipleDownloadFilesType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoadNotes implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        HashMap<String, String> notesMap = (HashMap<String, String>) delegateTask.getExecution().getVariable("editorsThatReviewed");

        for (FormField field : taskFormFields.getFormFields()) {
            if (field.getId().equals("notes")) {
                MultipleDownloadFilesType filesType = (MultipleDownloadFilesType) field.getType();
                filesType.getValues().clear();
                for (Map.Entry<String, String> note : notesMap.entrySet()) {
                    filesType.getValues().put(note.getValue(), note.getKey());
                }
            }
        }
    }
}
