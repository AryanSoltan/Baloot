package Baloot;

import javax.persistence.OneToOne;

public class Vote {

    int vote;

    int userId;
    Commodity commodity;
    public Vote(User user, int vote)
    {
       // this.user = user;
        this.vote = vote;
    }

}
