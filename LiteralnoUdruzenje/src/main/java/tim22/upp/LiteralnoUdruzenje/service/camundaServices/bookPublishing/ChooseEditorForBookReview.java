package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class ChooseEditorForBookReview  implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Choosing one editor to review this book");
    }
}
