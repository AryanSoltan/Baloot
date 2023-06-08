package controllers.baloot;//import Baloot.BalootServer;
//import ExternalServer.ExternalServer;
//import ExternalRepository;
import Baloot.Comment;
import Baloot.Commodity;
import Config.CorsConfig;
import Config.JWTFilterConfig;
import Repository.ExternalRepository;
import Repository.BalootServerRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })

//@Import(JWTFilterConfig.class)
public class BalootApplication {
    final static int PORT_NUM = 8080;
    final static String externalServerAddress = "http://5.253.25.110:5000";

    public static void main(String[] args) {
        try {

//            ExternalServer externalServer = new ExternalServer(externalServerAddress,BalootServer.getInstance());
            ExternalRepository externalRepository = new ExternalRepository(externalServerAddress);
            BalootServerRepo balootServerRepo = new BalootServerRepo(externalRepository.getEntityManagerFactory());
//            balootServerRepo.addComment(new Comment("amir@gmail.com" , 1, "very good",
//                    "1999"));
//            EntityManagerFactory entityManagerFactory = externalRepository.getEntityManagerFactory();
//            EntityManager entityManager = entityManagerFactory.createEntityManager();
//            entityManager.getTransaction().begin();
//            entityManager.getTransaction().commit();
  //          balootServerRepo.getSuggestedCommodities( 1);
   //        balootServerRepo.addCommidityToUserBuyList("amir", 1);
//            balootServerRepo.handlePaymentUser("amir");
           // BalootServerRepo.addCommidityToUserBuyList("amir", 1);
//            userRepo.removeFromBuyList("amir", 1);
//            userRepo.addCredit("amir", 10);
          //  MainRepository repository = new MainRepository();
           // System.out.println("Repo finished");

            ConfigurableApplicationContext ctx = SpringApplication.run(BalootApplication.class,args);
//            JWTFilterConfig configuration = ctx.getBean(JWTFilterConfig.class);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

}
