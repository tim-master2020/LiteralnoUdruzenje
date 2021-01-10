package tim22.upp.LiteralnoUdruzenje.dto;

import tim22.upp.LiteralnoUdruzenje.model.Review;
import tim22.upp.LiteralnoUdruzenje.model.enums.Vote;

public class ReviewDTO {
    private String comment;
    private Vote vote;
    private String writer;
    private String committee;

    public ReviewDTO(){

    }

    public ReviewDTO(String comment, Vote vote, String writer, String committee){
        this.comment = comment;
        this.vote = vote;
        this.writer = writer;
        this.committee = committee;
    }

    public ReviewDTO(Review review){
        this(review.getComment(), review.getVote(), review.getWriter().getUsername(), review.getCommittee().getUsername());
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

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }
}
