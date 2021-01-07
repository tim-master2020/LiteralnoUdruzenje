package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;
import tim22.upp.LiteralnoUdruzenje.service.IGenreService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.util.*;

@Service
public class SaveGeneralBookData  implements JavaDelegate {

    @Autowired
    private IBookService bookService;

    @Autowired
    private IGenreService genreService;

    @Autowired
    private IWriterService writerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Saving general book data");

        HashMap<String, Object> generalBookData = (HashMap<String, Object>) delegateExecution.getVariable("generalBookData");
        Book book = new Book();
        book.setName(generalBookData.get("bookTitle").toString());
        book.setSynopsis(generalBookData.get("synopsis").toString());
        LinkedHashMap<String,String> genres = (LinkedHashMap<String, String>) generalBookData.get("Genres");
        //genreService.findByName(genres.get("value"));
        book.setGenre(genreService.findByName(genres.get("value")));

        Set<Writer> writers = book.getAuthors();
        writers.add(writerService.findByUsername(delegateExecution.getVariable("loggedInWriter").toString()));
        book.setAuthors(writers);

        if(bookService.saveBook(book)){
            delegateExecution.setVariable("bookSaved",true);
            delegateExecution.setVariable("bookId",book.getId());
        }else{
            delegateExecution.setVariable("bookSaved",false);
        }
    }
}
