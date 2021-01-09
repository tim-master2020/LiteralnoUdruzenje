package tim22.upp.LiteralnoUdruzenje.service.camundaServices.Load;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.EnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultipleDownloadFilesType;

import java.util.List;

@Service
public class LoadPlagiatedFiles implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<String> plagiatedBooks = (List<String>)delegateTask.getExecution().getVariable("plagiatedBooks");

        for (FormField field : taskFormFields.getFormFields()) {
            if (field.getId().equals("files")) {
                MultipleDownloadFilesType filesType = (MultipleDownloadFilesType) field.getType();

                for (String bookName : plagiatedBooks) {
                    filesType.getValues().put(bookName, bookName);
                }
            }
        }
    }
}

