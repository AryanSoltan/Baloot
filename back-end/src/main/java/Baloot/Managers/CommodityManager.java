package Baloot.Managers;

import Baloot.*;
import Baloot.DTOObjects.CommentDTO;
import Baloot.DTOObjects.CommodityDTO;
import Baloot.Exception.CommodityOutOfStock;
import Baloot.Exception.NotEnoughCredit;
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
    public List getAllCommodities(EntityManager entityManager)
    {

        List commoditiesList = entityManager.createQuery("select c from Commodity c").getResultList();

//        var stream = commoditiesList.stream().map(
//                commodity -> new Commodity(commodity)
//        );

        var stream = commoditiesList.stream().map(
                commodity -> new CommodityDTO((Commodity) commodity)
        );
     //   System.out.println("\n\n\n\nin get a;; commoditis\n\n\n\n"+stream.toList());
        return stream.toList();
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

    public void decreaseStock(BuyList buyList)
    {
        Set<CommodityInBuyList> commoditiesList = buyList.getBuyList();
        for(CommodityInBuyList commodity : commoditiesList) {
            commodity.getCommodity().buy(commodity.getNumInStock());
        }
    }
//
    public void checkIfAllCommoditiesAreAvailabel(String username, EntityManager entityManager) throws Exception
    {

        var buylist = (List<CommodityInBuyList>) entityManager.createQuery("select c from CommodityInBuyList c " +
                        "where user.username=:userId and c.isBought=false ")
                .setParameter("userId", username)
                .getResultList();

        for (var item : buylist) {
            if (item.getCommodity().getInStock() < item.getNumInStock()) {
                entityManager.getTransaction().rollback();
                throw new CommodityOutOfStock(item.getCommodity().getId());
            }
        }

    }

    public double getBuylistPrice(String username, EntityManager entityManager){
        var buylist = (List<CommodityInBuyList>) entityManager.createQuery("select c from CommodityInBuyList c " +
                        "where user.username=:userId and c.isBought=false ")
                .setParameter("userId", username)
                .getResultList();

        double totalprice=0;
        for (var item : buylist) {
            totalprice += item.getCommodity().getPrice() * item.getNumInStock();

        }
        return totalprice;
    }

    public  void handleBuy(String username,EntityManager entityManager) throws Exception
    {
        var buylist = (List<CommodityInBuyList>) entityManager.createQuery("select c from CommodityInBuyList c " +
                        "where user.username=:userId and c.isBought=false ")
                .setParameter("userId", username)
                .getResultList();
        for (var item : buylist) {
            item.setIsBought(true);
            item.getCommodity().decreaseStock(item.getNumInStock());
           // item.setNumInStock()

        }

    }
    public ArrayList<Commodity> getCommodityByRangePrice(double startPrice, double endPrice, EntityManager entityManager)
    {
        List commoditiesList = entityManager.createQuery("FROM Commodity c WHERE c.price > :startPrice AND c.price < :endPrice").setParameter("startPrice", startPrice)
        .setParameter("endPrice", endPrice).getResultList();
        return (ArrayList<Commodity>) commoditiesList;
    }


    public List<CommodityDTO> getMostSimilarCommodities(int targetCommodityId, String username, EntityManager entityManager)
    {
        int n =50;
        var suggestionsIDs = entityManager.createNativeQuery("with commodity_cat(id,is_in_same_cat) AS (SELECT cc.id , case when cc.categoryId IN (select target.categoryId from Commodity_Category target where target.id=:targetCommodityId) then 1 else 0 end as is_in_same_cat " +
                        "                              from Commodity_Category cc )" +
                        "                            select c.id " +
                                "                   from Commodity c join commodity_cat t on c.id = t.id" +
                                "                      where c.id !=:targetCommodityId" +
                                "                    Order by 11*t.is_in_same_cat + c.rating desc " +
                                "                      limit 5"
                        )
                .setParameter("targetCommodityId", targetCommodityId)
                .getResultList();

        List<Commodity> suggestedCommodities = entityManager.createQuery("select c from Commodity c where c.id in :suggestions").setParameter("suggestions", suggestionsIDs).getResultList();
        var stream = suggestedCommodities.stream().map(
                commodity -> new CommodityDTO((Commodity) commodity , UserManager.countOfCommodityInBuylist(username, ((Commodity) commodity).getId(),entityManager))
        );
        return stream.toList();

    }

    public List getCommodityComments(int commoditytId,  EntityManager entityManager)
    {

        List comments = entityManager.createQuery("select c from Comment c where c.commodity.id =:commoditytId")
                .setParameter("commoditytId",commoditytId)
                .getResultList();

     //   List commoditiesList = entityManager.createQuery("select c from CommodityInBuyList b join Commodity c on b.commodity.id=c.id where b.user.username=:userId and b.isBought=false").setParameter("userId", userName).getResultList();
        var stream = comments.stream().map(
                comment -> new CommentDTO((Comment) comment)
        );

        return stream.toList();

    }


}
