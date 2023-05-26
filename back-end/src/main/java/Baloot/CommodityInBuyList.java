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
    Integer numInStock;

    public CommodityInBuyList(Commodity inputCommodity, Integer inputNumInStock) {
        numInStock = inputNumInStock;
        commodity = inputCommodity;
    }

    public CommodityInBuyList()
    {

    }

    public Commodity getCommodity() {
        return commodity;
    }
    public Integer getNumInStock()
    {
        return numInStock;
    }

    public void increaseOne() {
        numInStock += 1;
    }

    public void decreaseOne() {
        numInStock--;
    }
}
