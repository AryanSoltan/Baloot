package Baloot;

import Baloot.Exception.CommodityOutOfStock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BuyList {
    Map<Integer, Commodity> commoditiesList;

    private DiscountCode buylistDiscountCode;

    public BuyList()
    {
        commoditiesList = new HashMap<Integer, Commodity>();
        buylistDiscountCode = null;
    }
    public void addNewCommodityToBuyList(Commodity newCommodity)
    {
        commoditiesList.put(newCommodity.getId(), newCommodity);
    }

    public boolean contains(int commodityID)
    {
        return commoditiesList.containsKey(commodityID);
    }

    public void removeCommodityFromBuyList(int commodityID)
    {
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


