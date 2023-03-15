package InterfaceServer;
import Baloot.*;
import io.javalin.Javalin;

import javax.swing.text.html.HTML;
import java.util.*;


public class InterfaceServer {

    private Javalin serverJavalin;
    BalootServer balootServer;
    public InterfaceServer(int portNum, BalootServer inputbalootServer)
    {
        serverJavalin = Javalin.create().port(portNum)
                .start();
        balootServer = inputbalootServer;
        addQueriesResponses();

    }

    private void addQueriesResponses()
    {
        CommoditiesInterface.addCommoditiesResponse(serverJavalin, balootServer);
        CommodityInterface.addCommodityResponse(serverJavalin, balootServer);
        ProviderInterface.addProviderResponse(serverJavalin, balootServer);
        UserInterface.addUserResponse(serverJavalin, balootServer);
        UserInterface.addcreditIncreaseResponse(serverJavalin, balootServer);
        CommoditiesInterface.addSearchResponse(serverJavalin, balootServer);
        CommoditiesInterface.addCategorySearchResponse(serverJavalin, balootServer);
    }
}
