package Baloot;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "CommodityInBuyList")
public class CommodityInBuyList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commodityInBuyListId;
    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "id")
    Commodity commodity;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "buyListId")
    private BuyList buyList;


    Integer numInStock;

    boolean isBought;

    public CommodityInBuyList(User inputuser, Commodity inputCommodity,BuyList inputbuylist, Integer inputNumInStock) {
        user = inputuser;
        numInStock = inputNumInStock;
        commodity = inputCommodity;
        buyList =inputbuylist;
        isBought=false;
    }

    public CommodityInBuyList()
    {

    }

    public Commodity getCommodity() {
        return commodity;
    }

    public User getUser() {
        return user;
    }
    public Integer getNumInStock()
    {
        return numInStock;
    }

    public boolean getIsBought(){return isBought;}

    public void setIsBought(boolean bought){this.isBought = bought;}

    public void updateNumInStock(int count) {numInStock += count;}

    public void increaseOne() {
        numInStock += 1;
    }

    public void decreaseOne() {
        numInStock--;
    }
}
