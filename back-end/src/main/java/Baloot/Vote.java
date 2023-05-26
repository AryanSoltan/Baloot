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

    long vote;
    int userId;
//    Commodity commodity;
//    public Vote(User user, int vote)
//    {
//       // this.user = user;
//        this.vote = vote;
//    }

}
