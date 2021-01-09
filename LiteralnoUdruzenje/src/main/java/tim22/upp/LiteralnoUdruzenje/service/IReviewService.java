package tim22.upp.LiteralnoUdruzenje.service;

import tim22.upp.LiteralnoUdruzenje.dto.FormSubmissionDTO;
import tim22.upp.LiteralnoUdruzenje.dto.ReviewDTO;
import tim22.upp.LiteralnoUdruzenje.model.Review;
import tim22.upp.LiteralnoUdruzenje.model.Writer;

import java.util.List;

public interface IReviewService {
    List<Review> findByWriter(String username);
    Review saveReview(List<FormSubmissionDTO> formDTO, String username, String committee, String taskId);
    Review save(Review review);
}
