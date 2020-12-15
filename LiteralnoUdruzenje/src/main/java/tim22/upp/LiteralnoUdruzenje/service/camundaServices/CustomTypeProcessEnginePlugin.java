package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.MultiEnumType;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomTypeProcessEnginePlugin extends AbstractProcessEnginePlugin {

    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        if (processEngineConfiguration.getCustomFormTypes() == null) {
            processEngineConfiguration.setCustomFormTypes(new ArrayList<AbstractFormFieldType>());
        }

        List<AbstractFormFieldType> formTypes = processEngineConfiguration.getCustomFormTypes();
        //formTypes.add(new PasswordFormType());
        //formTypes.add(new FileFormType());
        formTypes.add(new MultiEnumType("genres"));
        //formTypes.add(new EmailStringFormType("email"));

    }
}
