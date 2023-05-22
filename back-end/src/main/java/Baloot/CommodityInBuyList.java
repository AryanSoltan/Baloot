package Baloot;

public class CommodityInBuyList {

    Commodity commodity;
    Integer numInStock;
    public CommodityInBuyList(Commodity inputCommodity, Integer inputNumInStock) {
        numInStock = inputNumInStock;
        commodity = inputCommodity;
    }

    public Commodity getCommodity() {
        return commodity;
    }
    public Integer getNumInStock()
    {
        return numInStock;
    }
}
