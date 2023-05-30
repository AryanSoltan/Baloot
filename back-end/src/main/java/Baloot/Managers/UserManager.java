package Baloot.Managers;


import Baloot.Exception.UserNotExist;
import Baloot.User;
import jakarta.persistence.EntityManager;

import java.util.List;

import Baloot.*;
import Baloot.Exception.*;
import java.util.HashMap;
import java.util.Map;
//
public class UserManager {
//    static Map<String, User> users;
//    public static User loggedInUser = null;
//
    public UserManager()
    {
//        users = new HashMap<String, User>() ;
    }

//    public User getLoggedInUser() {
//        return loggedInUser;
//    }
//
//    public static User getUserByUsername(String username) throws Exception
//    {
//        if(!users.containsKey(username))
//        {
//            throw new UserNotExist(username);
//        }
//        User neededUser = users.get(username);
//        return neededUser;
//    }
//
//    public int getNumOfUsers() throws Exception
//    {
//
//        return users.size();
//    }
//
//
    public User getUserByUseremail(String email, EntityManager entityManager) throws Exception
    {
        List users = entityManager.createQuery("SELECT u FROM User u where u.email=:userEmail")
                .setParameter("userEmail", email).getResultList();
        if (users.isEmpty()) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new UserNotExist(email);
        }
        return (User) users.get(0);
    }


//
//    public static boolean isAnyBodyLoggedIn()
//    {
//         if(loggedInUser!=null)
//             return true;
//         return false;
//    }
//
//
//    public void login(String username, String password) throws Exception
//    {
//        User user = getUserByUsername(username);
//        if(user.passwordMatches(password))
//            loggedInUser = user;
//        else
//            throw new IncorrectPassword();
//    }
//
//    public static void logOut() {
//        loggedInUser = null;
//    }
//
//    public void addNewUser(User newUser)
//    {
//        newUser.setBoughtCommitiesEmpty();
//        newUser.setPurchasedCommodityEmpty();
//        newUser.setUSedDiscountCodesEmpty();
//        users.put(newUser.getName(), newUser);
//    }
//
    public boolean doesExist(String userName, EntityManager entityManager)
    {
        String query = "FROM User u WHERE u.username = :userName";
        List userNeeded = entityManager.createQuery(query)
                .setParameter("userName", userName).getResultList();
        ;
        if(userNeeded.size() == 0)
        {
            return false;
        }
        return true;
    }
//
//    public void updateUser(String name, User newUser) throws Exception
//    {
//        if(!checkUserNameValid(name))
//        {
//            throw new UserNameNotValid(name);
//        }
//        users.remove(name);
//        newUser.setBoughtCommitiesEmpty();
//        users.put(name, newUser);
//    }
//
//    private boolean checkUserNameValid(String userName)
//    {
//        return userName.matches("[a-zA-Z0-9]+");
//    }
//
//
//    public boolean commodityExistsInUserBuyList(String username, int commodityId) throws Exception
//    {
//        User neededUser = getUserByUsername(username);
//        return neededUser.hasBoughtCommodity(commodityId);
//    }
//
//    public boolean userHasBoughtCommodity(String username, int commodityId) throws Exception
//    {
//        User neededUser = getUserByUsername(username);
//        return neededUser.hasBoughtCommodity(commodityId);
//    }
//
//    public void addCommidityToUserBuyList(String username,Commodity neededCommodity) throws Exception
//    {
//        User neededUser = getUserByUsername(username);
//        neededUser.buyCommodity(neededCommodity);
//    }
//
    public BuyList getUserBuyList(String userName,EntityManager entityManager) throws Exception {
        var userBuylistID = entityManager.createNativeQuery("select u.buyListId " +
                        "from User_BuyList u join BuyList b on u.buylistId=b.buylistId " +
                        "where u.username := username and b.isBought=false")
                .setParameter("username",userName)
                .getResultList();

        return (BuyList) entityManager.createQuery("select b from BuyList b where b.id =: userBuylistID").setParameter("userBuylistID",userBuylistID).getSingleResult();
    }

    public BuyList getUserPurchesedBuyList(String userName,EntityManager entityManager) throws Exception {
        var userBuylistID = entityManager.createNativeQuery("select u.buyListId " +
                        "from User_BuyList u join BuyList b on u.buylistId=b.buylistId " +
                        "where u.username := username and b.isBought=true")
                .setParameter("username",userName)
                .getResultList();

        return (BuyList) entityManager.createQuery("select b from BuyList b where b.id =: userBuylistID").setParameter("userBuylistID",userBuylistID).getSingleResult();
    }
//
//    public void removeCommodityFromBuyList(String username, int commodityId) throws Exception{
//        User neededUser = getUserByUsername(username);
//        if(!neededUser.hasBoughtCommodity(commodityId))
//        {
//            throw new CommodityIsNotInBuyList(commodityId);
//        }
//        neededUser.removeFromBuyList(commodityId);
//    }
//
//    public void userBoughtBuyList(User user, double totalPrice)
//    {
//        user.addBuyListToPurchasedCommodities();
//        user.decreaseCredit(totalPrice);
//        user.clearBuylist();
//    }
//
//    public void addCredit(String username, double credit) throws Exception
//    {
//        User user = getUserByUsername(username);
//        user.addCredit(credit);
//    }
//
    public void addDiscountCodeToUserBuyList(User user, DiscountCode discountCode,EntityManager entityManager) throws Exception
    {
        System.out.println("addDiscountCodeToUserBuyList");
        if(userHasUsedCode(user,discountCode,entityManager))
            throw new DiscountCodeAlreadyUsed(discountCode.getCode());
     //   user.addDiscountToBuylist(discountCode);
    }


//
    public boolean userHasUsedCode(User user, DiscountCode discountCode,EntityManager entityManager) throws Exception
    {

        return entityManager.createNativeQuery(
                        "select * from DiscountCode_User " +
                                "where discountId = :discountId and username = :username")
                .setParameter("discountId", discountCode.getDiscountId())
                .setParameter("username", user.getName())
                .getResultList().isEmpty();

    }
//
//
//    public User getUserById(String username) {
//        return users.get(username);
//    }
//
//    public BuyList getUserPurchasedList(String userName) throws Exception {
//
//        User neededUser = getUserByUsername(userName);
//        return neededUser.getPurchasedCommodities();
//    }
//
//    public int getUserNumBought(String userName, Integer commodityId) throws Exception {
//        User neededUser = getUserByUsername(userName);
//        return neededUser.getNumBought(commodityId);
//    }
//
//    public boolean isLogIn() {
//        if(loggedInUser == null)
//        {
//            return false;
//        }
//        return true;
//    }
}
