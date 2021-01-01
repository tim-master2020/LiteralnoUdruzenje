package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.FormFieldsDTO;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.dto.ReaderDTO;
import tim22.upp.LiteralnoUdruzenje.dto.ValidationErrorDTO;
import tim22.upp.LiteralnoUdruzenje.model.*;
import tim22.upp.LiteralnoUdruzenje.security.TokenUtils;
import tim22.upp.LiteralnoUdruzenje.security.auth.JwtAuthenticationRequest;
import tim22.upp.LiteralnoUdruzenje.service.IReaderService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;
import tim22.upp.LiteralnoUdruzenje.service.impl.CustomUserDetailsService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
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

    @Autowired
    IReaderService IReaderService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private IReaderService readerService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private IWriterService writerService;

    @GetMapping(path = "/reg-task/{type}", produces = "application/json")
    public @ResponseBody FormFieldsDTO getFormFieldsReaderRegistration(@PathVariable String type) {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(type);
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> properties = taskFormData.getFormFields();
        return new FormFieldsDTO(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/submit-general-data/{taskId}/{role}", produces = "application/json")
    public ResponseEntity<?> submitRegistrationData(@RequestBody List<FormSubmissionDTO> readerDTO,@PathVariable String taskId, @PathVariable String role) {

        HashMap<String, Object> map = this.mapListToDto(readerDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "registration", map);
        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            ResponseEntity responseEntity = new ResponseEntity<>("Validation failed,try again.",HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (role.equals("writer")){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(nextTask !=  null && formService.getTaskFormData(nextTask.getId()) != null){
            List<FormField> properties = formService.getTaskFormData(nextTask.getId()).getFormFields();
            return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), processInstanceId, properties),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping(path = "/submit-beta-user/{taskId}", produces = "application/json")
    public ResponseEntity<?> submitBetaUser(@RequestBody List<FormSubmissionDTO> dto,@PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "betaGenres", map);
        formService.submitTaskForm(taskId, map);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.GET, value="/confirm-account/{processInstanceId}/{username}")
    public ResponseEntity confirmUserAccount(@PathVariable("username") String username,@PathVariable("processInstanceId") String processInstanceId) throws URISyntaxException {

        User user = userService.findByUsername(username);

        if(!user.isActiveAccount()) {
            runtimeService.setVariable(processInstanceId, "verifed", true);
            URI newUri = new URI("");

            if(user.getRole().equals(Role.READER)){
                user.setActiveAccount(true);
                userService.updateUser(user);
            } else if (user.getRole().equals((Role.WRITER))) {
               Writer writer = writerService.findByUsername(username);
               writer.setVerified(true);
               writerService.updateWriter(writer);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            newUri = new URI("http://localhost:3000/login");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(newUri);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();

        if(user.getRole() == Role.READER) {
            ReaderDTO readerDTO = modelMapper.map(readerService.findByUsername(user.getUsername()), ReaderDTO.class);
            return new ResponseEntity<>(readerDTO, HttpStatus.OK);

        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
