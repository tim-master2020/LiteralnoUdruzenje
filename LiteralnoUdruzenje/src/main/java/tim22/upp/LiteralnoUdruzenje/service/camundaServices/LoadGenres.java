package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.EnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumType;
import tim22.upp.LiteralnoUdruzenje.service.IGenreService;

import java.util.List;

@Service
public class LoadGenres implements TaskListener {

        @Autowired
        IGenreService genreService;

        public void notify(DelegateTask delegateTask) {
            TaskFormData taskFormFields = delegateTask.getExecution().getProcessEngineServices().getFormService().getTaskFormData(delegateTask.getId());
            List<GenreDTO> genres = genreService.findAllGenres();

            for (FormField field : taskFormFields.getFormFields()) {
                if (field.getId().equals("Genres")) {
                    EnumFormType formType = null;
                    if(field.getType().getName().contains("multi")) {
                         formType = (MultiEnumType) field.getType();
                    }else{
                         formType = (EnumType) field.getType();
                    }

                    for (GenreDTO genre : genres) {
                        formType.getValues().put(Long.toString(genre.getId()), genre.getName());
                    }
                }
            }
        }

}
