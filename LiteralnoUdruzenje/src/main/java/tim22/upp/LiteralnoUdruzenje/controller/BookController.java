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
        if(nextTask !=  null){
            List<FormField> properties = formService.getTaskFormData(nextTask.getId()).getFormFields();
            return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), processInstanceId, properties,nextTask.getName()),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-explanation/{taskId}")
    public ResponseEntity<?> submitExplanation(@RequestBody List<FormSubmissionDTO> explanationDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(explanationDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        runtimeService.setVariable(task.getProcessInstanceId(),"explanation",map);
        formService.submitTaskForm(taskId,map);
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



