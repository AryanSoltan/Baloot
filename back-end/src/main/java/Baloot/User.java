package Baloot;

import Baloot.Exception.CommodityIsNotInBuyList;
//import InterfaceServer.CommodityInterface;

import com.google.common.hash.Hashing;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Entity
@Table(name = "User")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    Long userId;

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    @Column(name = "email")

    private String email;
    @Column(name = "birthDate")

    private String birthDate;
    @Column(name = "address")

    private String address;

    @Column(name = "credit")
    private double credit;

    @ManyToMany(mappedBy = "usersSet")
    private Set<DiscountCode> discountCodeSet = new HashSet<>();


//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "bought_id", referencedColumnName = "buyListId")
//    private BuyList buyList;

//    @OneToMany(cascade=CascadeType.ALL)
//    @JoinColumn(name = "username")
//    private Set<BuyList> buylistSet = new HashSet<>();;

//    @ManyToMany(mappedBy = "usersSet")
//    private Set<BuyList> buyListsSet = new HashSet<>();
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "purchased_id", referencedColumnName = "buyListId")
//    private BuyList purchased;
//    ArrayList<DiscountCode> usedDiscountCodes ;



    public User(String inputUserName, String inputPassword, String inputEmail,
                String inputBirthDate, String inputAddress, double inputCredit)
    {
        username = inputUserName;
        password = inputPassword;
        email = inputEmail;
        birthDate = inputBirthDate;
        address = inputAddress;
        credit = inputCredit;
       // buylistSet = new HashSet<>(BuyList);
//        buyList = new BuyList();
//        purchased = new BuyList();
        // usedDiscountCodes = new ArrayList<DiscountCode>();

    }
    public User(User user) {
        this(user.username, user.password, user.email, user.birthDate, user.address, user.credit);
    }

    public User() {

    }

//    public void buyCommodity(Commodity newCommodity)
//    {
//        CommodityInBuyList commodity = new CommodityInBuyList(newCommodity, 1);
//        buyList.addNewCommodityToBuyList(commodity);
//
//    }

//    public boolean hasBoughtCommodity(int commodityId)
//    {
//        return buyList.contains(commodityId);
//
//    }
//
//    public void removeFromBuyList(int commodityId) throws CommodityIsNotInBuyList {
//        if(!buyList.contains(commodityId))
//        {
//            throw new CommodityIsNotInBuyList(commodityId);
//        }
//        buyList.removeCommodityFromBuyList(commodityId);
//    }
//
//    public ArrayList<Commodity> getBoughtCommodities() {
//        ArrayList<CommodityInBuyList> commodities = buyList.getAllCommodities();
//        ArrayList<Commodity> commoditiesAns = new ArrayList<Commodity>();
//        for(CommodityInBuyList commodityInBuyList: commodities)
//        {
//            commoditiesAns.add(commodityInBuyList.getCommodity());
//        }
//        return commoditiesAns;
//    }

//    public void setBoughtCommitiesEmpty() {
//
//        buyList = new BuyList();
//    }
//    public void setPurchasedCommodityEmpty(){purchased = new BuyList();}
//
//    public void setUSedDiscountCodesEmpty(){usedDiscountCodes = new ArrayList<DiscountCode>();}

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public double getCredit() {
        return credit;
    }

    public Set<DiscountCode> getDiscountCode(){return discountCodeSet;}



    public void addCredit(double incCredit)
    {
        credit += incCredit;
    }
    public void decreaseCredit(double outCredit){credit -= outCredit;}

//    public void addBuyListToPurchasedCommodities()
//    {
//        for(CommodityInBuyList commodity : buyList.getAllCommodities())
//        {
//            purchased.addNewCommodityToBuyList(commodity);
//        }
//    }
//    public void clearBuylist()
//    {
//        if(buyList.hasDiscount())
//        {
//            usedDiscountCodes.add(buyList.getBuylistDiscountCode());
//        }
//        buyList.emptyList();
//    }

//    public BuyList getPurchasedCommodities() {
//        return purchased;
//    }
//
//    public BuyList getBuyList()
//    {
//        return buyList;
//    }

    public boolean passwordMatches(String pass)
    {
        if(password.equals(pass))
            return true;
        return false;
    }

    public boolean emailEquals(String emailAdd)
    {
        if(email.equals(emailAdd))
            return true;
        return false;
    }

    public void passwordGetHash()
    {
        this.password = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        System.out.println(password);
    }

//    public BuyList getPurchased() {
//        return purchased;
//    }
//    public BuyList getBuyList()
//    {
//        return buyList;
//    }
//
//    public BuyList getBought() {
//        return buyList;
//    }

//    public boolean hasUsedDiscountCode(DiscountCode discountCode)
//    {
//        if(usedDiscountCodes.isEmpty())
//            return false;
//        if(usedDiscountCodes.contains(discountCode))
//            return true;
//        return false;
//    }

//    public void addDiscountToBuylist(DiscountCode discountCode)
//    {
//        System.out.println("addDiscountToBuylist"+discountCode.getCode());
//        buyList.setDiscountCode(discountCode);
//        usedDiscountCodes.add(discountCode);
//    }
//
//    public int getNumBought(Integer commodityId) {
//        return buyList.getBuylistNum(commodityId);
//    }
}