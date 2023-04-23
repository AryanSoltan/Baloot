package BalootApp;

import Baloot.BalootServer;
import ExternalServer.ExternalServer;
import InterfaceServer.InterfaceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class BalootApplication {
    final static int PORT_NUM = 8080;
    final static String externalServerAddress = "http://5.253.25.110:5000";


    public static void main(String[] args) {
        try {

            BalootServer balootServer = new BalootServer();
            ExternalServer externalServer = new ExternalServer(externalServerAddress, balootServer);
            InterfaceServer interfaceServer = new InterfaceServer(PORT_NUM, balootServer);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        SpringApplication.run(BalootApplication.class,args);
    }

}
