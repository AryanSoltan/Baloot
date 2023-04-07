package Baloot.Exception;

public class CommodityOutOfStock extends Exception{
    public CommodityOutOfStock(int commodityID){super("Error!: commodity " + commodityID + " is out of stock");}
}
