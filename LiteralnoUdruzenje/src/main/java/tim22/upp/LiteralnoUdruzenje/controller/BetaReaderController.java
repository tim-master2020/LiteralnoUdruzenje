package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
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
import tim22.upp.LiteralnoUdruzenje.dto.ExplanationDTO;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;

import java.util.*;

@Controller
@RequestMapping("/api/betareaders")
public class BetaReaderController {

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

    @RequestMapping(method = RequestMethod.POST, value = "/choose-beta-reader/{taskId}")
    public ResponseEntity<?> chooseBetaReader(@RequestBody List<FormSubmissionDTO> betaUsernames, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(betaUsernames);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        List betaReaders = new ArrayList();
        Map betasThatCommented = new HashMap<String,String>();
        for(Object username : map.values()){ ;
            betaReaders.add(username);
        }
        runtimeService.setVariable(task.getProcessInstanceId(), "selectedBetaReaders", betaReaders);
        runtimeService.setVariable(task.getProcessInstanceId(), "betasThatCommented", betasThatCommented);
        map.put("betas",betaReaders);
        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            ResponseEntity responseEntity = new ResponseEntity<>("Validation failed,try again.",HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
