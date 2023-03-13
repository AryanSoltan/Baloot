package InterfaceServer;
import Baloot.BalootServer;
import Baloot.Commodity;
import io.javalin.Javalin;

import javax.swing.text.html.HTML;
import java.util.*;


public class InterfaceServer {

    private Javalin serverJavalin;

    final static String commoditiesURL = "/commodities";

    BalootServer balootServer;
    final static List<String> colNamesCommidites =  Arrays.asList("Id", "Name", "Provider Id", "Price",
            "Categories", "Rating", "In Stock", "Links");
    final static List<String> idNamesCommidity =  Arrays.asList("id", "name", "providerId", "price",
            "categories", "rating", "inStock");

    public InterfaceServer(int portNum, BalootServer inputbalootServer)
    {
        serverJavalin = Javalin.create().port(portNum)
                .start();
        addQueriesResponses();
        balootServer = inputbalootServer;
    }

    private void addQueriesResponses()
    {
        addCommoditiesResponse();
        addCommodityResponse();
    }

    private void addCommoditiesResponse()
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

    public void addCommodityResponse()
    {
        serverJavalin.get("/commodities/:commodityId", ctx -> {
            String commodityId = ctx.param("commodityId");
            try {
                ctx.html(createCommodityPage(commodityId));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(Integer.toString(ctx.status()) + ":| " + e.getMessage());
            }
        });
    }

    private String createCommodityPage(String commodityId) throws Exception {

        String commodityHTMLPage = HTMLWriter.readHTMLFile("CommodityHeader.html");
        commodityHTMLPage += "<html>";
        commodityHTMLPage += "<body>";
        Commodity neededCommodity = balootServer.findCommodity(Integer.parseInt(commodityId));

        LinkedHashMap<String, String> listCommodityAttributes = getCommodityValues(neededCommodity);
        commodityHTMLPage += HTMLWriter.writeList(idNamesCommidity, listCommodityAttributes);
        commodityHTMLPage += HTMLWriter.readHTMLFile("CommodityFooter.html");
        commodityHTMLPage += "</html>";
        commodityHTMLPage += "</body>";
        return commodityHTMLPage;
    }

    private LinkedHashMap<String, String> getCommodityValues(Commodity commodity)
    {
        LinkedHashMap<String, String> listCommodityAttributes = new LinkedHashMap<String, String>();
        listCommodityAttributes.put("Id", String.valueOf(commodity.getId()));
        listCommodityAttributes.put("Name", commodity.getName());
        listCommodityAttributes.put("Provider Id", String.valueOf(commodity.getProviderId()));
        listCommodityAttributes.put("Price", String.valueOf((int)commodity.getPrice()));
        listCommodityAttributes.put("Categories", commodity.getStringCategories());
        listCommodityAttributes.put("Rating", String.valueOf(commodity.getRating()));
        listCommodityAttributes.put("In Stock", String.valueOf(commodity.getInStock()));
        return listCommodityAttributes;
    }

    private String createCommoditiesPage()
    {
        String commidityHTMLPage = HTMLWriter.readHTMLFile("CommiditesHeader.html");
        ArrayList<ArrayList<String> > tableCommodities =
                new ArrayList<ArrayList<String> >();
        ArrayList<String>colName = new ArrayList<String>(colNamesCommidites);
        tableCommodities.add(colName);
        ArrayList<Commodity> commodities = balootServer.getCommodityList();
        for(Commodity commodity: commodities)
        {
            ArrayList<String> newCommodityRow = createNewCommodityRow(commodity);
            tableCommodities.add(newCommodityRow);
        }
        commidityHTMLPage += HTMLWriter.writeTable(tableCommodities);
        commidityHTMLPage += "</html>";
        return commidityHTMLPage;
    }

    private ArrayList<String> createNewCommodityRow(Commodity commodity)
    {
        ArrayList<String> newCommodityRow = new ArrayList<String>();
        newCommodityRow.add(String.valueOf(commodity.getId()));
        newCommodityRow.add(commodity.getName());
        newCommodityRow.add(String.valueOf(commodity.getProviderId()));
        newCommodityRow.add(String.valueOf((int)commodity.getPrice()));
        newCommodityRow.add(commodity.getStringCategories());
        newCommodityRow.add(String.valueOf(commodity.getRating()));
        newCommodityRow.add(String.valueOf(commodity.getInStock()));
        newCommodityRow.add("Link");
        return newCommodityRow;
    }

}
