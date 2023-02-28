package Baloot;

import Baloot.Exception.UserNotExist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BalootServer {

    Map<String, User> users;
    Map<Integer, Provider> providers;
    Map<Integer, Commodity> commodities;


    BalootServer()
    {
        users = new HashMap<String, User>();
        providers = new HashMap<Integer, Provider>();
        commodities = new HashMap<Integer, Commodity>();

    }

    public void addUser(String name, User newUser)
    {
        users.put(name, newUser);
    }

    public boolean doesExist(String userName)
    {
        return users.containsKey(userName);
    }

    public void addProvider(int id, Provider newProvider)
    {
        providers.put(id, newProvider);
    }

    public void updateUser(String name, User newUser)
    {
        users.remove(name);
        users.put(name, newUser);
    }

    public void addCommidity(int id, Commodity newCommidity)
    {
        commodities.put(id, newCommidity);
    }

    public void addCommodityToProvider(int commoditiyId, int providerId)
    {
        Provider neededProvider = providers.get(providerId);
        Commodity neededComodity = commodities.get(commoditiyId);
        neededProvider.addCommodity(neededComodity);
    }

    public boolean checkExistProvider(int id)
    { return users.containsKey(id);}

    public ArrayList<Commodity> getCommodityList()
    {
        Collection<Commodity> collectionCommidity = commodities.values();
        ArrayList<Commodity> commiditesArray = new ArrayList<Commodity>(collectionCommidity);
        return commiditesArray;
    }

    public void rateCommodity(String username, int commodityId, int score)
    {
        Commodity neededCommodity = commodities.get(commodityId);
        if(neededCommodity.hasRating(username))
        {
            neededCommodity.updateRating(username, score);
        }
        else
        {
            neededCommodity.addRating(username, score);
        }
    }

    private User findUser(String username) throws Exception {
        if(!users.containsKey(username))
        {
            throw new UserNotExist(username);
        }
        User neededUser = users.get(username);
        return neededUser;
    }

    public Commodity findCommodity(int commodityId)
    {
        if(!commodities.containsKey(commodityId))
        {
            //throw
        }
        Commodity neededCommodity = commodities.get(commodityId);
        return neededCommodity;
    }
    public boolean commodityExistsInUserBuyList(String username, int commodityId) throws Exception {
        User neededUser = findUser(username);
        return neededUser.hasBoughtCommodity(commodityId);
    }

    public boolean commodityIsAvailable(int commodityId)
    {
        Commodity neededCommodity = findCommodity(commodityId);
        return neededCommodity.isAvailable();
    }

    public void addCommidityToUserBuyList(String username, int commodityId) throws Exception {

        User neededUser = findUser(username);
        Commodity neededCommodity = findCommodity(commodityId);
        neededUser.buyCommodity(commodityId, neededCommodity);
        neededCommodity.buyOne();
    }

    public void setCommodityProviderName(int commodityId, int providerId)
    {
        Commodity neededCommodity = commodities.get(commodityId);
        Provider neededProvider = providers.get(providerId);
        String providerName = neededProvider.getName();
        neededCommodity.setProvider(providerName);
    }

    public void removeFromBuyList(String username, int commodityId) {
        User neededUser = users.get(username);
        neededUser.removeFromBuyList(commodityId);
    }

    public Commodity getCommodityById(int commodityId)
    {
        Commodity neededCommodity = findCommodity(commodityId);
        return neededCommodity;
    }

    public ArrayList<Commodity> getCommoditiesByCategory(String category) {
        ArrayList<Commodity> commoditiesByCategory = new ArrayList<Commodity>();
        for(Commodity commodity: commodities.values())
        {
            if(commodity.hasCategory(category))
            {
                commoditiesByCategory.add(commodity);
            }
        }
        return commoditiesByCategory;
    }

    public ArrayList<Commodity> getUserByList(String userName) throws Exception {

        User neededUser = findUser(userName);
        return neededUser.getCommodities();
    }
}
