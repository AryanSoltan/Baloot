import Baloot.BalootServer;
import InterfaceServer.InterfaceServer;

public class Main {
    final static int PORT_NUM = 8080;


    public static void main(String[] args) {

        InterfaceServer interfaceServer  = new InterfaceServer(PORT_NUM);
    }

}
