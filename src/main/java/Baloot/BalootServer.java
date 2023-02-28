package Baloot;

import Baloot.Exception.*;

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

    public boolean checkUserNameValid(String userName)
    {
        return userName.matches("[a-zA-Z0-9]+");
    }

    public void updateUser(String name, User newUser) throws Exception {
        if(!checkUserNameValid(name))
        {
            throw new UserNameNotValid(name);
        }
        users.remove(name);
        users.put(name, newUser);
    }

    private Provider findProvider(int providerId) throws ProviderNotExist {
        if(providers.containsKey(providerId))
            return providers.get(providerId);
        else
            throw new ProviderNotExist(providerId);
    }

    public void addCommidity(int providerId, int id, Commodity newCommidity)
            throws ProviderNotExist {
        Provider provider = findProvider(providerId);
        String providerName = provider.getProviderName();
        newCommidity.setProviderName(providerName);
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

    public void rateCommodity(String username, int commodityId, String scoreStr) throws Exception {
        if(!scoreStr.matches("-?(0|[1-9]\\d*)"))
        {
            throw new InvalidRating();
        }
        int score = Integer.parseInt(scoreStr);
        if(score < 1 || score > 10)
        {
            throw new InvalidRating();
        }
        Commodity neededCommodity = findCommodity(commodityId);
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

    public Commodity findCommodity(int commodityId) throws Exception {
        if(!commodities.containsKey(commodityId))
        {
            throw new CommodityNotExist(commodityId);
        }
        Commodity neededCommodity = commodities.get(commodityId);
        return neededCommodity;
    }
    public boolean commodityExistsInUserBuyList(String username, int commodityId) throws Exception {
        User neededUser = findUser(username);
        return neededUser.hasBoughtCommodity(commodityId);
    }

    public boolean commodityIsAvailable(int commodityId) throws Exception {
        Commodity neededCommodity = findCommodity(commodityId);
        return neededCommodity.isAvailable();
    }

    public void addCommidityToUserBuyList(String username, int commodityId) throws Exception {

        User neededUser = findUser(username);
        Commodity neededCommodity = findCommodity(commodityId);
        if(!neededCommodity.isAvailable())
            throw new CommodityOutOfStock(commodityId);
        if(neededUser.hasBoughtCommodity(commodityId))
            throw new CommodityAlreadyAdded(commodityId);
        neededUser.buyCommodity(commodityId, neededCommodity);
        neededCommodity.buyOne();
    }

    public void setCommodityProviderName(int commodityId, int providerId)
    {
        Commodity neededCommodity = commodities.get(commodityId);
        Provider neededProvider = providers.get(providerId);
        String providerName = neededProvider.getProviderName();
        neededCommodity.setProvider(providerName);
    }

    public void removeFromBuyList(String username, int commodityId) throws Exception {
        User neededUser = findUser(username);
        if(neededUser.hasBoughtCommodity(commodityId))
        {
            throw new CommodityIsNotInBuyList(commodityId);
        }
        neededUser.removeFromBuyList(commodityId);
    }

    public Commodity getCommodityById(int commodityId) throws Exception {
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

    public ArrayList<Commodity> getUserBuyList(String userName) throws Exception {

        User neededUser = findUser(userName);
        return neededUser.getCommodities();
    }
}
