package Baloot;

import Baloot.Exception.CommodityOutOfStock;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "BuyList")
public class BuyList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long buyListId;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BUY_LIST_COMMODITIES", joinColumns = @JoinColumn(name = "buyListId"),
    inverseJoinColumns = @JoinColumn(name = "commodityId"))
    Set<CommodityInBuyList> commoditiesList = new HashSet<>();

    @OneToOne(mappedBy = "buyList")
    private DiscountCode buylistDiscountCode;

//    public void addNewCommodityToBuyList(CommodityInBuyList newCommodity)
//    {
//        if(!commoditiesList.containsKey(newCommodity.getCommodity().getId()))
//        {
//            commoditiesList.put(newCommodity.getCommodity().getId(), newCommodity.getCommodity());
//            commoditiesCount.put(newCommodity.getCommodity().getId(),newCommodity.getNumInStock());
//        }
//        else{
//            int currCount = commoditiesCount.get(newCommodity.getCommodity().getId());
//            commoditiesCount.put(newCommodity.getCommodity().getId(),currCount+newCommodity.getNumInStock());
//
//        }
//
//    }
//
//    public boolean contains(int commodityID)
//    {
//        return commoditiesList.containsKey(commodityID);
//    }
//
//    public void removeCommodityFromBuyList(int commodityID)
//    {
//        int currCount = commoditiesCount.get(commodityID);
//        if(currCount>=2) {
//            commoditiesCount.put(commodityID, currCount -1);
//        }
//        if(currCount==1)
//            commoditiesList.remove(commodityID);
//    }
//
//    public ArrayList<CommodityInBuyList> getAllCommodities()
//    {
//        Collection<Commodity> collectionCommidity = commoditiesList.values();
//        ArrayList<Commodity> commoditesArray = new ArrayList<Commodity>(collectionCommidity);
//        ArrayList<CommodityInBuyList> commoditiesInBuyList = new ArrayList<>();
//        for(Commodity commodity: commoditesArray)
//        {
//            CommodityInBuyList commodityBuyList = new CommodityInBuyList(commodity, commoditiesCount.get(commodity.getId()));
//            commoditiesInBuyList.add(commodityBuyList);
//        }
//        return commoditiesInBuyList;
//
//    }
//
//
//    public void emptyList()
//    {
//
//        commoditiesList = new HashMap<Integer, Commodity>();
//        buylistDiscountCode = null;
//    }
//
//    public double getBuylistPrice()
//    {
//
//        double price = 0;
//        for(Commodity commodity: commoditiesList.values())
//        {
//            price += commodity.getPrice() * commoditiesCount.get(commodity.getId());
//        }
//        if(buylistDiscountCode != null)
//        {
//            price = price - (price*buylistDiscountCode.getPercentage())/100;
//        }
//        return price;
//    }
//
//    public void setDiscountCode(DiscountCode discountCode)
//    {
//        System.out.println("setDiscountCode"+discountCode.getCode());
//        buylistDiscountCode = discountCode;
//        System.out.println("buylistDiscountCode is"+buylistDiscountCode.getCode());
//
//    }
//
//    public boolean hasDiscount(){
//        if(buylistDiscountCode!=null)
//            return true;
//        return false;
//    }
//
//    public DiscountCode getBuylistDiscountCode()
//    {
//        return buylistDiscountCode;
//    }
//
//    public int getBuylistNum(Integer commodityId) {
//        if(commoditiesList.containsKey(commodityId))
//        {
//            return commoditiesCount.get(commodityId);
//        }
//        else return 0;
//    }
}


