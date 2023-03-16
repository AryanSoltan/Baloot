package Baloot;

import Baloot.Exception.CommodityIsNotInBuyList;
import InterfaceServer.CommodityInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private double credit;

    Map<Integer, Commodity> boughtCommodities;
    ArrayList<Commodity> purchased;
    public User(String inputUserName, String inputPassword, String inputEmail,
         String inputBirthDate, String inputAddress, double inputCredit)
    {
        username = inputUserName;
        password = inputPassword;
        email = inputEmail;
        birthDate = inputBirthDate;
        address = inputAddress;
        credit = inputCredit;
    }

    public void buyCommodity(int commodityId, Commodity newCommodity)
    {
        boughtCommodities.put(commodityId, newCommodity);
    }

    public boolean hasBoughtCommodity(int commodityId)
    {
        return boughtCommodities.containsKey(commodityId);
    }

    public void removeFromBuyList(int commodityId) throws CommodityIsNotInBuyList {
        if(!boughtCommodities.containsKey(commodityId))
        {
            throw new CommodityIsNotInBuyList(commodityId);
        }
        boughtCommodities.remove(commodityId);
    }

    public ArrayList<Commodity> getBoughtCommodities() {
        Collection<Commodity> commoditiesBought = boughtCommodities.values();
        return new ArrayList<Commodity>(commoditiesBought);
    }

    public void setBoughtCommitiesEmpty() {
        boughtCommodities = new HashMap<>();
    }
    public void setPurchasedCommodityEmpty(){purchased = new ArrayList<Commodity>();}

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public double getCredit() {
        return credit;
    }

    public void addCredit(double incCredit) {
        credit += incCredit;
    }
    public void decreaseCredit(double outCredit){credit -= outCredit;}

    public void addPurchasedCommodity(Commodity commodity)
    {
        purchased.add(commodity);
        boughtCommodities.remove(commodity.getId());
    }

    public ArrayList<Commodity> getPurchasedCommodities() {
        return purchased;
    }
}
