package tim22.upp.LiteralnoUdruzenje.service.impl;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.dto.ReviewDTO;
import tim22.upp.LiteralnoUdruzenje.model.Book;
import tim22.upp.LiteralnoUdruzenje.model.Review;
import tim22.upp.LiteralnoUdruzenje.model.User;
import tim22.upp.LiteralnoUdruzenje.model.Writer;
import tim22.upp.LiteralnoUdruzenje.model.enums.Vote;
import tim22.upp.LiteralnoUdruzenje.repository.ReviewRepository;
import tim22.upp.LiteralnoUdruzenje.service.IBookService;
import tim22.upp.LiteralnoUdruzenje.service.IReviewService;
import tim22.upp.LiteralnoUdruzenje.service.IUserService;
import tim22.upp.LiteralnoUdruzenje.service.IWriterService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IWriterService writerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormService formService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBookService bookService;

    @Override
    public List<Review> findByWriter(String username) {
        Writer writer = writerService.findByUsername(username);
        if(writer == null){
            return null;
        }

        List<Review> reviews = reviewRepository.findByWriter(writer);
//        List<ReviewDTO> reviewDTOS = new ArrayList<>();
//        for(Review review : reviews){
//            ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
//            reviewDTO.setWriter(writer.getUsername());
//            reviewDTOS.add(reviewDTO);
//        }

        return reviews;
    }

    @Override
    public List<ReviewDTO> getAll(Principal principal) {
        Writer writer = writerService.findByUsername(principal.getName());
        if(writer == null){
            return null;
        }

        List<Review> reviews = reviewRepository.findByWriter(writer);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(Review review : reviews){
            ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
            reviewDTO.setWriter(writer.getUsername());
            reviewDTO.setCommittee(review.getReviewer().getUsername());
            reviewDTOS.add(reviewDTO);
        }

        return reviewDTOS;
    }

    @Override
    public Review saveReview(List<FormSubmissionDTO> formDTO, String username, String committee, String taskId) {
        Review review = new Review();
        List<Vote> votes = (List<Vote>) taskService.getVariables(taskId).get("votes");
        List<String> comments = (List<String>) taskService.getVariables(taskId).get("comments");

        Writer writer = writerService.findByUsername(username);
        review.setWriter(writer);

        User user = userService.findByUsername(committee);
        review.setReviewer(user);

        for(FormSubmissionDTO formSubmissionDTO : formDTO){
            if(formSubmissionDTO.getFieldId().equals("reviewComment")){
                review.setComment((String) formSubmissionDTO.getFieldValue());
                comments.add(review.getComment());
            }
            if(formSubmissionDTO.getFieldId().equals("reviewRate")) {
                if (formSubmissionDTO.getFieldValue().equals(Vote.DECLINE.name())) {
                    review.setVote(Vote.DECLINE);
                } else if (formSubmissionDTO.getFieldValue().equals(Vote.APPROVE.name())) {
                    review.setVote(Vote.APPROVE);
                } else if (formSubmissionDTO.getFieldValue().equals(Vote.MOREMATERIAL.name())) {
                    review.setVote(Vote.MOREMATERIAL);
                }

                votes.add(review.getVote());
            }

            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            String processInstanceId = task.getProcessInstanceId();

            runtimeService.setVariable(processInstanceId, "votes", votes);
            runtimeService.setVariable(processInstanceId, "comments", comments);
        }

        return save(review);
    }

    @Override
    public Review saveComment(String comment, String username, String reviewer, String bookName) {
        Review review = new Review();
        review.setComment(comment);
        Writer writer = writerService.findByUsername(username);
        review.setWriter(writer);

        User user = userService.findByUsername(reviewer);
        review.setReviewer(user);

        Book book = bookService.findBookByName(bookName);
        review.setBook(book);

        return save(review);
    }

    @Override
    public Review save(Review review) {
        Review reviewSaved = reviewRepository.save(review);
        return reviewSaved;
    }

}
