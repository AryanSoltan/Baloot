package Repository;

import Baloot.BuyList;
import Baloot.Commodity;
import Baloot.CommodityInBuyList;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;

public class CommodityRepository {
    private final EntityManagerFactory entityManagerFactory;
    private final ExternalRepository externalRepository;

    public CommodityRepository(EntityManagerFactory entityManagerFactory, ExternalRepository externalRepository) {
        this.entityManagerFactory = entityManagerFactory;
        this.externalRepository = externalRepository;
    }

    public void checkIfAllCommoditiesAreAvailabel(BuyList buyList) throws Exception
    {
        ArrayList<CommodityInBuyList> commoditiesList = buyList.getAllCommodities();
        for(CommodityInBuyList commodityInBuyList: commoditiesList)
        {
            Commodity commodity = commodityInBuyList.getCommodity();
            if(commodity.getInStock() == 0)
                throw new CommodityOutOfStock(commodity.getId());
        }
    }


    public void decreaseStock(BuyList userBuyList)
    {
        for(CommodityInBuyList commodity: userBuyList.getBuyList())
        {
            Commodity commodityIns = commodity.getCommodity();
            commodityIns.buy(commodity.getNumInStock());
        }
    }
}
