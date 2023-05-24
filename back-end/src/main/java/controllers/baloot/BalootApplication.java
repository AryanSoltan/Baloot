package controllers.baloot;

import Baloot.BalootServer;
import ExternalServer.ExternalServer;
import Repository.MainRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication()

public class BalootApplication {
    final static int PORT_NUM = 8080;
    final static String externalServerAddress = "http://5.253.25.110:5000";


    public static void main(String[] args) {
        try {

//            ExternalServer externalServer = new ExternalServer(externalServerAddress,BalootServer.getInstance());
            MainRepository repository = new MainRepository();
            System.out.println("Repo finished");
//            SpringApplication.run(BalootApplication.class,args);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

}
