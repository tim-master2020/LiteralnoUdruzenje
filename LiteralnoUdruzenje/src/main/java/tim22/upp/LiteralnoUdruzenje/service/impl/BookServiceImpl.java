package tim22.upp.LiteralnoUdruzenje.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
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

    @Override
    public List<Book> findAllModels() {
        return bookRepository.findAll();
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
    public Book update(Book book) {
        if (book.getPdfName() == null) {
            return bookRepository.save(book);
        } else if (book.getPdfName() != null && bookRepository.findByPdfName(book.getPdfName()) == null) {
            return bookRepository.save(book);
        }
        return null;
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
        Book book = bookRepository.findBookByName(name);
        File file = new File("src/main/resources/pdfs/".concat(book.getPdfName()).concat(".pdf"));

        StreamingResponseBody responseBody = outputStream -> {
            Files.copy(file.toPath(), outputStream);
        };
        return responseBody;
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public void removeBookFromDB(Long id) {
         bookRepository.removeById(id);
    }

    @Override
    public List<String> savePdf(List<String> lists, String bookName, String username) {
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
        Boolean isInitialUploadOfABook = false;
        for(int i = 0; i < fileNames.size(); i++) {
            Book book = new Book();
            if(bookName == null){
                book.setName(fileNames.get(i).split(".pdf")[0]);
                isInitialUploadOfABook = true;
            } else {
                book = bookRepository.findBookByName(bookName);
            }

            Boolean isPdfUpdate = false;
            if (book.getPdfName() != null) {
                removePdf(book.getPdfName());
                isPdfUpdate = true;
            }
            book.setPdfName(book.getName());

            if (writer != null && !book.getWriters().contains(writer)){
                book.getWriters().add(writer);
            }

            convertToPdf(fileBytes.get(i), book, isInitialUploadOfABook);

            Book bookSaved = new Book();
            if (!isPdfUpdate) {
                if (bookName == null) {
                    bookSaved = save(book);
                } else {
                    bookSaved = update(book);
                }
            } else {
                bookSaved = book;
            }
            booksSaved.add(bookSaved.getName());
        }
        return booksSaved;
    }

    private void convertToPdf(String bytes, Book book, Boolean isInitialUploadOfABook) {
        String[] existingFileNamesWithExtension = new File("src/main/resources/pdfs").list();
        List<String> existingFileNamesWithoutExtenstion = new ArrayList<>();

       boolean oneAlreadyExists = false;
        for (String name : existingFileNamesWithExtension) {
            if(name.equals(book.getPdfName() + ".pdf"))
                oneAlreadyExists = true;
            existingFileNamesWithoutExtenstion.add(name.split(".pdf")[0]);
        }

        for (String name : existingFileNamesWithoutExtenstion) {
            if (name.equals(book.getPdfName()) && oneAlreadyExists == true & !name.contains("(")) {
                book.setPdfName(book.getPdfName() + "(1)");
            }
            else if (name.contains(book.getPdfName()) && name.contains("(") && name.contains(")")) {
                int fileNumber = Integer.parseInt(name.split("[\\(\\)]")[1]);
                fileNumber++;
                book.setPdfName(name.replace(name.split("[\\(\\)]")[1], String.valueOf(fileNumber)));
                book.setPdfName(book.getPdfName());
            }

            if (isInitialUploadOfABook) {
                book.setName(book.getPdfName());
            }
        }

        File file = new File("src/main/resources/pdfs/" + book.getPdfName() + ".pdf");

        try (FileOutputStream fos = new FileOutputStream(file);) {
            String realPart = bytes.split(";")[1].split(",")[1];
            byte[] decoder = Base64.getDecoder().decode(realPart);

            fos.write(decoder);
            System.out.println("PDF File Saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removePdf(String pdfName){
        if(pdfName != null) {
            File file = new File("src/main/resources/pdfs/".concat(pdfName).concat(".pdf"));
            if(file != null) {
                if (file.delete()) {
                    System.out.println("File deleted successfully");
                } else {
                    System.out.println("Failed to delete the file");
                }
            }
        }
    }
}
