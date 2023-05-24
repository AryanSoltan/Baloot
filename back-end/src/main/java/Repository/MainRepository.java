package Repository;

import Baloot.User;
import org.eclipse.jetty.websocket.common.events.AbstractEventDriver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainRepository {

    SessionFactory sessionFactory;
    public MainRepository()
    {
        System.out.println("Enter Main Repo");
        Configuration config = new Configuration();
        config.configure();
        System.out.println(config);
        sessionFactory = config.buildSessionFactory();
        User user = new User("a", "a", "a", "a", "a", 1);
        Session session=sessionFactory.openSession();
        session.save(user);
        Transaction t = session.beginTransaction();
        t.commit();
    }

}
