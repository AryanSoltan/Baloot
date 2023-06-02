package Repository;



import Baloot.*;
import Baloot.DTOObjects.*;
import Baloot.Exception.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import Baloot.Managers.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BalootServerRepo {

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


    public static BalootServerRepo getInstance() {
        if(instance == null) {
            EntityManagerFactory entityManagerFactory;
            var registry = new StandardServiceRegistryBuilder().configure().build();
            entityManagerFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            instance = new BalootServerRepo(entityManagerFactory );
        }

        return instance;

    }

    public BalootServerRepo(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        userManager = new UserManager();
//        providerManager = new ProviderManager();
        commodityManager = new CommodityManager();

        paymentManager = new PaymentManager();
    }

    public void logIn(String username, String password) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = entityManager.find(User.class, username);
        if (user == null) {
            entityManager.getTransaction().rollback();
            throw new UserNotExist(username);
        } else {
            if (password.equals(user.getPassword())) {
                sessions.put(user.getName(), user);
                return;
            } else {
                throw new IncorrectPassword();
            }
        }


    }

    public boolean userIsLoggedIn(String username, String password) throws Exception {
        if (sessions.containsKey(username))
            return true;
        else
            return false;

    }

    public void logOut(String username, String password) throws Exception {
        if (sessions.containsKey(username))
            sessions.remove(username);
        else
            throw new UserNotExist(username);

    }

    public UserDTO getUserById(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(username, entityManager);

        return new UserDTO(user);
        //  return userManager.getUserById(username, entityManagerFactory);
    }

    public Provider getProviderById(int providerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Provider provider = entityManager.find(Provider.class, providerId);
        return provider;
        //  return userManager.getUserById(username, entityManagerFactory);
    }


    public void addUser(User newUser) throws Exception {
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

    public BuyListDTO getUserBuyList(String userName) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        return userManager.getUserBuyList(userName, entityManager);

    }

    public BuyListDTO getUserPurchesedBuyList(String userName) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        return userManager.getUserPurchesedBuyList(userName, entityManager);

    }

    public void addCredit(String userName, String credit) throws Exception {
        if (!credit.matches("-?(0|[1-9]\\d*)"))
            throw new InvalidCreditValue();
        double creditVal = Double.parseDouble(credit);
        if (creditVal < 1)
            throw new InvalidCreditValue();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = findUserById(userName, entityManager);
        if (user == null) {
            entityManager.getTransaction().rollback();
            throw new UserNotExist(userName);
        } else {
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
        if (userNeeded.size() == 0) {
            return null;
        }
        return (User) userNeeded.get(0);
    }

    public void updateCommodityCountInUserBuyList(String username, int commodityId, int count) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("before in updateCommodityCountInUserBuyList");

        var countList = entityManager.createQuery("select c from CommodityInBuyList c  " +
                        "                                             where c.user.username=:userId " +
                        "                                                  and c.isBought=false " +
                        "                                                   and c.commodity.id=:commodityId")
                .setParameter("userId", username)
                .setParameter("commodityId", commodityId)
                .getResultList();
        System.out.println("after in updateCommodityCountInUserBuyList");
        User user = entityManager.find(User.class , username);
        Commodity commodity = entityManager.find(Commodity.class , commodityId);

        if (countList.isEmpty()) {

            BuyList buyList = new BuyList();
            entityManager.persist(buyList);

            CommodityInBuyList commodityInBuyList = new CommodityInBuyList(user,commodity, buyList, 1);
            entityManager.persist(commodityInBuyList);

        } else {
//
            CommodityInBuyList commodityInBuyList =(CommodityInBuyList) countList.get(0);
            if(count < 0)
            {
                if (commodityInBuyList.getNumInStock() >= count) {
                    commodityInBuyList.updateNumInStock(count);
                }
            }
            else
                commodityInBuyList.updateNumInStock(count);

        }

        entityManager.getTransaction().commit();
    }

    public boolean commodityExistsInBuylist(BuyList buyList, int commodityId) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var userBuylistID = entityManager.createNativeQuery("select c.id" +
                        "                                       from BUY_LIST_COMMODITIES b join CommodityInBuyList c on b.commodityInBuyListId = c.commodityInBuyListId " +
                        "                                       where b.buyListId =: buylistId and c.id =: commodityId")
                .setParameter("commodityId", commodityId).setParameter("buylistId", buyList.getId())
                .getResultList();
        if (userBuylistID.isEmpty()) {
            return false;
        } else
            return true;
    }


    public List getCommodityList(EntityManager entityManager) {
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

    public CommodityDTO findCommodityById(int commodityId, EntityManager entityManager) {

        List commoditiesList = entityManager.createQuery("select c from Commodity c where c.id=:commodityId")
                .setParameter("commodityId", commodityId).getResultList();


        var stream = commoditiesList.stream().map(
                commodity -> new CommodityDTO((Commodity) commodity)
        );
        //   System.out.println("\n\n\n\nin get a;; commoditis\n\n\n\n"+stream.toList());
        return (CommodityDTO) stream.toList().get(0);
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

    public void rateCommodity(String username, int commodityId, String scoreStr) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        if (!scoreStr.matches("-?(0|[1-9]\\d*)"))
            throw new InvalidRating();
        int score = Integer.parseInt(scoreStr);
        if (score < 1 || score > 10)
            throw new InvalidRating();
        if (!userManager.doesExist(username, entityManager))
            throw new UserNotExist(username);
        commodityManager.rateCommodity(username, commodityId, score, entityManager);
        entityManager.getTransaction().commit();
    }

    public List<CommodityDTO> getSuggestedCommodities(int commodityID,String username) throws Exception {
        System.out.println("in Csuggesstions");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<CommodityDTO> suggestions = commodityManager.getMostSimilarCommodities(commodityID, username, entityManager);
        return suggestions;


    }

    //todo
    public void handlePayment(String username, String code) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        System.out.println("in apply discount code");
        DiscountCode discountCode;
        if(code!="")
            discountCode = paymentManager.getDiscountCode(code, entityManager);
        else  {
            discountCode = null;
        }
        User user = entityManager.find(User.class, username);
        System.out.println("after user");


        commodityManager.checkIfAllCommoditiesAreAvailabel(username,entityManager);
        System.out.println("after check");
        double totalPrice = commodityManager.getBuylistPrice(username, entityManager);
        userManager.buyBuyList(user,discountCode, totalPrice,entityManager);
        commodityManager.handleBuy(username,entityManager);
        entityManager.getTransaction().commit();

    }

    public DiscountCodeDTO validateDiscountCode(String username, String code) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        System.out.println("befire get discount");

        DiscountCode discountCode = paymentManager.getDiscountCode(code, entityManager);

        System.out.println("after get discount");
        User user = entityManager.find(User.class, username);


        if (userManager.userHasNotUsedCode(user, discountCode, entityManager)) {

            return new DiscountCodeDTO(discountCode);
        }
        else
            throw new InvalidDiscountCode(code);
    }

    public List getAllCommodities() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List commoditiesList = commodityManager.getAllCommodities(entityManager);

        return commoditiesList;
    }

    public CommodityDTO getCommodityById(Integer id)throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CommodityDTO commodity = findCommodityById(id,entityManager);
        List<CommentDTO> comments = getCommodityComments(id);
        commodity.setComments(comments);

        return commodity;
    }

    public List getCommodityComments(int commodityId)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<CommentDTO> comments = commodityManager.getCommodityComments(commodityId, entityManager);

        return comments;



    }


    public int getUserNumBought(String username, Integer commodityId) throws Exception {


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        return userManager.countOfCommodityInBuylist( username, commodityId, entityManager);
    }

    public CommentDTO addRatingToComment(int commentId, String userName, int rate) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = entityManager.find(User.class, userName);

        Comment comment = entityManager.find(Comment.class, commentId);

        Commodity commodity = entityManager.find(Commodity.class, comment.getCommodityId());

        var resultList = entityManager.createQuery("select v from Vote v where v.user.username=:userId and" +
                        " v.comment.commentId=:commentId")
                .setParameter("userId",  userName)
                .setParameter("commentId", commentId)
                .getResultList();
        Vote vote;
        if (resultList.isEmpty()) {
            vote = new Vote(comment, user, rate);
            entityManager.persist(vote);
        } else {
            vote = (Vote) resultList.get(0);
            vote.setVote(rate);
        }
        entityManager.getTransaction().commit();

        return new CommentDTO(comment);
    }

    public CommentDTO addComment(String username, int commodityID, String commentText, String date) throws Exception { //done
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = entityManager.find(User.class, username);
        Commodity commodity = entityManager.find(Commodity.class, commodityID);
        Comment com = new Comment(user,  commodity,commentText, date);

        try {

            entityManager.persist(com);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new Exception(e.getMessage());
        }
        entityManager.getTransaction().commit();

        return new CommentDTO(com);

    }



}

