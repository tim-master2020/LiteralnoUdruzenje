package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.FormFieldsDTO;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/writers")
public class WriterController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    IWriterService writerService;

    @GetMapping(path = "/upload-pdf-task/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO getFormFieldUploadPdf(@PathVariable String taskId) {
        return getFormFieldsDTO(taskId);
    }

    @GetMapping(path = "/pay/{taskId}", produces = "application/json")
    public @ResponseBody FormFieldsDTO pay(@PathVariable String taskId) {
        return getFormFieldsDTO(taskId);
    }

    private FormFieldsDTO getFormFieldsDTO(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String piId = task.getProcessInstanceId();
        List<FormField> properties = null;
        String taskName = task.getName();
        if(task !=  null && formService.getTaskFormData(task.getId()) != null) {
            properties = formService.getTaskFormData(task.getId()).getFormFields();
        }
        FormFieldsDTO formFieldsDTO = new FormFieldsDTO(taskId, piId, properties);
        formFieldsDTO.setTaskName(taskName);
        return formFieldsDTO;
    }

    @PostMapping(path = "/activate-account/{taskId}", produces = "application/json")
    public ResponseEntity<?> activate(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String username = task.getAssignee();

        Writer writer = writerService.findByUsername(username);
        writer.setActiveAccount(true);
        writerService.updateWriter(writer);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", username);

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
