package Baloot;

import Baloot.Exception.CommodityOutOfStock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BuyList {
    Map<Integer, Commodity> commoditiesList;
    Map<Integer,Integer> commoditiesCount;

    private DiscountCode buylistDiscountCode;

    public BuyList()
    {
        commoditiesList = new HashMap<Integer, Commodity>();
        commoditiesCount = new HashMap<Integer,Integer>();
        buylistDiscountCode = null;
    }
    public void addNewCommodityToBuyList(Commodity newCommodity)
    {
        if(commoditiesList.containsKey(newCommodity.getId()))
        {
            commoditiesList.put(newCommodity.getId(), newCommodity);
            commoditiesCount.put(newCommodity.getId(),1);
        }
        else{
            int currCount = commoditiesCount.get(newCommodity.getId());
            commoditiesCount.put(newCommodity.getId(),currCount+1);
        }



    }

    public boolean contains(int commodityID)
    {
        return commoditiesList.containsKey(commodityID);
    }

    public void removeCommodityFromBuyList(int commodityID)
    {
        int currCount = commoditiesCount.get(commodityID);
        if(currCount>2) {
            commoditiesCount.put(commodityID, currCount -1);
        }
        if(currCount==1)
            commoditiesList.remove(commodityID);
    }

    public ArrayList<Commodity> getAllCommodities()
    {
        Collection<Commodity> collectionCommidity = commoditiesList.values();
        ArrayList<Commodity> commiditesArray = new ArrayList<Commodity>(collectionCommidity);
        return commiditesArray;

    }


    public void emptyList()
    {

        commoditiesList = new HashMap<Integer, Commodity>();
        buylistDiscountCode = null;
    }

    public double getBuylistPrice()
    {

        double price = 0;
        for(Commodity commodity: commoditiesList.values())
        {
            price += commodity.getPrice();
        }
        if(buylistDiscountCode != null)
        {
            price = price - (price*buylistDiscountCode.getPercentage())/100;
        }
        return price;
    }

    public void setDiscountCode(DiscountCode discountCode)
    {
        buylistDiscountCode = discountCode;
    }

    public boolean hasDiscount(){
        if(buylistDiscountCode!=null)
            return true;
        return false;
    }

    public DiscountCode getBuylistDiscountCode()
    {
        return buylistDiscountCode;
    }



}


