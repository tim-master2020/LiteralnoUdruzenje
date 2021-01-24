package tim22.upp.LiteralnoUdruzenje.service.camundaServices.plagiatorProcess;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

@Service
public class BookIsPlagiat implements JavaDelegate {

    @Autowired
    private IBookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Book book = bookService.findBookByName(delegateExecution.getVariable("complaneeBook").toString());
        book.setPlagiat(true);
    }
}
