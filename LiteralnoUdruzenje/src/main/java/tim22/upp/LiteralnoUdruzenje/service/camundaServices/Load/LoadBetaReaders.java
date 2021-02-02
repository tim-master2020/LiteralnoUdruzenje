package tim22.upp.LiteralnoUdruzenje.service.camundaServices.Load;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.BetaReaderDTO;
import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
import tim22.upp.LiteralnoUdruzenje.model.Reader;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.EnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultipleDownloadFilesType;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoadBetaReaders implements TaskListener {

    @Autowired
    private IReaderService readerService;
    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
        List<String> betareadersUsernames = (List<String>)delegateTask.getExecution().getVariable("betaReaders");
        HashMap<String, String> dict = new HashMap<>();

        for (String username : betareadersUsernames) {

            Reader reader = readerService.findByUsername(username);
            dict.put(username, reader.getId());
        }

        for (FormField field : taskFormFields.getFormFields()) {
            if (field.getId().equals("betas")) {
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