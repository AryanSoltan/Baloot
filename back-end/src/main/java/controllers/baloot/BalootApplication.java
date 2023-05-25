package controllers.baloot;//import Baloot.BalootServer;
//import ExternalServer.ExternalServer;
//import ExternalRepository;
import Repository.ExternalRepository;
import Repository.UserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication()

public class BalootApplication {
    final static int PORT_NUM = 8080;
    final static String externalServerAddress = "http://5.253.25.110:5000";


    public static void main(String[] args) {
        try {

//            ExternalServer externalServer = new ExternalServer(externalServerAddress,BalootServer.getInstance());
            ExternalRepository externalRepository = new ExternalRepository(externalServerAddress);
            UserRepository userRepo = new UserRepository(externalRepository.getEntityManagerFactory());
//            userRepo.addCredit("amir", 10);
          //  MainRepository repository = new MainRepository();
           // System.out.println("Repo finished");
//            SpringApplication.run(controllers.baloot.BalootApplication.class,args);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

}
