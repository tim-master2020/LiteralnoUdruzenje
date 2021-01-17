package tim22.upp.LiteralnoUdruzenje.service.camundaServices.Load;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultipleDownloadFilesType;

import java.util.List;

public class LoadPlagiatedBooks implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<String> booksForComparison = (List<String>)delegateTask.getExecution().getVariable("booksForComparison");

        for (FormField field : taskFormFields.getFormFields()) {
            if (field.getId().equals("booksForReview")) {
                MultipleDownloadFilesType filesType = (MultipleDownloadFilesType) field.getType();

                for (String bookName : booksForComparison) {
                    filesType.getValues().put(bookName, bookName);
                }
            }
        }
    }
}
