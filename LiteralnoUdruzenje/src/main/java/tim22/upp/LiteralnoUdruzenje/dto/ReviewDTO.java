package tim22.upp.LiteralnoUdruzenje.dto;

import tim22.upp.LiteralnoUdruzenje.model.Review;
import tim22.upp.LiteralnoUdruzenje.model.enums.Vote;

public class ReviewDTO {
    private String comment;
    private Vote vote;
    private String writer;

    public ReviewDTO(){

    }

    public ReviewDTO(String comment, Vote vote, String writer){
        this.comment = comment;
        this.vote = vote;
        this.writer = writer;
    }

    public ReviewDTO(Review review){
        this(review.getComment(), review.getVote(), review.getWriter().getUsername());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
