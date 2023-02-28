package Baloot;

import Baloot.Exception.UserAlreadyExistError;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BalootDatabase {

    Map<String, User> users;
    Map<Integer, Provider> providers;
    Map<Integer, Commodity> commodities;

    Map<String, Rating> ratings;
    BalootDatabase()
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
}
