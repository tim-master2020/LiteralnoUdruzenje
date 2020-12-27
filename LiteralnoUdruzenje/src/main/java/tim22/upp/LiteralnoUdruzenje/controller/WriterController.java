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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.FormFieldsDTO;

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

    @GetMapping(path = "/upload-pdf-task", produces = "application/json")
    public @ResponseBody FormFieldsDTO getFormFieldUploadPdf() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("WriterRegistration");
        Task task = taskService.createTaskQuery().taskId("UploadPDFForm").singleResult();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        return new FormFieldsDTO(task.getId(), pi.getId(), properties);
    }
}
