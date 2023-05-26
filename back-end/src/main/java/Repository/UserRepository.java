package Repository;

import Baloot.BuyList;
import Baloot.Commodity;
import Baloot.CommodityInBuyList;
import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.NotEnoughCredit;
import Baloot.Exception.UserNotExist;
import Baloot.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserRepository
{
    private final EntityManagerFactory entityManagerFactory;
    private ExternalRepository externalRepository;

    public UserRepository(EntityManagerFactory entityManagerFactory, ExternalRepository externalRepository) {
        this.entityManagerFactory = entityManagerFactory;
        this.externalRepository = externalRepository;
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
            Commodity newCommodity = externalRepository.findCommodityById(commodityId, entityManager);
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
            externalRepository.checkIfAllCommoditiesAreAvailable(userBuyList);
            double totalPrice = userBuyList.getBuylistPrice();
            System.out.println("total price is"+totalPrice);
            if(user.getCredit() < totalPrice) {
                throw new NotEnoughCredit();
            }
            externalRepository.decreaseStock(userBuyList);
            userBuyList.makeEmpty();
        }
}

