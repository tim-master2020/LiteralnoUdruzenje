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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tim22.upp.LiteralnoUdruzenje.dto.*;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormService formService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<List<BookDTO>> getAll() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-genre")
    public ResponseEntity<List<BookDTO>> getAllByGenre(GenreDTO genreDTO) {
        return new ResponseEntity(bookService.findAllByGenre(modelMapper.map(genreDTO, Genre.class)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save-general-book-data/{taskId}")
    public ResponseEntity<List<BookDTO>> saveGeneralBookData(@RequestBody List<FormSubmissionDTO> bookDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(bookDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "generalBookData", map);
        formService.submitTaskForm(taskId,map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save-general-book-data-review/{taskId}")
    public ResponseEntity<?> submitGeneralBookDataReview(@RequestBody List<FormSubmissionDTO> reviewDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(reviewDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        LinkedHashMap decision = (LinkedHashMap) map.get("decision");
        runtimeService.setVariable(processInstanceId, "result", decision.get("value"));
        formService.submitTaskForm(taskId,decision);

        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        String taskName = nextTask.getName().toString();
        if(nextTask != null && taskName.equals("GiveExplanation")) {
            if(formService.getTaskFormData(nextTask.getId()) != null) {
                List<FormField> properties = formService.getTaskFormData(nextTask.getId()).getFormFields();
                return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), processInstanceId, properties, nextTask.getName()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-explanation/{taskId}")
    public ResponseEntity<?> submitExplanation(@RequestBody List<FormSubmissionDTO> explanationDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(explanationDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        runtimeService.setVariable(task.getProcessInstanceId(),"explanation",map);
        formService.submitTaskForm(taskId,map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-rest-of-work/{taskId}")
    public ResponseEntity<?> submitRestOfBook(@RequestBody List<FormSubmissionDTO> bookDTO, @PathVariable String taskId) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if(task == null){
            return new ResponseEntity<>( new  ExplanationDTO("You failed to upload your book in given time. Your data will not be submitted."),HttpStatus.OK);
        }

        String username = task.getAssignee();
        String processInstanceId = task.getProcessInstanceId();
        List<String> booksSaved = bookService.savePdf((List<String>) bookDTO.get(0).getFieldValue(), username);
        runtimeService.setVariable(processInstanceId, "booksSaved", booksSaved);

        HashMap<String, Object> map = new HashMap<>();
        for (String name : booksSaved){
            map.put("name", name);
        }

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<>(new ExplanationDTO("Uploading book failed."),HttpStatus.BAD_REQUEST);
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



