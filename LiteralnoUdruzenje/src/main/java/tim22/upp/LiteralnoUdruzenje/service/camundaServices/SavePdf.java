package tim22.upp.LiteralnoUdruzenje.service.camundaServices;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.model.*;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

@Service
public class SavePdf implements JavaDelegate {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private IWriterService writerService;

//    private ArrayList<String> fileBytes = new ArrayList<>();
//
//    public ArrayList<String> getFileBytes() {
//        return fileBytes;
//    }
//
//    public void setFileBytes(ArrayList<String> fileBytes) {
//        this.fileBytes = fileBytes;
//    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        HashMap<String, Object> docs = (HashMap<String, Object>) delegateExecution.getVariable("docs");
        ArrayList<String> all = ((ArrayList<String>) docs.get("uploadPDFs"));
        ArrayList<String> fileNames = new ArrayList<>();
        List<String> fileBytes = new ArrayList<>();
        for (String item : all) {
            if (item.contains("Filename")) {
                String filename = item.split("Filename")[1];
                fileNames.add(filename);
            } else {
                fileBytes.add(item);
            }
        }
        String username = (String) delegateExecution.getVariable("writer");
        Writer writer = writerService.findByUsername(username);

        List<String> booksSaved = new ArrayList<>();
        for(int i = 0; i < fileNames.size(); i++) {
            Book book = new Book();
            book.setName(fileNames.get(i));
            if (writer != null){
                book.getWriters().add(writer);
            }
            //book.setBytes(getFileBytes().get(i));
            convertToPdf(fileBytes.get(i), book);

            Book bookSaved = bookService.save(book);
            booksSaved.add(bookSaved.getName());

        }

        runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "savedBooks", booksSaved);
    }

    private void convertToPdf(String bytes, Book book){
        File file = new File("src/main/resources/pdfs/" + book.getName());

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
