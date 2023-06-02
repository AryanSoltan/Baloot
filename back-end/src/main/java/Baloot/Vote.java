package Baloot;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import javax.persistence.OneToOne;
import jakarta.persistence.*;


@Entity
@Table(name = "Vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long voteId;

    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private int  vote;

    public Vote() {
    }

    public Vote(Comment comment, User user, int vote) {
        this.comment = comment;
        this.user = user;
        this.vote = vote;
    }

    public long getVoteId() {
        return voteId;
    }

    public void setVoteId(long voteId) {
        this.voteId = voteId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
//    Commodity commodity;
//    public Vote(User user, int vote)
//    {
//       // this.user = user;
//        this.vote = vote;
//    }

}
