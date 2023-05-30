package Repository;



import Baloot.*;
import Baloot.Exception.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import Baloot.Managers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BalootServerRepo
{

    private final EntityManagerFactory entityManagerFactory;

    final private HashMap<String, User> sessions = new HashMap<>();

    private UserManager userManager;
//    private ProviderManager providerManager;
    private CommodityManager commodityManager;

    private PaymentManager paymentManager;
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
                sessions.put(user.getName(), user);
                return ;
            }
            else{
                throw new IncorrectPassword();
            }
        }


    }

    public boolean userIsLoggedIn(String username, String password) throws Exception
    {
        if(sessions.containsKey(username))
            return true;
        else
            return false;

    }

    public void logOut(String username, String password) throws Exception
    {
        if(sessions.containsKey(username))
           sessions.remove(username);
        else
            throw new UserNotExist(username);

    }

    public User getUserById(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(username, entityManager);
        return user;
      //  return userManager.getUserById(username, entityManagerFactory);
    }

    public Provider getProviderById(int providerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Provider provider = entityManager.find(Provider.class, providerId);
        return provider;
        //  return userManager.getUserById(username, entityManagerFactory);
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
        return userManager.getUserBuyList(userName, entityManager);

    }

    public BuyList getUserPurchesedBuyList(String userName) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        return userManager.getUserPurchesedBuyList(userName, entityManager);

    }

    public void addCredit(String userName, String credit) throws Exception {
        if(!credit.matches("-?(0|[1-9]\\d*)"))
            throw new InvalidCreditValue();
        double creditVal = Double.parseDouble(credit);
        if(creditVal < 1)
            throw new InvalidCreditValue();
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
            user.addCredit(creditVal);
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
    public void addCommidityToUserBuyList(String username, int commodityId) throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(username, entityManager);
        BuyList buyList = getUserBuyList(username);
        if(!commodityExistsInBuylist(buyList,commodityId)) {
            Commodity newCommodity = findCommodityById(commodityId, entityManager);
            CommodityInBuyList commodity = new CommodityInBuyList(newCommodity, 1);
            entityManager.persist(commodity);
            //  buyList.addSingleCommodityInBuyList(commodity);
        }
        else
        {
            CommodityInBuyList commodityInBuyList = buyList.getCommodityInBuyList(commodityId);
            commodityInBuyList.increaseOne();
        }
        entityManager.getTransaction().commit();
    }

    public boolean commodityExistsInBuylist(BuyList buyList, int commodityId) throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var userBuylistID = entityManager.createNativeQuery("select c.id" +
                        "                                       from BUY_LIST_COMMODITIES b join CommodityInBuyList c on b.commodityInBuyListId = c.commodityInBuyListId " +
                        "                                       where b.buyListId =: buylistId and c.id =: commodityId")
                .setParameter("commodityId",commodityId).setParameter("buylistId",buyList.getId())
                .getResultList();
        if(userBuylistID.isEmpty())
        {
            return false;
        }
        else
            return true;
    }

    public void removeFromBuyList(String username, int commodityId) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(username, entityManager);
        BuyList buyList = getUserBuyList(username);
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
        BuyList userBuyList = getUserBuyList(userName);
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

    public ArrayList<Commodity> getSuggestedCommodities(int commodityID) throws Exception
    {
        System.out.println("in Csuggesstions");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        ArrayList<Commodity> suggestions = commodityManager.getMostSimilarCommodities(commodityID,entityManager);
        return suggestions;


    }

    //todo
    public void applyDiscountCode(String username, String code) throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        DiscountCode discountCode = paymentManager.getDiscountCode(code,entityManager);
        User user = getUserById(username);
        userManager.addDiscountCodeToUserBuyList(user, discountCode,entityManager);
    }

    public DiscountCode validateDiscountCode(String username, String code) throws Exception
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        DiscountCode discountCode = paymentManager.getDiscountCode(code,entityManager);
        User user = getUserById(username);
        if(!userManager.userHasUsedCode(user, discountCode, entityManager))
            return discountCode;
        else
            throw new InvalidDiscountCode(code);
    }

    public ArrayList<Commodity> getAllCommodities()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List commoditiesList = commodityManager.getAllCommodities(entityManager);
        return (ArrayList<Commodity>) commoditiesList;
    }

    public Commodity getCommodityById(Integer id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        return findCommodityById(id,  entityManager);


    }

    public int getUserNumBought(String username, Integer commodityId) throws Exception {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            var count = entityManager.createNativeQuery("select c.numInStock" +
                            "                                       from CommodityInBuyList c " +
                            "                                       where c.id =: commodityId")
                    .setParameter("commodityId",commodityId)
                    .getSingleResult();
            return (int) count;
      //  return userManager.getUserNumBought(username, commodityId);
    }

    public Comment addRatingToComment(int commentId, String userName, int rate) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = getUserById(userName);
        return commodityManager.rateCommoditiesComment(commentId , user, rate,entityManager );
    }
}

