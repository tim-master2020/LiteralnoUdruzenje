package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.task.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.*;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.util.HashMap;
import java.util.List;

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
    public ResponseEntity<?> submitInitialPdfs(@RequestBody List<FormSubmissionDTO> formDTO, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(formDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "docs", map);
        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            ResponseEntity responseEntity = new ResponseEntity<>("Uploading books failed.",HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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
