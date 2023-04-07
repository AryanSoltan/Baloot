package Baloot.Exception;

public class CommodityAlreadyAdded extends Exception{
    public CommodityAlreadyAdded(int commodityId){super("Error!: commodity " + commodityId + " already exists in buyList");}
}
