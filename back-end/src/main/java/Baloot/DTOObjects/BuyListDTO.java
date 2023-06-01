package Baloot.DTOObjects;

import Baloot.Category;
import Baloot.Commodity;

import java.util.List;

public class BuyListDTO {

    private List<CommodityDTO> commoditiesList;

    private double buylistPrice;

    private int buylistId;



    public BuyListDTO(List<CommodityDTO> commodities) {

        this.commoditiesList = commodities;

        this.buylistPrice = setBuylistPrice();
    }

    public BuyListDTO(List<CommodityDTO> commodities, int buylistid) {

        this.commoditiesList = commodities;
        this.buylistId = buylistid;

        this.buylistPrice = setBuylistPrice();
    }

    public List<CommodityDTO> getallCommodities() {
        return commoditiesList;
    }

    public int getBuylistId() {
        return buylistId;
    }

    public void setBuylistId(int id){buylistId=id;}

    public double getbuylistPrice() {
        return buylistPrice;
    }

    public double setBuylistPrice()
    {

        double price = 0;
        for(CommodityDTO commodity: commoditiesList)
        {
            price += commodity.getPrice() * commodity.getcountInBuylist();
        }
//        if(buylistDiscountCode != null)
//        {
//            price = price - (price*buylistDiscountCode.getPercentage())/100;
//        }
        return price;
    }

}
