package InterfaceServer;
import Baloot.*;
import io.javalin.Context;
import io.javalin.ErrorHandler;
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
        CommodityInterface.addVoteComment(serverJavalin, balootServer);
        CommoditiesInterface.addSearchResponse(serverJavalin, balootServer);
        CommoditiesInterface.addCategorySearchResponse(serverJavalin, balootServer);
        UserInterface.addPostUserPage(serverJavalin, balootServer);
        CommodityInterface.addRateCommodityResponse(serverJavalin, balootServer);
        CommodityInterface.addPostCommodityPage(serverJavalin, balootServer);
        CommodityInterface.addGetBuyPage(serverJavalin, balootServer);
        CommodityInterface.addGetRemoveCommodity(serverJavalin, balootServer);
        ErrorHandler handlerError = new ErrorHandler() {
            @Override
            public void handle(Context ctx) {
                ctx.html("<html><body><h1>" + "404 Not Found" + "</h1></body></html>");
            }
        };
        serverJavalin.error(404, handlerError);
    }

    public void closeConnection()
    {
        serverJavalin.stop();
    }
}
