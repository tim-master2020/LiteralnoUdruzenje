package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.GenreDTO;
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
                    MultiEnumType multipleEnumFormType = (MultiEnumType) field.getType();

                    for (GenreDTO genre : genres) {
                        multipleEnumFormType.getValues().put(Long.toString(genre.getId()), genre.getName());
                    }
                }
            }
        }

}
