package Baloot.Managers;


import Baloot.DTOObjects.BuyListDTO;
import Baloot.DTOObjects.CommodityDTO;
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


    public static int countOfCommodityInBuylist(String username, Integer commodityId,EntityManager entityManager)  {

        var count = entityManager.createQuery("select c.numInStock" +
                        "                                       from CommodityInBuyList c " +
                        "                                       where c.commodity.id =: commodityId and c.user.username =:name" +
                        " and c.isBought=false")
                .setParameter("commodityId", commodityId).setParameter("name", username)
                .getResultList();

        if (count.isEmpty()){
            return 0;
        }
        return (int) count.get(0);
    }
    public BuyListDTO getUserBuyList(String userName,EntityManager entityManager) throws Exception {

        List commoditiesList = entityManager.createQuery("select c from CommodityInBuyList b join Commodity c on b.commodity.id=c.id where b.isBought=false and b.user.username=:userId ").setParameter("userId", userName).getResultList();
        var stream = commoditiesList.stream().map(
                commodity -> new CommodityDTO((Commodity) commodity , countOfCommodityInBuylist(userName, ((Commodity) commodity).getId(),entityManager))
        );

//        int buyListID = entityManager.createQuery("select c.id from CommodityInBuyList c where c.")
        System.out.println("in user nuy;ist gey");

        BuyListDTO buylist = new BuyListDTO(stream.toList());

        return buylist;
    }

    public BuyListDTO getUserPurchesedBuyList(String userName,EntityManager entityManager) throws Exception {
        List commoditiesList = entityManager.createQuery("select c from CommodityInBuyList b join Commodity c on b.commodity.id=c.id where b.isBought=true and b.user.username=:userId ").setParameter("userId", userName).getResultList();
        var stream = commoditiesList.stream().map(
                commodity -> new CommodityDTO((Commodity) commodity , countOfCommodityInBuylist(userName, ((Commodity) commodity).getId(),entityManager))
        );

        BuyListDTO buylist = new BuyListDTO(stream.toList());

        return buylist;
    }

    public void addDiscountCodeToUserBuyList(User user, DiscountCode discountCode,BuyList buylist,EntityManager entityManager) throws Exception
    {
        System.out.println("addDiscountCodeToUserBuyList");
        if(!userHasNotUsedCode(user,discountCode,entityManager))
            throw new DiscountCodeAlreadyUsed(discountCode.getCode());

    }

    public  void buyBuyList(User user, DiscountCode discountCode, double totalprice ,EntityManager entityManager) throws Exception
    {
        double final_price = totalprice;
        if(discountCode!=null)
            final_price = totalprice*(100-discountCode.getPercentage())/100;
        if (final_price > user.getCredit()) {
            entityManager.getTransaction().rollback();
            throw new NotEnoughCredit();
        } else {
            user.decreaseCredit( final_price);

        }
        if(discountCode!=null) {
            user.getDiscountCode().add(discountCode);
            discountCode.getUsersSet().add(user);
        }
    }


//
    public boolean userHasNotUsedCode(User user, DiscountCode discountCode,EntityManager entityManager) throws Exception
    {

        return entityManager.createNativeQuery(
                        "select * from DiscountCode_User c " +
                                "where c.discountId = :discountId and c.username = :username")
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
