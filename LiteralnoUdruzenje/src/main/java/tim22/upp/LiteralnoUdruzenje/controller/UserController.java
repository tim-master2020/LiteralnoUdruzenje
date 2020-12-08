package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tim22.upp.LiteralnoUdruzenje.dto.FormFieldsDTO;

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

    @GetMapping(path = "/regtask", produces = "application/json")
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
}
