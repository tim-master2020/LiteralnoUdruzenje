package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class SubmitToPlagiatorSystem implements JavaDelegate {

    @Autowired
    private IBookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<Book> books = bookService.findAllModels();
        List<String> currentBook = (List<String>) delegateExecution.getVariable("booksSaved");

        List<String> bookNames =  new ArrayList<>();
        Random r = new Random();
        for(int i=0;i<3;i++){
            int result = r.nextInt(books.size()-0) + 0;
            if(!bookNames.contains(books.get(result).getName()) && !books.get(result).getName().equals(currentBook.get(0))){
                bookNames.add(books.get(result).getName());
            }
        }
        delegateExecution.setVariable("plagiatedBooks",bookNames);
    }
}
