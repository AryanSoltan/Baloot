package Baloot.Managers;

import Baloot.*;
import Baloot.Exception.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    static Map<String, User> users;
    public static User loggedInUser = null;

    public UserManager()
    {
        users = new HashMap<String, User>() ;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public static User getUserByUsername(String username) throws Exception
    {
        if(!users.containsKey(username))
        {
            throw new UserNotExist(username);
        }
        User neededUser = users.get(username);
        return neededUser;
    }

    public static User getUserByUseremail(String email) throws Exception
    {
        for(User user : users.values())
        {
            if(user.emailEquals(email)) {
                User neededUser = users.get(user.getName());
                return neededUser;
            }
        }
        throw new UserNotExist(email);
    }


    public static boolean isAnyBodyLoggedIn()
    {
         if(loggedInUser!=null)
             return true;
         return false;
    }


    public void login(String username, String password) throws Exception
    {
        User user = getUserByUsername(username);
        if(user.passwordMatches(password))
            loggedInUser = user;
        else
            throw new IncorrectPassword();
    }

    public static void logOut() {
        loggedInUser = null;
    }

    public void addNewUser(User newUser)
    {
        newUser.setBoughtCommitiesEmpty();
        newUser.setPurchasedCommodityEmpty();
        newUser.setUSedDiscountCodesEmpty();
        users.put(newUser.getName(), newUser);
    }

    public boolean doesExist(String userName)
    {
        return users.containsKey(userName);
    }

    public void updateUser(String name, User newUser) throws Exception
    {
        if(!checkUserNameValid(name))
        {
            throw new UserNameNotValid(name);
        }
        users.remove(name);
        newUser.setBoughtCommitiesEmpty();
        users.put(name, newUser);
    }

    private boolean checkUserNameValid(String userName)
    {
        return userName.matches("[a-zA-Z0-9]+");
    }


    public boolean commodityExistsInUserBuyList(String username, int commodityId) throws Exception
    {
        User neededUser = getUserByUsername(username);
        return neededUser.hasBoughtCommodity(commodityId);
    }

    public boolean userHasBoughtCommodity(String username, int commodityId) throws Exception
    {
        User neededUser = getUserByUsername(username);
        return neededUser.hasBoughtCommodity(commodityId);
    }

    public void addCommidityToUserBuyList(String username,Commodity neededCommodity) throws Exception
    {
        User neededUser = getUserByUsername(username);
        neededUser.buyCommodity(neededCommodity);
    }

    public BuyList getUserBuyList(String userName) throws Exception {

        User neededUser = getUserByUsername(userName);
        return neededUser.getBuyList();
    }

    public void removeCommodityFromBuyList(String username, int commodityId) throws Exception{
        User neededUser = getUserByUsername(username);
        if(!neededUser.hasBoughtCommodity(commodityId))
        {
            throw new CommodityIsNotInBuyList(commodityId);
        }
        neededUser.removeFromBuyList(commodityId);
    }

    public void userBoughtBuyList(User user, double totalPrice)
    {
        user.addBuyListToPurchasedCommodities();
        user.decreaseCredit(totalPrice);
        user.clearBuylist();
    }

    public void addCredit(String username, double credit) throws Exception
    {
        User user = getUserByUsername(username);
        user.addCredit(credit);
    }

    public void addDiscountCodeToUserBuyList(User user, DiscountCode discountCode) throws Exception
    {
        if(user.hasUsedDiscountCode(discountCode))
            throw new DiscountCodeAlreadyUsed(discountCode.getCode());
        user.addDiscountToBuylist(discountCode);
    }



}
