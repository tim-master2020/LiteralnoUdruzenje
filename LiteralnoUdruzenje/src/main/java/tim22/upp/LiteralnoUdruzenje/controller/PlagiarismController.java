package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.BookDTO;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/plagiarism", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlagiarismController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private IUserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/file-complaint/{taskId}")
    public ResponseEntity<?> fileComplaint(@RequestBody List<FormSubmissionDTO> complaintDTO, @PathVariable String taskId, Principal principal) {
        HashMap<String, Object> map = this.mapListToDto(complaintDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        List booksForComparison = new ArrayList<String>();
        booksForComparison.add(complaintDTO.get(1).getFieldValue());
        booksForComparison.add(complaintDTO.get(2).getFieldValue());

        runtimeService.setVariable(processInstanceId, "complaintData", map);
        runtimeService.setVariable(processInstanceId, "booksForComparison", booksForComparison);
        String username = principal.getName();
        User mainEditor = userService.findMainEditor();
        runtimeService.setVariable(processInstanceId, "writerWithComplaint", username);
        runtimeService.setVariable(processInstanceId, "mainEditor", mainEditor.getUsername());
        try {
            formService.submitTaskForm(taskId, map);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/choose-editors/{taskId}")
    public ResponseEntity<?> chooseEditors(@RequestBody List<FormSubmissionDTO> editorsUsernames, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(editorsUsernames);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        List editors = new ArrayList();

        for(Object username : map.values()){ ;
            editors.add(username);
        }
        runtimeService.setVariable(task.getProcessInstanceId(), "selectedEditors", editors);
        runtimeService.setVariable(task.getProcessInstanceId(),"editorsThatReviewed", new HashMap<String,String>());

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            ResponseEntity responseEntity = new ResponseEntity<>("Validation failed,try again.",HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-review/{taskId}")
    public ResponseEntity<?> submitReviewAndNotes(@RequestBody List<FormSubmissionDTO> reviewDTO,@PathVariable String taskId,Principal principal){
        HashMap<String, Object> map = this.mapListToDto(reviewDTO);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        HashMap<String,String> editorsThatReviewed = (HashMap<String,String>) runtimeService.getVariable(task.getProcessInstanceId(),"editorsThatReviewed");
        String username = task.getAssignee();
        editorsThatReviewed.put(username,reviewDTO.get(1).getFieldValue().toString());
        runtimeService.setVariable(processInstanceId,"editorsThatReviewed",editorsThatReviewed);

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
