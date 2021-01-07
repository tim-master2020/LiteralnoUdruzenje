package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

@Service
public class RemoveBookFromDB implements JavaDelegate {

    @Autowired
    private IBookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String bookId = delegateExecution.getVariable("bookId").toString();
        boolean isremoved = bookService.removeBookFromDB(Long.parseLong(bookId));
    }
}
