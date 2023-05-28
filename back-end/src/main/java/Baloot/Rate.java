package Baloot;


import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="Rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long rateId;

    @Column(nullable = false)
    private double rateValue;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id")
    private Commodity commodity;

    public Rate(User user, Commodity commodity, double rateValue){
        this.user = user;
        this.commodity = commodity;
        this.rateValue = rateValue;
    }
    public Rate(){}

    public String getUser() {
        return user.getName();
    }

    public double getRate() {
        return  rateValue;
    }
}
