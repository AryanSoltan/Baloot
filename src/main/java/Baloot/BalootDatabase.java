package Baloot;

import Baloot.Exception.UserAlreadyExistError;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BalootDatabase {

    Map<String, User> users;
    Map<Integer, Provider> providers;
    Map<Integer, Commodity> commodities;
    BalootDatabase()
    {
        users = new HashMap<String, User>();
    }

    public void addUser(String name, String password, String email,
                         String birthDate, String address, double credit)
    {
        User newUser = new User(name, password, email, birthDate, address, credit);
        users.put(name, newUser);
    }

    public boolean doesExist(String userName)
    {
        return users.containsKey(userName);
    }

    public void addProvider(int id, String name, String registryDate)
    {
        Provider newProvider = new Provider(id, name, registryDate);
        providers.put(id, newProvider);
    }

    public void updateUser(String name, String password, String email, String birthDate,
                           String address, double credit)
    {
        users.remove(name);
        addUser(name, password, email, birthDate, address, credit);
    }

    public void addCommidity(int id, String name, int providerId, double price,
                             ArrayList<String> categories, double rating, int inStock)
    {
        Commodity newCommidity = new Commodity(id, name, providerId, price, categories,
                rating, inStock);
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



}
