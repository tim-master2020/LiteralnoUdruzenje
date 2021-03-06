package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tim22.upp.LiteralnoUdruzenje.dto.*;
import tim22.upp.LiteralnoUdruzenje.model.Genre;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;
import tim22.upp.LiteralnoUdruzenje.service.IReviewService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.Principal;
import java.util.*;
import java.io.IOException;
import java.util.stream.Stream;

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

    @Autowired
    private IReviewService reviewService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<List<BookDTO>> getAll() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/by-genre")
    public ResponseEntity<List<BookDTO>> getAllByGenre(GenreDTO genreDTO) {
        return new ResponseEntity(bookService.findAllByGenre(modelMapper.map(genreDTO, Genre.class)), HttpStatus.OK);
    }

    @PostMapping(path = "/save-pdfs/{taskId}", produces = "application/json")
    public ResponseEntity<?> submitInitialPdfs(@RequestBody List<FormSubmissionDTO> formDTO, @PathVariable String taskId, Principal principal) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String username = task.getAssignee();
        String processInstanceId = task.getProcessInstanceId();
        List<String> booksSaved = bookService.savePdf((List<String>) formDTO.get(0).getFieldValue(), null, username);

        runtimeService.setVariable(processInstanceId, "booksSaved", booksSaved);

        HashMap<String, Object> map = new HashMap<>();
        Object name = formDTO.get(0).getFieldValue();
        ArrayList fileNames =(ArrayList<String>) name;
        List subList = fileNames.subList(0,fileNames.size()/2);

        String stringFileNames = "";
        for(Object oneFileName : subList){
            if(subList.get(subList.size()-1) == oneFileName){
                stringFileNames = stringFileNames + oneFileName;
            }else {
                stringFileNames = stringFileNames + oneFileName.toString() + ",";
            }
        }

        HashMap<String,Object> files = new HashMap<>();
        files.put(formDTO.get(0).getFieldId(),stringFileNames);

        try {
            formService.submitTaskForm(taskId, files);
        }catch (Exception e){
            ResponseEntity responseEntity = new ResponseEntity<>("Uploading books failed.",HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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

        map.put("decision",decision.get("value"));
        formService.submitTaskForm(taskId,map);

        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(nextTask != null && task.getName().equals("GiveExplanation")) {
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
        String bookName = (String) taskService.getVariables(taskId).get("bookName");
        List<String> booksSaved = bookService.savePdf((List<String>) bookDTO.get(0).getFieldValue(), bookName, username);
        runtimeService.setVariable(processInstanceId, "booksSaved", booksSaved);

        HashMap<String, Object> map = new HashMap<>();
        for (String name : booksSaved){
            map.put("name", name);
        }

        Object name = bookDTO.get(0).getFieldValue();
        ArrayList fileNames =(ArrayList<String>) name;
        List subList = fileNames.subList(0,fileNames.size()/2);
        String stringFileNames = "";
        for(Object oneFileName : subList){
            if(subList.get(subList.size()-1) == oneFileName){
                stringFileNames = stringFileNames + oneFileName;
            }else {
                stringFileNames = stringFileNames + oneFileName.toString() + ",";
            }
        }

        HashMap<String,Object> files = new HashMap<>();
        files.put(bookDTO.get(0).getFieldId(),stringFileNames);

        try {
            formService.submitTaskForm(taskId, files);
        }catch (Exception e){
            return new ResponseEntity<>(new ExplanationDTO("Uploading book failed."),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/download/{name:.+}")
    public ResponseEntity<StreamingResponseBody> downloadBook(@PathVariable String name) throws IOException {
        StreamingResponseBody response = bookService.downloadPDF(name);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generic_file_name.bin")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-plagiarism-decision/{taskId}")
    public ResponseEntity<?> submitPlagiarismDecision(@RequestBody List<FormSubmissionDTO> plagiatDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(plagiatDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String plagiat = (String) map.get("isPlagiat");
        runtimeService.setVariable(processInstanceId, "isPlagiat", plagiat);

        //LinkedHashMap isPlagiat = (LinkedHashMap) map.get("isPlagiat");
        //map.put("isPlagiat",isPlagiat.get("isPlagiat"));
        formService.submitTaskForm(taskId,map);

        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(nextTask != null && nextTask.getName().equals("DownloadFile")) {
            if(formService.getTaskFormData(nextTask.getId()) != null) {
                List<FormField> properties = formService.getTaskFormData(nextTask.getId()).getFormFields();
                return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), processInstanceId, properties, nextTask.getName()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-approval/{taskId}")
    public ResponseEntity<?> submitApproval(@RequestBody List<FormSubmissionDTO> plagiatDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(plagiatDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String approval = (String) map.get("approval");
        runtimeService.setVariable(processInstanceId, "isApproved", approval);
        formService.submitTaskForm(taskId,map);

        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(nextTask != null) {
            if(formService.getTaskFormData(nextTask.getId()) != null) {
                List<FormField> properties = formService.getTaskFormData(nextTask.getId()).getFormFields();
                return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), processInstanceId, properties, nextTask.getName()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-sending-to-beta/{taskId}")
    public ResponseEntity<?> submitSendingToBetaReaders(@RequestBody List<FormSubmissionDTO> plagiatDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(plagiatDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String decision = (String) map.get("sendToBetaReaders");
        runtimeService.setVariable(processInstanceId, "sendToBeta", decision);
        formService.submitTaskForm(taskId,map);

        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(nextTask != null) {
            if(formService.getTaskFormData(nextTask.getId()) != null) {
                List<FormField> properties = formService.getTaskFormData(nextTask.getId()).getFormFields();
                return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), processInstanceId, properties, nextTask.getName()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-comment/{taskId}")
    public ResponseEntity<?> submitComment(@RequestBody List<FormSubmissionDTO> commentDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(commentDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map betasThatCommented = (HashMap<String,String>)runtimeService.getVariable(task.getProcessInstanceId(),"betasThatCommented");
        betasThatCommented.put(task.getAssignee(),commentDTO.get(1).getFieldValue());
        String comment = (String) commentDTO.get(1).getFieldValue();
        String writer = (String) taskService.getVariables(taskId).get("loggedInWriter");
        String bookName = (String) taskService.getVariables(taskId).get("bookName");
        reviewService.saveComment(comment, writer, task.getAssignee(), bookName);

        runtimeService.setVariable(task.getProcessInstanceId(),"betasThatCommented",betasThatCommented);
        formService.submitTaskForm(taskId,map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/submit-updated-book/{taskId}")
    public ResponseEntity<?> submitUpdatedBook(@RequestBody List<FormSubmissionDTO> bookDTO, @PathVariable String taskId) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        String username = task.getAssignee();
        String bookName = (String) taskService.getVariables(taskId).get("bookName");
        List<String> booksSaved = bookService.savePdf((List<String>) bookDTO.get(0).getFieldValue(), bookName, username);
        runtimeService.setVariable(task.getProcessInstanceId(),"booksSaved",booksSaved);

        HashMap<String, Object> map = new HashMap<>();
        for (String name : booksSaved){
            map.put("name", name);
        }

        Object name = bookDTO.get(0).getFieldValue();
        ArrayList fileNames =(ArrayList<String>) name;
        List subList = fileNames.subList(0,fileNames.size()/2);

        String stringFileNames = "";
        for(Object oneFileName : subList){
            if(subList.get(subList.size()-1) == oneFileName){
                stringFileNames = stringFileNames + oneFileName;
            }else {
                stringFileNames = stringFileNames + oneFileName.toString() + ",";
            }
        }

        HashMap<String,Object> files = new HashMap<>();
        files.put(bookDTO.get(0).getFieldId(),stringFileNames);

        try {
            formService.submitTaskForm(taskId, files);
        }catch (Exception e){
            return new ResponseEntity<>(new ExplanationDTO("Uploading book failed."),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editor-review/{taskId}")
    public ResponseEntity<?> submitNewChangesDecision(@RequestBody List<FormSubmissionDTO> decisionDTO, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(decisionDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String decision = (String) map.get("changesNeeded");
        String comment = (String) map.get("commentFromEditor");
        String writer = (String) taskService.getVariables(taskId).get("loggedInWriter");
        String bookName = (String) taskService.getVariables(taskId).get("bookName");
        reviewService.saveComment(comment, writer, task.getAssignee(), bookName);

        runtimeService.setVariable(processInstanceId, "changesNeeded", decision);
        runtimeService.setVariable(processInstanceId, "editorsComment", comment);
        formService.submitTaskForm(taskId,map);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/lector-review/{taskId}")
    public ResponseEntity<?> submitLectorDecision(@RequestBody List<FormSubmissionDTO> decisionDTO, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(decisionDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String decision = (String) map.get("changesNeededLector");
        String comment = (String) map.get("commentFromLector");
        String writer = (String) taskService.getVariables(taskId).get("loggedInWriter");
        String bookName = (String) taskService.getVariables(taskId).get("bookName");
        reviewService.saveComment(comment, writer, task.getAssignee(), bookName);

        runtimeService.setVariable(processInstanceId, "lectorChangesNeeded", decision);
        runtimeService.setVariable(processInstanceId, "lectorsComment", comment);
        formService.submitTaskForm(taskId,map);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/main-editor-review/{taskId}")
    public ResponseEntity<?> submitMainEditorDecision(@RequestBody List<FormSubmissionDTO> decisionDTO, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(decisionDTO);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String decision = (String) map.get("changesNeededMainEditor");
        String comment = (String) map.get("commentMainEditor");
        String writer = (String) taskService.getVariables(taskId).get("loggedInWriter");
        String bookName = (String) taskService.getVariables(taskId).get("bookName");
        reviewService.saveComment(comment, writer, task.getAssignee(), bookName);

        runtimeService.setVariable(processInstanceId, "mainEditorChangesNeeded", decision);
        runtimeService.setVariable(processInstanceId, "mainEditorsComment", comment);
        formService.submitTaskForm(taskId,map);

        Task nextTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        if(nextTask != null) {
            if(formService.getTaskFormData(nextTask.getId()) != null) {
                List<FormField> properties = formService.getTaskFormData(nextTask.getId()).getFormFields();
                return new ResponseEntity<>(new FormFieldsDTO(nextTask.getId(), processInstanceId, properties, nextTask.getName()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/print-book/{taskId}")
    public ResponseEntity<?> sendBookToPrinting(@RequestBody List<FormSubmissionDTO> decisionDTO, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(decisionDTO);
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



