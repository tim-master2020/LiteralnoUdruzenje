package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

@Service
public class Payment implements JavaDelegate {
    @Autowired
    private IWriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int secondsToSleep = 3;

        try {
            Thread.sleep(secondsToSleep * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        String username = (String) delegateExecution.getVariable("writer");
        Writer writer = writerService.findByUsername(username);
        writer.setActiveAccount(true);
        writerService.updateWriter(writer);
    }
}
