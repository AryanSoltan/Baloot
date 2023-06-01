package Baloot;


import jakarta.persistence.*;
import java.util.*;
@Entity
@Table(name="DiscountCode")
public class DiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long discountId;
    String discountCode;
    int discount;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(joinColumns = {@JoinColumn(name="discountId")}, inverseJoinColumns = {@JoinColumn(name="username")})
    private Set<User> usersSet = new HashSet<>();

    public String getCode() {return discountCode; }

//    public Set<User> getbuyLists(){return buyListsSet;}

    public int getPercentage(){return discount;}

    public DiscountCode(String inputCode, int inputPercentage){
        discountCode = inputCode;
        discount = inputPercentage;

    }
    public DiscountCode(){}

    public long getDiscountId(){return discountId;}

    public Set<User> getUsersSet(){return usersSet;}
}
