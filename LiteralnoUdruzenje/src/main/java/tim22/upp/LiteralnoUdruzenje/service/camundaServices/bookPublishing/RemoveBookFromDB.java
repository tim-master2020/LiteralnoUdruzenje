package tim22.upp.LiteralnoUdruzenje.service.camundaServices.bookPublishing;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.io.File;

@Service
public class RemoveBookFromDB implements JavaDelegate {

    @Autowired
    private IBookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String bookId = delegateExecution.getVariable("bookId").toString();

        String bookPdf = bookService.findById(Long.parseLong(bookId)).getPdfName();
        if(bookPdf != null) {
            File file = new File("src/main/resources/pdfs/".concat(bookPdf).concat(".pdf"));
            if(file != null) {
                if (file.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
            }
        }
        bookService.removeBookFromDB(Long.parseLong(bookId));
    }
}
