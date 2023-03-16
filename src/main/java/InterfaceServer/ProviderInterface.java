package InterfaceServer;

import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Provider;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class ProviderInterface
{

    final static List<String> colNamesCommiditesProvider =  Arrays.asList("Id", "Name", "Price",
            "Categories", "Rating", "In Stock", "");
    final static List<String> idNamesProvider = Arrays.asList("id", "name", "registryDate");

    public static void addProviderResponse(Javalin serverJavalin, BalootServer balootServer) {
        serverJavalin.get("/providers/:providerId", ctx -> {
            String providerId = ctx.param("providerId");
            try {
                ctx.html(createProviderPage(providerId, balootServer));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                ctx.html("<html><body><h1>" + e.getMessage() + "</h1></body></html>");
            }
        });
    }

    private static ArrayList<String> createNewCommodityRowProvider(Commodity commodity)
    {
        ArrayList<String> newCommodityRow = new ArrayList<String>();
        newCommodityRow.add(String.valueOf(commodity.getId()));
        newCommodityRow.add(commodity.getName());
        newCommodityRow.add(String.valueOf((int)commodity.getPrice()));
        newCommodityRow.add(commodity.getStringCategories());
        newCommodityRow.add(String.valueOf(commodity.getRating()));
        newCommodityRow.add(String.valueOf(commodity.getInStock()));
        String linkSection = "<a href=\"/commodities/" +
                String.valueOf(commodity.getId()) + "\">" + "Link</a>";
        newCommodityRow.add(linkSection);
        return newCommodityRow;
    }

    private static LinkedHashMap<String, String> getProviderListValue(Provider neededProvider)
    {
        LinkedHashMap<String, String> providerValues =
                new LinkedHashMap<String, String>();
        providerValues.put("Id", String.valueOf(neededProvider.getId()));
        providerValues.put("Name", neededProvider.getProviderName());
        providerValues.put("Registry Date", neededProvider.getRegistryDate());
        return providerValues;
    }

    private static ArrayList< ArrayList<String> > createProductsRows(ArrayList<Commodity> commodities) {

        ArrayList<ArrayList<String> > productRows = new ArrayList<ArrayList<String>>();
        ArrayList<String> colName = new ArrayList<String>(colNamesCommiditesProvider);
        colName = HTMLWriter.makeAllTh(colName);
        productRows.add(colName);
        for(Commodity commodity: commodities)
        {
            ArrayList<String> newRow = createNewCommodityRowProvider(commodity);
            newRow = HTMLWriter.makeAllTh(newRow);
            productRows.add(newRow);
        }
        return productRows;
    }

    private static String createProviderPage(String providerId, BalootServer balootServer) throws Exception {
        String providerHTMLPage = HTMLWriter.readHTMLFile("ProviderHeader.html");
        providerHTMLPage += "<body>";
        Provider neededProvider = balootServer.findProvider(
                Integer.parseInt(providerId));
        LinkedHashMap<String, String> providerList = getProviderListValue(neededProvider);
        providerHTMLPage += HTMLWriter.writeList(idNamesProvider, providerList);
        ArrayList< ArrayList<String> > tableProducts = createProductsRows(
                neededProvider.getCommodities());
        providerHTMLPage += HTMLWriter.writeTable(tableProducts,
                "<h3>Provided Commodities</h3>");
        providerHTMLPage += "</body></html>";
        return providerHTMLPage;
    }

}
