package InterfaceServer;

import Baloot.BalootServer;
import Baloot.Commodity;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommoditiesInterface
{

    final static List<String> colNamesCommidites =  Arrays.asList("Id", "Name", "Provider Id", "Price",
            "Categories", "Rating", "In Stock", "Links");
    public static void addCategorySearchResponse(Javalin serverJavalin,
                                                 BalootServer balootServer)
    {
        serverJavalin.get("commodities/search/:category", ctx -> {
            try {
                String category = ctx.param("category");
                ctx.html(createCommoditiesPage(balootServer.
                        getCommoditiesByCategory(category)));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502);
            }
        });
    }

    private static String createCommoditiesPage(ArrayList<Commodity> commodities)
    {
        String commidityHTMLPage = HTMLWriter.readHTMLFile("CommiditesHeader.html");
        ArrayList<ArrayList<String> > tableCommodities =
                new ArrayList<ArrayList<String> >();
        ArrayList<String>colName = new ArrayList<String>(colNamesCommidites);
        colName = HTMLWriter.makeAllTh(colName);
        tableCommodities.add(colName);
        for(Commodity commodity: commodities)
        {
            ArrayList<String> newCommodityRow = createNewCommodityRow(commodity);
            newCommodityRow = HTMLWriter.makeAllTd(newCommodityRow);
            tableCommodities.add(newCommodityRow);
        }
        commidityHTMLPage += HTMLWriter.writeTable(tableCommodities, "");
        commidityHTMLPage += "</html>";
        return commidityHTMLPage;
    }

    private static ArrayList<String> createNewCommodityRow(Commodity commodity)
    {
        ArrayList<String> newCommodityRow = new ArrayList<String>();
        newCommodityRow.add(String.valueOf(commodity.getId()));
        newCommodityRow.add(commodity.getName());
        newCommodityRow.add(String.valueOf(commodity.getProviderId()));
        newCommodityRow.add(String.valueOf((int)commodity.getPrice()));
        newCommodityRow.add(commodity.getStringCategories());
        newCommodityRow.add(String.valueOf(commodity.getRating()));
        newCommodityRow.add(String.valueOf(commodity.getInStock()));
        String linkSection = "<a href=\"/commodities/" +
                String.valueOf(commodity.getId()) + "\">" + "Link</a>";
        newCommodityRow.add(linkSection);
        return newCommodityRow;
    }

    public static void addSearchResponse(Javalin serverJavalin,
                                         BalootServer balootServer)
    {
        serverJavalin.get("commodities/search/:startPrice/:endPrice", ctx -> {
            try {
                double startPrice = Double.parseDouble(ctx.param("startPrice"));
                double endPrice = Double.parseDouble(ctx.param("endPrice"));
                ctx.html(createCommoditiesPage(balootServer.
                        getCommodityRangePrice(startPrice, endPrice)));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502);
            }
        });
    }

    public static void addCommoditiesResponse(Javalin serverJavalin,
                                        BalootServer balootServer)
    {
        serverJavalin.get("/commodities", ctx -> {
            try {
                ctx.html(createCommoditiesPage(balootServer.getCommodityList()));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502);
            }
        });
    }
}
