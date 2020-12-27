package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.*;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.io.File;
import java.io.FileOutputStream;
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
        ArrayList<String> listOfPdfBytes = ((ArrayList<String>) docs.get("uploadPDFs"));
        int count = 0;
        for (String pdfBytesItem : listOfPdfBytes) {
            Book book = new Book();
            book.setName("ime");
            book.setBytes(pdfBytesItem);
            convertToPdf(book.getBytes(), book, count);
            count++;

            Book bookSaved = bookService.save(book);

            if (bookSaved != null) {
                runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "savedBook", bookSaved);
            }
        }
    }

    private void convertToPdf(String bytes, Book book, int count){
        File file = new File("src/main/resources/pdfs/" + book.getName() + toString().valueOf(count) + ".pdf");

        try (FileOutputStream fos = new FileOutputStream(file); ) {
            String realPart = bytes.split(";")[1].split(",")[1];
            byte[] decoder = Base64.getDecoder().decode(realPart);

            fos.write(decoder);
            System.out.println("PDF File Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
