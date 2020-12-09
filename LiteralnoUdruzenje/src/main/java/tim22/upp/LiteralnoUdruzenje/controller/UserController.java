package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.DelegationState;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.FormFieldsDTO;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.dto.ReaderDTO;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @GetMapping(path = "/reg-task-user", produces = "application/json")
    public @ResponseBody FormFieldsDTO getFormFieldsReaderRegistration() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("ReaderRegistration");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        return new FormFieldsDTO(task.getId(), pi.getId(), properties);
    }

    @GetMapping(path = "/logintask", produces = "application/json")
    public @ResponseBody FormFieldsDTO getFormFieldsLoginForm() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("LoginProcess");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        return new FormFieldsDTO(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/submit-reg-data/{taskId}", produces = "application/json")
    public ResponseEntity<?> submitRegistrationData(@RequestBody List<FormSubmissionDTO> readerDTO,@PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(readerDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "registration", readerDTO);
        formService.submitTaskForm(taskId, map);

        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(nextTask !=  null && formService.getTaskFormData(nextTask.getId()) != null){
            List<FormField> properties = formService.getTaskFormData(nextTask.getId()).getFormFields();
            return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), processInstanceId, properties),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
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
