package InterfaceServer;
import io.javalin.Javalin;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InterfaceServer {

    private Javalin serverJavalin;

    final static String commoditiesURL = "/commodities";
    final static List<String> colNamesCommidites =  Arrays.asList("Id", "Name", "Provider Id", "Price",
            "Categories", "Rating", "In Stock", "Links");

    public InterfaceServer(int portNum)
    {
        serverJavalin = Javalin.create().port(portNum)
                .start();
        addQueriesResponses();
    }

    private void addQueriesResponses()
    {
        addCommodityResponse();
    }

    private void addCommodityResponse()
    {
        serverJavalin.get(commoditiesURL, ctx -> {
            try {
                ctx.html(createCommoditiesPage());
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502);
            }
        });
    }

    private String createCommoditiesPage()
    {
        String commidityHTMLPage = HTMLWriter.readHTMLFile("CommiditesHeader.html");
        ArrayList<ArrayList<String> > tableCommodities =
                new ArrayList<ArrayList<String> >();
        ArrayList<String>colName = new ArrayList<String>(colNamesCommidites);
        tableCommodities.add(colName);
        commidityHTMLPage += HTMLWriter.writeTable(tableCommodities);
        commidityHTMLPage += "</html>";
        return commidityHTMLPage;
    }

}
