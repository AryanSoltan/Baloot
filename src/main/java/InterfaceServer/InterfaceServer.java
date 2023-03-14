package InterfaceServer;
import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Provider;
import Baloot.User;
import io.javalin.Javalin;

import javax.swing.text.html.HTML;
import java.util.*;


public class InterfaceServer {

    private Javalin serverJavalin;

    final static String commoditiesURL = "/commodities";

    BalootServer balootServer;
    final static List<String> colNamesCommidites =  Arrays.asList("Id", "Name", "Provider Id", "Price",
            "Categories", "Rating", "In Stock", "Links");
    final static List<String> colNamesCommiditesProvider =  Arrays.asList("Id", "Name", "Price",
            "Categories", "Rating", "In Stock", "");
    final static List<String> idNamesCommidity =  Arrays.asList("id", "name", "providerId", "price",
            "categories", "rating", "inStock");
    final static List<String> idNamesProvider = Arrays.asList("id", "name", "registryDate");

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
        addProviderResponse();
        addUserResponse();
        addcreditIncreaseResponse();
        addSearchResponse();
        addCategorySearchResponse();
    }

    private void addCategorySearchResponse()
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

    private void addcreditIncreaseResponse()
    {
        serverJavalin.get("/addCredit/:userName/:credit", ctx -> {
            try {
                String userName = ctx.param("userName");
                String credit = ctx.param("credit");
                increaseCredit(userName, credit);
                ctx.html(HTMLWriter.readHTMLFile("200.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(Integer.toString(ctx.status()) + ":| " + e.getMessage());
            }
        });
    }

    public void increaseCredit(String userName, String credit) throws Exception {
        User neededUser = balootServer.findUser(userName);
        neededUser.addCredit(Double.parseDouble(credit));
    }

    private void addUserResponse()
    {
        serverJavalin.get("/users/:userId", ctx -> {
            String userId = ctx.param("userId");
            try {
                ctx.html(createUserPage(userId));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(Integer.toString(ctx.status()) + ":| " + e.getMessage());
            }
        });
    }

    private String createUserPage(String userId) throws Exception {
        String userHTMLPage = HTMLWriter.readHTMLFile("UserHeader.html");
        userHTMLPage += "<body>";
        User neededUser = balootServer.findUser(userId);
        LinkedHashMap<String, String> providerList = getUserListValue(neededUser);
        userHTMLPage += HTMLWriter.writeList(idNamesProvider, providerList);
        userHTMLPage += "</body></html>";
        return userHTMLPage;
    }

    private LinkedHashMap<String, String> getUserListValue(User neededUser)
    {
        LinkedHashMap<String, String> userValues =
                new LinkedHashMap<String, String>();
        userValues.put("Name", neededUser.getName());
        userValues.put("Email", neededUser.getEmail());
        userValues.put("Birth Data", neededUser.getBirthDate());
        userValues.put("address", neededUser.getAddress());
        userValues.put("credit", String.valueOf((int)neededUser.getCredit()));
        String formValue = String.format("<form action=\"\" method=\"POST\" >\n" +
                "                <label>Buy List Payment</label>\n" +
                "                <input id=\"form_payment\" type=\"hidden\" name=\"userId\" value=\"%s\">\n" +
                "                <button type=\"submit\">Payment</button>\n" +
                "            </form>", neededUser.getName());
        userValues.put(formValue, "");
        return userValues;
    }

    private void addProviderResponse() {
        serverJavalin.get("/providers/:providerId", ctx -> {
            String providerId = ctx.param("providerId");
            try {
                ctx.html(createProviderPage(providerId));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(Integer.toString(ctx.status()) + ":| " + e.getMessage());
            }
        });
    }

    private String createProviderPage(String providerId) throws Exception {
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

    private ArrayList< ArrayList<String> > createProductsRows(ArrayList<Commodity> commodities) {

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

    private LinkedHashMap<String, String> getProviderListValue(Provider neededProvider)
    {
        LinkedHashMap<String, String> providerValues =
                new LinkedHashMap<String, String>();
        providerValues.put("Id", String.valueOf(neededProvider.getId()));
        providerValues.put("Name", neededProvider.getProviderName());
        providerValues.put("Registry Date", neededProvider.getRegistryDate());
        return providerValues;
    }

    private void addSearchResponse()
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

    private void addCommoditiesResponse()
    {
        serverJavalin.get(commoditiesURL, ctx -> {
            try {
                ctx.html(createCommoditiesPage(balootServer.getCommodityList()));
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

    private String createCommoditiesPage(ArrayList<Commodity> commodities)
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
        String linkSection = "<a href=\"/commodities/" +
                String.valueOf(commodity.getId()) + "\">" + "Link</a>";
        newCommodityRow.add(linkSection);
        return newCommodityRow;
    }

    private ArrayList<String> createNewCommodityRowProvider(Commodity commodity)
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

}
