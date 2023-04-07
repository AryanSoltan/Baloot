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

    private BuyList buyList;
    ArrayList<Commodity> purchased;
    ArrayList<DiscountCode> usedDiscountCodes ;

    public User(String inputUserName, String inputPassword, String inputEmail,
         String inputBirthDate, String inputAddress, double inputCredit)
    {
        username = inputUserName;
        password = inputPassword;
        email = inputEmail;
        birthDate = inputBirthDate;
        address = inputAddress;
        credit = inputCredit;
        buyList = new BuyList();
        // usedDiscountCodes = new ArrayList<DiscountCode>();

    }

    public void buyCommodity(Commodity newCommodity)
    {
        buyList.addNewCommodityToBuyList(newCommodity);
    }

    public boolean hasBoughtCommodity(int commodityId)
    {
        return buyList.contains(commodityId);

    }

    public void removeFromBuyList(int commodityId) throws CommodityIsNotInBuyList {
        if(!buyList.contains(commodityId))
        {
            throw new CommodityIsNotInBuyList(commodityId);
        }
        buyList.removeCommodityFromBuyList(commodityId);
    }

    public ArrayList<Commodity> getBoughtCommodities() {
        return buyList.getAllCommodities();
    }

    public void setBoughtCommitiesEmpty() {

        buyList = new BuyList();
    }
    public void setPurchasedCommodityEmpty(){purchased = new ArrayList<Commodity>();}

    public void setUSedDiscountCodesEmpty(){usedDiscountCodes = new ArrayList<DiscountCode>();}

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

    public void addBuyListToPurchasedCommodities()
    {
        for(Commodity commodity: buyList.getAllCommodities())
        {
            purchased.add(commodity);
        }
    }
    public void clearBuylist()
    {
        if(buyList.hasDiscount())
        {
            usedDiscountCodes.add(buyList.getBuylistDiscountCode());
        }
        buyList.emptyList();
    }

    public ArrayList<Commodity> getPurchasedCommodities() {
        return purchased;
    }

    public BuyList getBuyList()
    {
        return buyList;
    }

    public boolean passwordMatches(String pass)
    {
        if(password.equals(pass))
            return true;
        return false;
    }

    public boolean emailEquals(String emailAdd)
    {
        if(email.equals(emailAdd))
            return true;
        return false;
    }

    public boolean hasUsedDiscountCode(DiscountCode discountCode)
    {
        if(usedDiscountCodes.isEmpty())
            return false;
        if(usedDiscountCodes.contains(discountCode))
            return true;
        return false;
    }

    public void addDiscountToBuylist(DiscountCode discountCode)
    {
        buyList.setDiscountCode(discountCode);
    }
}
