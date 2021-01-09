package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tim22.upp.LiteralnoUdruzenje.dto.BookDTO;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.repository.BookRepository;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IWriterService writerService;

    public List<BookDTO> findAll() {
        return convertFromModelToDto(bookRepository.findAll());
    }

    public List<BookDTO> convertFromModelToDto(List<Book> books) {
        List<BookDTO> bookDTOs = new ArrayList<>();
        for(Book b : books){
            BookDTO bookDTO = new BookDTO(b.getName(), b.getWriters(), b.getGenre().getName());
            bookDTOs.add(bookDTO);
        }
        return bookDTOs;
    }

    public Book findBookByName(String name) {
        return bookRepository.findBookByName(name);
    }

    public List<Book> findAllByGenre(Genre genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    @Override
    public List<String> savePdf(List<String> lists, String username) {
        ArrayList<String> fileNames = new ArrayList<>();
        List<String> fileBytes = new ArrayList<>();
        for (String item : lists) {
            if (item.contains("Filename")) {
                String filename = item.split("Filename")[1];
                fileNames.add(filename);
            } else {
                fileBytes.add(item);
            }
        }
        Writer writer = writerService.findByUsername(username);

        List<String> booksSaved = new ArrayList<>();
        for(int i = 0; i < fileNames.size(); i++) {
            Book book = new Book();
            book.setName(fileNames.get(i).split(".pdf")[0]);
            if (writer != null){
                book.getWriters().add(writer);
            }
            //book.setBytes(getFileBytes().get(i));
            convertToPdf(fileBytes.get(i), book);

            Book bookSaved = save(book);
            booksSaved.add(bookSaved.getName());
        }
        return booksSaved;
    }

    @Override
    public Book save(Book book) {
        if (bookRepository.findBookByName(book.getName()) == null) {
            return  bookRepository.save(book);
        }
        return null;
    }

    @Override
    public StreamingResponseBody downloadPDF(String name) throws IOException {
        File file = new File("src/main/resources/pdfs/".concat(name).concat(".pdf"));

        StreamingResponseBody responseBody = outputStream -> {
            Files.copy(file.toPath(), outputStream);
        };
        return responseBody;
    }

    private void convertToPdf(String bytes, Book book) {
        String[] existingFileNamesWithExtenstion = new File("src/main/resources/pdfs").list();
        List<String> existingFileNames = new ArrayList<>();

        boolean oneAlreadyExists = false;
        for (String name : existingFileNamesWithExtenstion) {
            if(name.equals(book.getName() + "(1)"))
                oneAlreadyExists = true;
            existingFileNames.add(name.split(".pdf")[0]);
        }

        for (String name : existingFileNames) {
            if(name.equals(book.getName()) && oneAlreadyExists == false)
                book.setName(book.getName() + "(1)");
            else if (name.contains(book.getName()) && name.contains("(") && name.contains(")")) {
                int fileNumber = Integer.parseInt(name.split("[\\(\\)]")[1]);
                fileNumber++;
                book.setName(name.replace(name.split("[\\(\\)]")[1], String.valueOf(fileNumber)));
                book.setName(book.getName());
            }
        }

        File file = new File("src/main/resources/pdfs/" + book.getName() + ".pdf");

        try (FileOutputStream fos = new FileOutputStream(file);) {
            String realPart = bytes.split(";")[1].split(",")[1];
            byte[] decoder = Base64.getDecoder().decode(realPart);

            fos.write(decoder);
            System.out.println("PDF File Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
