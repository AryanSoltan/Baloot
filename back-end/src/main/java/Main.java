import Baloot.BalootServer;
import Baloot.Exception.ProviderNotExist;
import ExternalServer.ExternalServer;
import InterfaceServer.InterfaceServer;

public class Main {
    final static int PORT_NUM = 8080;
    final static String externalServerAddress = "http://5.253.25.110:5000";


    public static void main(String[] args) throws Exception {

        BalootServer balootServer = new BalootServer();
        ExternalServer externalServer = new ExternalServer(externalServerAddress, balootServer);
        InterfaceServer interfaceServer  = new InterfaceServer(PORT_NUM, balootServer);
    }

}
