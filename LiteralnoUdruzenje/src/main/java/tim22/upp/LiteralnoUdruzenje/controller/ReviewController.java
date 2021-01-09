package tim22.upp.LiteralnoUdruzenje.controller;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tim22.upp.LiteralnoUdruzenje.dto.BookDTO;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.dto.ReviewDTO;
import tim22.upp.LiteralnoUdruzenje.model.Review;
import tim22.upp.LiteralnoUdruzenje.service.IReviewService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    IReviewService reviewService;

//    @PostMapping(produces = "application/json")
//    public ResponseEntity<ReviewDTO> saveReview(@RequestBody ReviewDTO reviewDTO) {
//        ReviewDTO review = reviewService.save(reviewDTO);
//        return new ResponseEntity<>(review, HttpStatus.OK);
//    }

    @PostMapping(path = "/save-review/{taskId}/{username}", produces = "application/json")
    public ResponseEntity<?> submitReview(@RequestBody List<FormSubmissionDTO> formDTO, @PathVariable String taskId, @PathVariable String username) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String committee = task.getAssignee();
        String processInstanceId = task.getProcessInstanceId();

        Review review = reviewService.saveReview(formDTO, username, committee, taskId);
        runtimeService.setVariable(processInstanceId, "review", review);

        HashMap<String, Object> map = this.mapListToDto(formDTO);

        try {
            formService.submitTaskForm(taskId, map);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<ReviewDTO>> getAllByWriter(Principal principal) {
        List<ReviewDTO> reviews = reviewService.getAll(principal);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
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
