package Baloot.Exception;

public class CommodityNotExist extends Exception {
    public CommodityNotExist(int commodityId) {
        super("Error!: commodity " + commodityId + " doesn't exist");
    }

}
