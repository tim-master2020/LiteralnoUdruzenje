package tim22.upp.LiteralnoUdruzenje.model;

import tim22.upp.LiteralnoUdruzenje.model.enums.Vote;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column
    private String comment;

    @Column
    private Vote vote;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name="writer_id")
    private Writer writer;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private User committee;

    public Review(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public User getCommittee() {
        return committee;
    }

    public void setCommittee(User committee) {
        this.committee = committee;
    }
}
