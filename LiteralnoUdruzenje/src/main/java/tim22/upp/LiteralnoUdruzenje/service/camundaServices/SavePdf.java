package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.*;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.util.*;

@Service
public class SavePdf implements JavaDelegate {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IBookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> docs = (HashMap<String, Object>) delegateExecution.getVariable("docs");

        Book book = new Book();
        book.setName(docs.get("uploadPDFs").toString());
        Book bookSaved = bookService.save(book);

        if(bookSaved != null) {
            runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "savedBook", bookSaved);
        }
    }
}
