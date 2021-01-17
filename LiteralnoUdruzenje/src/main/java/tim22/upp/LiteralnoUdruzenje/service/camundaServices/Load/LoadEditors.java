package tim22.upp.LiteralnoUdruzenje.service.camundaServices.Load;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
public class LoadEditors implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
