package Repository;

import Baloot.Exception.UserNotExist;
import Baloot.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserRepository
{
    private final EntityManagerFactory entityManagerFactory;

    public UserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
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
}
