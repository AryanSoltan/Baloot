package Repository;



import Baloot.*;
import Baloot.Exception.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import Baloot.Managers.*;

import java.util.ArrayList;
import java.util.List;

public class BalootServerRepo
{
    private final EntityManagerFactory entityManagerFactory;

    private UserManager userManager;
//    private ProviderManager providerManager;
    private CommodityManager commodityManager;
//    private PaymentManager paymentManager;
    static int commentIdNow;

     private static BalootServerRepo instance = null;
//


    public static BalootServerRepo getInstance()
    {
//        if(instance == null)
//            instance = new BalootServerRepo();
        return instance;

    }

    public BalootServerRepo(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        userManager = new UserManager();
//        providerManager = new ProviderManager();
        commodityManager = new CommodityManager();
//        paymentManager = new PaymentManager();
    }

    public void logIn(String username, String password) throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, username);
        if(user == null)
        {
            entityManager.getTransaction().rollback();
            throw new UserNotExist(username);
        }
        else{
            if(password.equals(user.getPassword()))
            {
                return ;
            }
            else{
                throw new IncorrectPassword();
            }
        }
    }

    public void addUser(User newUser) throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(newUser);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new UserAlreadyExistError(newUser.getName());
        }
        entityManager.getTransaction().commit();
    }

    public BuyList getUserBuyList(String userName) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, userName);
        return user.getBuyList();
    }

    public void addCredit(String userName, double credit) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(userName, entityManager);
        if(user == null)
        {
            entityManager.getTransaction().rollback();
            throw new UserNotExist(userName);
        }
        else
        {
            user.addCredit(credit);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private User findUserById(String userName, EntityManager entityManager) {
        String query = "FROM User u WHERE u.username = :userName";
        List userNeeded = entityManager.createQuery(query)
                .setParameter("userName", userName).getResultList();
        ;
        if(userNeeded.size() == 0)
        {
            return null;
        }
        return (User) userNeeded.get(0);
    }
    public void addCommidityToUserBuyList(String username, int commodityId)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(username, entityManager);
        BuyList buyList = user.getBuyList();
        if(!buyList.contains(commodityId)) {
            Commodity newCommodity = findCommodityById(commodityId, entityManager);
            CommodityInBuyList commodity = new CommodityInBuyList(newCommodity, 1);
            entityManager.persist(commodity);
            buyList.addSingleCommodityInBuyList(commodity);
        }
        else
        {
            CommodityInBuyList commodityInBuyList = buyList.getCommodityInBuyList(commodityId);
            commodityInBuyList.increaseOne();
        }
        entityManager.getTransaction().commit();
    }

    public void removeFromBuyList(String username, int commodityId) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(username, entityManager);
        BuyList buyList = user.getBuyList();
        if(!buyList.contains(commodityId)) {
            throw new CommodityIsNotInBuyList(commodityId);
        }
        else
        {
            buyList.decreaseOneCommodity(commodityId);
        }
        entityManager.getTransaction().commit();
    }
    public void handlePaymentUser(String userName) throws Exception //done
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(userName, entityManager);
        BuyList userBuyList = user.getBuyList();
        commodityManager.checkIfAllCommoditiesAreAvailabel(userBuyList);
        double totalPrice = userBuyList.getBuylistPrice();
        System.out.println("total price is"+totalPrice);
        if(user.getCredit() < totalPrice) {
            throw new NotEnoughCredit();
        }
        commodityManager.decreaseStock(userBuyList);
        userBuyList.makeEmpty();
        entityManager.getTransaction().commit();
    }

    public List getCommodityList(EntityManager entityManager)
    {
        List commoditiesList = commodityManager.getAllCommodities(entityManager);
        return commoditiesList;
    }

    public ArrayList<Commodity> getCommoditiesByCategory(String category) { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ArrayList<Commodity> commodities = commodityManager.getCommoditiesByCategory(category, entityManager);
        entityManager.getTransaction().commit();
        return commodities;
    }

    public Commodity findCommodityById(int commodityId, EntityManager entityManager)
    {
        String query = "FROM Commodity c WHERE c.id = :commodityId";
        List commodityNeeded = entityManager.createQuery(query)
                .setParameter("commodityId", commodityId).getResultList();
        if(commodityNeeded.size() == 0)
        {
            return null;
        }
        return (Commodity) commodityNeeded.get(0);
    }

    public ArrayList<Commodity> getCommodityRangePrice(double startPrice, double endPrice) { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ArrayList<Commodity> commodities = commodityManager.getCommodityByRangePrice(startPrice, endPrice, entityManager);
        entityManager.getTransaction().commit();
        return commodities;
    }

    public void addComment(Comment comment) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = userManager.getUserByUseremail(comment.getUserEmail(), entityManager);
        commodityManager.addCommentToCommodity(comment, comment.getId(), user.getName(), entityManager);
        entityManager.getTransaction().commit();
    }

    public void rateCommodity(String username, int commodityId, String scoreStr) throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        if(!scoreStr.matches("-?(0|[1-9]\\d*)"))
            throw new InvalidRating();
        int score = Integer.parseInt(scoreStr);
        if(score < 1 || score > 10)
            throw new InvalidRating();
        if(!userManager.doesExist( username, entityManager))
            throw new UserNotExist(username);
        commodityManager.rateCommodity(username, commodityId, score, entityManager);
        entityManager.getTransaction().commit();
    }

//    public ArrayList<Commodity> getSuggestedCommodities(int commodityID) throws Exception
//    {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        Commodity targetCommodity = entityManager.find(Commodity.class, commodityID);
//        return CommodityManager.getMostSimilarCommodities(targetCommodity, 3);
//    }
}

