package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.CustomType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.EnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultiEnumType;
import tim22.upp.LiteralnoUdruzenje.model.camundaCustomTypes.MultipleDownloadFilesType;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomTypeProcessEnginePlugin extends AbstractProcessEnginePlugin {

    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        if (processEngineConfiguration.getCustomFormTypes() == null) {
            processEngineConfiguration.setCustomFormTypes(new ArrayList<AbstractFormFieldType>());
        }

        List<AbstractFormFieldType> formTypes = processEngineConfiguration.getCustomFormTypes();
        formTypes.add(new MultiEnumType("genres"));
        formTypes.add(new EnumType("genre"));
        formTypes.add(new CustomType("password"));
        formTypes.add(new CustomType("email"));
        formTypes.add(new CustomType("textArea"));
        formTypes.add(new CustomType("label"));
        formTypes.add(new CustomType("input_file"));
        formTypes.add(new CustomType("input_single"));
        formTypes.add(new MultipleDownloadFilesType("multiFilesDownload"));
    }
}
