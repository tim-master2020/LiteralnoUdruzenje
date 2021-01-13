package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.FormFieldsDTO;
import tim22.upp.LiteralnoUdruzenje.dto.ReviewFormFieldsDTO;
import tim22.upp.LiteralnoUdruzenje.model.Review;
import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;

import java.security.Principal;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcessController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private IUserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/start-process-universal/{key}")
    public ResponseEntity<?> startProcessLoggedOut(@PathVariable String key){
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(key);
        return new ResponseEntity<>(taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0).getId(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/start-process-specific/{key}")
    public ResponseEntity<?> startProcessLoggedIn(Principal principal,@PathVariable String key){
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(key);
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        User user = userService.findByUsername(principal.getName());

        if(user.getRole() == Role.WRITER){
            runtimeService.setVariable(pi.getId(),"loggedInWriter",user.getUsername());
        }else if( user.getRole() == Role.READER){
            runtimeService.setVariable(pi.getId(),"loggedInReader",user.getUsername());
        }else if( user.getRole() == Role.LECTOR){
            runtimeService.setVariable(pi.getId(),"loggedInLector",user.getUsername());
        }else if( user.getRole() == Role.EDITOR){
            runtimeService.setVariable(pi.getId(),"loggedInEditor",user.getUsername());
        }
        return new ResponseEntity<>(new FormFieldsDTO(task.getId(), pi.getId(), null,""),HttpStatus.OK);
    }

    @GetMapping(path = "/get-form-fields/{taskId}", produces = "application/json")
    public ResponseEntity<FormFieldsDTO> getFormFields(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        return new ResponseEntity<>(new FormFieldsDTO(task.getId(),task.getProcessInstanceId(), properties,task.getName()),HttpStatus.OK);
    }

    @GetMapping(path = "/get-form-fields/review/{taskId}", produces = "application/json")
    public ResponseEntity<ReviewFormFieldsDTO> getFormFieldsReview(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        String writer = (String) taskService.getVariables(taskId).get("writer");
        return new ResponseEntity<>(new ReviewFormFieldsDTO(task.getId(),task.getProcessInstanceId(), properties,task.getName(), writer),HttpStatus.OK);
    }

}
