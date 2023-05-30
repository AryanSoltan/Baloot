package Baloot.Managers;

import Baloot.*;
import Baloot.Exception.CommodityOutOfStock;
import com.beust.ah.A;
import jakarta.persistence.EntityManager;
import kotlin.Pair;

import java.util.*;

import static java.lang.Math.min;

public class CommodityManager {
    static Map<Integer, Commodity> commodities;
    private static String filterContent;
    private static String sortBy;
    private static String filterBy;
    private static boolean searchFilterIsSet;
    private static boolean sortFilterIsSet;

    public CommodityManager()
    {
        commodities = new HashMap<Integer, Commodity>() ;
        searchFilterIsSet = false;
        sortFilterIsSet = false;
        filterBy = null;
        sortBy = null;
        filterContent = null;
    }

    //
//
//    public void setFilterContent(String filter)
//    {
//        filterContent = filter;
//    }
//    public void setFilterType(String filter)
//    {
//        filterBy = filter;
//    }
//    public void setSearchFilter()
//     {
//         searchFilterIsSet = true;
//     }
//
//    public void setSortBy(String sort)
//    {
//        sortBy = sort;
//    }
//
//    public void setSortFilter()
//    {
//        sortFilterIsSet = true;
//    }
//
//    public Commodity getCommodityByID(int commodityID) throws Exception
//    {
//        if(!commodities.containsKey(commodityID))
//            throw new CommodityNotExist(commodityID);
//        return commodities.get(commodityID);
//    }
//
    public List getAllCommodities(EntityManager entityManager)
    {
        List commoditiesList = entityManager.createQuery("From Commodity c").getResultList();
        return commoditiesList;
    }
//
    public ArrayList<Commodity> getCommoditiesByCategory(String category, EntityManager entityManager)
    {
        List commoditiesList = entityManager.createQuery("FROM Commodity c").getResultList();
        ArrayList<Commodity> commditiesCategory = new ArrayList<Commodity>();
        for(Object commodityObj: commoditiesList)
        {
            Commodity commodity = (Commodity) commodityObj;
            if(((Commodity) commodityObj).hasCategory(category))
                commditiesCategory.add(commodity);
        }
        return commditiesCategory;
    }
//
//    public ArrayList<Commodity> getCommoditiesByName(String name)
//    {
//        ArrayList<Commodity> commoditiesByName = new ArrayList<Commodity>();
//        for(Commodity commodity: commodities.values())
//        {
//            if(commodity.nameContains(name))
//                commoditiesByName.add(commodity);
//        }
//        return commoditiesByName;
//    }
//
//    public ArrayList<Commodity> getCommodityByRangePrice(double startPrice, double endPrice) {
//        ArrayList<Commodity> answerCommodities = new ArrayList<Commodity>();
//        for(Commodity commodity: commodities.values())
//        {
//            if(commodity.getPrice() <= endPrice && commodity.getPrice() >= startPrice)
//                answerCommodities.add(commodity);
//        }
//        return answerCommodities;
//    }
//
//    public void addNewCommodity(Commodity newCommodity,String providerName)
//    {
//        newCommodity.setProviderName(providerName);
//        newCommodity.setUserRatingsEmpty();
//        newCommodity.setCommentsEmpty();
//        commodities.put(newCommodity.getId(), newCommodity);
//    }
//
    public void addCommentToCommodity(Comment comment, int commentIdNow, String username, EntityManager entityManager) throws Exception {
        entityManager.persist(comment);
        Commodity commodity = entityManager.find(Commodity.class, comment.getCommodityId());
//        comment.setRatingEmpty();
        comment.setUserName(username);
        commodity.addComment(comment);
    }
//
    public void rateCommodity(String username, int commodityId, int score, EntityManager entityManager) throws Exception
    {
        Commodity neededCommodity = entityManager.find(Commodity.class, commodityId);
        if(neededCommodity.hasRating(username))
            neededCommodity.updateRating(username, score, entityManager);
        else
            neededCommodity.addRating(username, score, entityManager);
    }
//
//
//    public Comment rateCommoditiesComment(int commentId, User user, int rate)
//    {
//        for(Commodity commodity: commodities.values())
//        {
//            if(commodity.hasCommentId(commentId)) {
//                commodity.rateComment(commentId, user, rate);
//                return commodity.getComment(commentId);
//            }
//        }
//        return null;
//    }
//
//    public void clearSearchFilter()
//    {
//        searchFilterIsSet = false;
//        filterBy = null;
//        filterContent = null;
//    }
//
    public void decreaseStock(BuyList buyList)
    {
        Set<CommodityInBuyList> commoditiesList = buyList.getBuyList();
        for(CommodityInBuyList commodity : commoditiesList) {
            commodity.getCommodity().buy(commodity.getNumInStock());
        }
    }
//
    public void checkIfAllCommoditiesAreAvailabel(BuyList buyList) throws Exception
    {
        Set<CommodityInBuyList> commoditiesList = buyList.getBuyList();
        for(CommodityInBuyList commodityInBuyList: commoditiesList)
        {
            Commodity commodity = commodityInBuyList.getCommodity();
            if(commodity.getInStock() == 0)
                throw new CommodityOutOfStock(commodity.getId());
        }
    }

    public ArrayList<Commodity> getCommodityByRangePrice(double startPrice, double endPrice, EntityManager entityManager)
    {
        List commoditiesList = entityManager.createQuery("FROM Commodity c WHERE c.price > :startPrice AND c.price < :endPrice").setParameter("startPrice", startPrice)
        .setParameter("endPrice", endPrice).getResultList();
        return (ArrayList<Commodity>) commoditiesList;
    }

//
//
//    public ArrayList<Commodity> getFilteredCommodities()
//    {
//        ArrayList<Commodity> answerCommodities = new ArrayList<Commodity>();
//        answerCommodities = getAllCommodities();
//        if(searchFilterIsSet)
//        {
//            if(filterBy == "category")
//                answerCommodities = getCommoditiesByCategory(filterContent);
//            else if (filterBy=="name")
//                answerCommodities = getCommoditiesByName(filterContent);
//        }
//       if(sortFilterIsSet)
//       {
//           if(sortBy == "rate")
//               answerCommodities.sort(Comparator.comparing(Commodity::getRating).reversed());
//           if(sortBy == "price")
//               answerCommodities.sort(Comparator.comparing(Commodity::getPrice).reversed());
//       }
//       return answerCommodities;
//    }
//
    public static ArrayList<Commodity> getMostSimilarCommodities(int targetCommodityId,  EntityManager entityManager)
    {
        int n =50;
        var suggestions = entityManager.createNativeQuery("with commodity_cat(id,is_in_same_cat) AS (SELECT cc.id , case when cc.categoryId IN (select target.categoryId from Commodity_Category target where target.id=:targetCommodityId) then 1 else 0 end as is_in_same_cat " +
                        "                              from Commodity_Category cc )" +
                        "                            select c.id " +
                                "                   from Commodity c join commodity_cat t on c.id = t.id" +
                                "                      where c.id !=:targetCommodityId" +
                                "                    Order by 11*t.is_in_same_cat + c.rating desc " +
                                "                      limit 5"
                        )
                .setParameter("targetCommodityId", targetCommodityId)
                .getResultList();

        List <Commodity> suggestedCommodities = entityManager.createQuery("select c from Commodity c where c.id in :suggestions") .setParameter("suggestions", suggestions).getResultList();

        return (ArrayList<Commodity>)suggestedCommodities;

    }

}
