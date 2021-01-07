package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tim22.upp.LiteralnoUdruzenje.dto.*;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<List<BookDTO>> getAll() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-genre")
    public ResponseEntity<List<BookDTO>> getAllByGenre(GenreDTO genreDTO) {
        return new ResponseEntity(bookService.findAllByGenre(modelMapper.map(genreDTO, Genre.class)), HttpStatus.OK);
    }

    /*@RequestMapping(method = RequestMethod.GET, value = "/by-writer")
    public ResponseEntity<List<BookDTO>> getAllByGenre(WriterDTO writerDTO) {
        return new ResponseEntity(bookService.findAllByWriter(modelMapper.map(writerDTO, Writer.class)), HttpStatus.OK);
    }*/

    @PostMapping(path = "/save-pdfs/{taskId}", produces = "application/json")
    public ResponseEntity<?> submitInitialPdfs(@RequestBody List<FormSubmissionDTO> formDTO, @PathVariable String taskId, Principal principal) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String username = task.getAssignee();
        String processInstanceId = task.getProcessInstanceId();

        List<String> booksSaved = bookService.savePdf((List<String>) formDTO.get(0).getFieldValue(), username);
        runtimeService.setVariable(processInstanceId, "booksSaved", booksSaved);

        HashMap<String, Object> map = new HashMap<>();
        for (String name : booksSaved){
            map.put("name", name);
        }

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            ResponseEntity responseEntity = new ResponseEntity<>("Uploading books failed.",HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/book-review/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO getFormFieldsBookReview(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        List<String> bookNames = (List<String>) taskService.getVariables(taskId).get("booksSaved");
        String writer = (String) taskService.getVariables(taskId).get("writer");
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        return new ReviewFormFieldsDTO(task.getId(), task.getProcessInstanceId(), properties, bookNames, writer);
    }

    @GetMapping(path = "/download/{name:.+}")
    public ResponseEntity<StreamingResponseBody> downloadBook(@PathVariable String name) throws IOException {
        StreamingResponseBody response = bookService.downloadPDF(name);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generic_file_name.bin")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDTO> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDTO temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }
}
