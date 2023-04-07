package Baloot.Exception;

public class CommodityIsNotInBuyList extends Exception{
    public CommodityIsNotInBuyList(int commodityID){super("Error!: commodity " + commodityID + " is not in buyList");}
}
