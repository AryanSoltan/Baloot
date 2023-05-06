package Baloot.Managers;

import Baloot.*;
import Baloot.Exception.CommodityNotExist;
import Baloot.Exception.CommodityOutOfStock;
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


    public void setFilterContent(String filter)
    {
        filterContent = filter;
    }
    public void setFilterType(String filter)
    {
        filterBy = filter;
    }
    public void setSearchFilter()
     {
         searchFilterIsSet = true;
     }

    public void setSortBy(String sort)
    {
        sortBy = sort;
    }

    public void setSortFilter()
    {
        sortFilterIsSet = true;
    }

    public Commodity getCommodityByID(int commodityID) throws Exception
    {
        if(!commodities.containsKey(commodityID))
            throw new CommodityNotExist(commodityID);
        return commodities.get(commodityID);
    }

    public ArrayList<Commodity> getAllCommodities()
    {
        Collection<Commodity> collectionCommidity = commodities.values();
        ArrayList<Commodity> commiditesArray = new ArrayList<Commodity>(collectionCommidity);
        return commiditesArray;
    }

    public ArrayList<Commodity> getCommoditiesByCategory(String category)
    {
        ArrayList<Commodity> commoditiesByCategory = new ArrayList<Commodity>();
        for(Commodity commodity: commodities.values())
        {
            if(commodity.hasCategory(category))
                commoditiesByCategory.add(commodity);
        }
        return commoditiesByCategory;
    }

    public ArrayList<Commodity> getCommoditiesByName(String name)
    {
        ArrayList<Commodity> commoditiesByName = new ArrayList<Commodity>();
        for(Commodity commodity: commodities.values())
        {
            if(commodity.nameContains(name))
                commoditiesByName.add(commodity);
        }
        return commoditiesByName;
    }

    public ArrayList<Commodity> getCommodityByRangePrice(double startPrice, double endPrice) {
        ArrayList<Commodity> answerCommodities = new ArrayList<Commodity>();
        for(Commodity commodity: commodities.values())
        {
            if(commodity.getPrice() <= endPrice && commodity.getPrice() >= startPrice)
                answerCommodities.add(commodity);
        }
        return answerCommodities;
    }

    public void addNewCommodity(Commodity newCommodity,String providerName)
    {
        newCommodity.setProviderName(providerName);
        newCommodity.setUserRatingsEmpty();
        newCommodity.setCommentsEmpty();
        commodities.put(newCommodity.getId(), newCommodity);
    }

    public void addCommentToCommodity(Comment comment,int commentIdNow, String username) throws Exception {
        int commodityId = comment.getCommodityId();
        comment.setRatingEmpty();
        Commodity commodity = getCommodityByID(commodityId);
        comment.setCommentId(commentIdNow);
        comment.setUserName(username);
        commodity.addComment(comment);
    }

    public void rateCommodity(String username, int commodityId, int score) throws Exception
    {
        Commodity neededCommodity = getCommodityByID(commodityId);
        if(neededCommodity.hasRating(username))
            neededCommodity.updateRating(username, score);
        else
            neededCommodity.addRating(username, score);
    }


    public void rateCommoditiesComment(int commentId, User user, int rate)
    {
        for(Commodity commodity: commodities.values())
        {
            if(commodity.hasCommentId(commentId))
                commodity.rateComment(commentId, user, rate);
        }
    }

    public void clearSearchFilter()
    {
        searchFilterIsSet = false;
        filterBy = null;
        filterContent = null;
    }

    public void decreaseStock(BuyList buyList)
    {
        ArrayList<CommodityInBuyList> commoditiesList = buyList.getAllCommodities();
        for(CommodityInBuyList commodity : commoditiesList) {
            commodity.getCommodity().buyOne();
        }
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


    public ArrayList<Commodity> getFilteredCommodities()
    {
        ArrayList<Commodity> answerCommodities = new ArrayList<Commodity>();
        answerCommodities = getAllCommodities();
        if(searchFilterIsSet)
        {
            if(filterBy == "category")
                answerCommodities = getCommoditiesByCategory(filterContent);
            else if (filterBy=="name")
                answerCommodities = getCommoditiesByName(filterContent);
        }
       if(sortFilterIsSet)
       {
           if(sortBy == "rate")
               answerCommodities.sort(Comparator.comparing(Commodity::getRating).reversed());
           if(sortBy == "price")
               answerCommodities.sort(Comparator.comparing(Commodity::getPrice).reversed());
       }
       return answerCommodities;
    }

    public static ArrayList<Commodity> getMostSimilarCommodities(Commodity targetCommodity, int n)
    {
        ArrayList<Pair<Commodity , Double>> suggestionsList = new ArrayList<>();
        ArrayList<String> targetCats = targetCommodity.getCategories();
        double score;
        for(Commodity commodity : commodities.values())
        {
            if(commodity.getId() == targetCommodity.getId())
                continue;
            if(commodity.hasCategory(targetCats))
                score = 11 + commodity.getRating();
            else
                score = commodity.getRating();
            suggestionsList.add(new Pair<>(commodity, score));
        }

        suggestionsList.sort(Comparator.comparing(Pair<Commodity, Double>::getSecond).reversed());
        //remove commodities with repeated scores
        HashSet<Object> seen = new HashSet<>();
        suggestionsList.removeIf(e -> !seen.add(e.getSecond()));
        ArrayList<Commodity> answerCommodities = new ArrayList<Commodity>();
        for(int i=0 ; i<min(n,suggestionsList.size()) ; i++)
        {
            answerCommodities.add(suggestionsList.get(i).getFirst());
        }
        return answerCommodities;

    }




}
