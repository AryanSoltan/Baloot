package Baloot;

import Baloot.Exception.CommodityOutOfStock;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "BuyList")
public class BuyList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long buyListId;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BUY_LIST_COMMODITIES", joinColumns = @JoinColumn(name = "buyListId"),
    inverseJoinColumns = @JoinColumn(name = "commodityInBuyListId"))
    Set<CommodityInBuyList> commoditiesList = new HashSet<>();

    @OneToOne(mappedBy = "buyList")
    User user1;

    @OneToOne(mappedBy = "purchased")
    User user2;

//    private DiscountCode buylistDiscountCode;

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
    public boolean contains(int commodityID)
    {
        for(CommodityInBuyList commodity: commoditiesList)
        {
            if(commodity.getCommodity().getId() == commodityID)
                return true;
        }
        return false;
    }

    public void addSingleCommodityInBuyList(CommodityInBuyList commodity)
    {
        commoditiesList.add(commodity);
    }

    public CommodityInBuyList getCommodityInBuyList(int commodityId)
    {
        for(CommodityInBuyList commodity: commoditiesList)
        {
            if(commodity.getCommodity().getId() == commodityId)
                return commodity;
        }
        return null;
    }

    public void decreaseOneCommodity(int commodityId)
    {
        for(CommodityInBuyList commodity: commoditiesList)
        {
            if(commodity.getCommodity().getId() == commodityId)
            {
                if(commodity.getNumInStock() == 1)
                {
                    commoditiesList.remove(commodity);
                    break;
                }
                else
                {
                    commodity.decreaseOne();
                }
            }
        }
    }

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

    public double getBuylistPrice()
    {

        double price = 0;
        for(CommodityInBuyList commodityInBuyList: commoditiesList)
        {
            Commodity commodity = commodityInBuyList.getCommodity();
            price += commodity.getPrice() * commodityInBuyList.getNumInStock();
        }
//        if(buylistDiscountCode != null)
//        {
//            price = price - (price*buylistDiscountCode.getPercentage())/100;
//        }
        return price;
    }

    public Set<CommodityInBuyList> getBuyList() {
        return commoditiesList;
    }

    public void makeEmpty()
    {
        commoditiesList.clear();
//        buyListDiscountCode = null;
    }

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


