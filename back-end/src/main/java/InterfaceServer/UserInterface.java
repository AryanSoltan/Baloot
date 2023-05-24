//package InterfaceServer;
//
//import Baloot.BalootServer;
//import Baloot.Commodity;
//import Baloot.User;
//import io.javalin.Context;
//import io.javalin.Javalin;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//import static InterfaceServer.ProviderInterface.idNamesProvider;
//
//public class UserInterface
//{
//
//    final static List<String> colNamesCommiditesBought =  Arrays.asList("Id", "Name", "Provider Id", "Price",
//            "Categories", "Rating", "In Stock", "", "");
//    public static void addUserResponse(Javalin serverJavalin, BalootServer balootServer)
//    {
//        serverJavalin.get("/users/:userId", ctx -> {
//            String userId = ctx.param("userId");
//            try {
//                ctx.html(createUserPage(userId, balootServer));
//            }
//            catch (Exception e){
//                System.out.println(e.getMessage());
//                ctx.html("<html><body><h1>" + e.getMessage() + "</h1></body></html>");
//            }
//        });
//    }
//    private static String createUserPage(String userId, BalootServer balootServer) throws Exception {
//        String userHTMLPage = HTMLWriter.readHTMLFile("UserHeader.html");
//        userHTMLPage += "<body>";
//        User neededUser = balootServer.findUser(userId);
//        LinkedHashMap<String, String> userList = getUserListValue(neededUser);
//        userHTMLPage += HTMLWriter.writeList(idNamesProvider, userList);
//        ArrayList<ArrayList<String>> boughtCommodities = createCommodityRows(
//                neededUser.getBoughtCommodities(), neededUser, true);
//        userHTMLPage += HTMLWriter.writeTable(boughtCommodities,
//                "<h3>Buy List</h3>");
////        ArrayList<ArrayList<String>> purchasedCommodities = createCommodityRows(
////                neededUser.getPurchasedCommodities(), neededUser, false);
////        userHTMLPage += HTMLWriter.writeTable(purchasedCommodities,
////                "<h3>Purchased List</h3>");
//        userHTMLPage += "</body></html>";
//        return userHTMLPage;
//    }
//
//    private static ArrayList<ArrayList<String>> createCommodityRows(ArrayList<Commodity> commodities,
//                                                                          User user, boolean isRemove)
//    {
//        ArrayList<ArrayList<String> > productRows = new ArrayList<ArrayList<String>>();
//        ArrayList<String> colName = new ArrayList<String>(colNamesCommiditesBought);
//        if(!isRemove)
//            colName.remove(colName.size() - 1);
//        colName = HTMLWriter.makeAllTh(colName);
//        productRows.add(colName);
//        for(Commodity commodity: commodities)
//        {
//            ArrayList<String> newRow = createNewCommodityBought(commodity, user, isRemove);
//            newRow = HTMLWriter.makeAllTh(newRow);
//            productRows.add(newRow);
//        }
//        return productRows;
//    }
//
//    private static ArrayList<String> createNewCommodityBought(Commodity commodity, User user, boolean isRemove)
//    {
//        ArrayList<String> newCommodityRow = new ArrayList<String>();
//        newCommodityRow.add(String.valueOf(commodity.getId()));
//        newCommodityRow.add(commodity.getName());
//        newCommodityRow.add(String.valueOf(commodity.getProviderId()));
//        newCommodityRow.add(String.valueOf((int)commodity.getPrice()));
//        newCommodityRow.add(commodity.getStringCategories());
//        newCommodityRow.add(String.valueOf(commodity.getRating()));
//        newCommodityRow.add(String.valueOf(commodity.getInStock()));
//        String linkSection = "<a href=\"/commodities/" +
//                String.valueOf(commodity.getId()) + "\">" + "Link</a>";
//        newCommodityRow.add(linkSection);
//        if(isRemove == true) {
//            newCommodityRow.add(String.format("<form action=\"removeCommodityFromBuyList/%s/%d\" method=\"POST\" >\n" +
//                    "                    <input id=\"form_commodity_id\" type=\"hidden\"" +
//                    " name=\"commodityId\" value=\"2341\">\n" +
//                    "                    <button type=\"submit\">Remove</button>\n" +
//                    "                </form>", user.getName(), commodity.getId()));
//        }
//        return newCommodityRow;
//    }
//
//
//    private static LinkedHashMap<String, String> getUserListValue(User neededUser)
//    {
//        LinkedHashMap<String, String> userValues =
//                new LinkedHashMap<String, String>();
//        userValues.put("Name", neededUser.getName());
//        userValues.put("Email", neededUser.getEmail());
//        userValues.put("Birth Data", neededUser.getBirthDate());
//        userValues.put("address", neededUser.getAddress());
//        userValues.put("credit", String.valueOf((int)neededUser.getCredit()));
//        String formValue = String.format("<form action=\"payment/%s\" method=\"POST\" >\n" +
//                "                <label>Buy List Payment</label>\n" +
//                "                <input id=\"form_payment\" type=\"hidden\" name=\"userId\" value=\"%s\">\n" +
//                "                <button type=\"submit\">Payment</button>\n" +
//                "            </form>", neededUser.getName(), neededUser.getName());
//        userValues.put(formValue, "");
//        userValues.put(String.format(" <form action=\"increaseAmount/%s\" method=\"POST\" >\n" +
//                "                <label>Increase Credit</label>\n" +
//                "                <input id=\"amount\" type=\"number\" name=\"amount\">\n" +
//                "                <button type=\"submit\">Increase</button>\n" +
//                "            </form>", neededUser.getName()), "");
//        return userValues;
//    }
//
//    public static void addcreditIncreaseResponse(Javalin serverJavalin, BalootServer balootServer)
//    {
//        serverJavalin.get("/addCredit/:userName/:credit", ctx -> {
//            try {
//                String userName = ctx.param("userName");
//                String credit = ctx.param("credit");
//                increaseCredit(userName, credit, balootServer);
//                ctx.html(HTMLWriter.readHTMLFile("200.html"));
//            } catch (Exception e){
//                System.out.println(e.getMessage());
//                ctx.html("<html><body><h1>" + e.getMessage() + "</h1></body></html>");
//            }
//        });
//    }
//
//    public static void increaseCredit(String userName, String credit,
//                                      BalootServer balootServer) throws Exception {
//        User neededUser = balootServer.findUser(userName);
//        neededUser.addCredit(Double.parseDouble(credit));
//    }
//
//
//    public static void addPostUserPage(Javalin serverJavalin, BalootServer balootServer)
//    {
//        serverJavalin.post("/users/:typeCommand/:userName", ctx -> {
//            try {
//                String typeCommand = ctx.param("typeCommand");
//                String userName = ctx.param("userName");
//                switch (typeCommand)
//                {
//                    case("increaseAmount"):
//                        handleIncreaseAmount(ctx, userName, balootServer);
//                        break;
//                    case("payment"):
//                        handlePayment(userName, balootServer);
//                        break;
//                }
//                ctx.redirect("/users/" + ctx.param("userName"));
//            } catch (Exception e) {
//                ctx.html("<html><body><h1>" + e.getMessage() + "</h1></body></html>");
//            }
//        });
//        serverJavalin.post("/users/:typeCommand/:userName/:commodityId", ctx -> {
//            try {
//                String typeCommand = ctx.param("typeCommand");
//                String userName = ctx.param("userName");
//                int commodityId = Integer.parseInt(ctx.param("commodityId"));
//                switch (typeCommand)
//                {
//                    case("removeCommodityFromBuyList"):
//                        handleRemoveFromBuyList(commodityId, userName, balootServer);
//                        break;
//                }
//                ctx.redirect("/users/" + ctx.param("userName"));
//            } catch (Exception e) {
//                ctx.html("<html><body><h1>" + e.getMessage() + "</h1></body></html>");
//            }
//        });
//    }
//
//    private static void handleRemoveFromBuyList(int commodityId, String userName, BalootServer balootServer)
//            throws Exception {
//        balootServer.removeFromBuyList(userName, commodityId);
//    }
//
//    private static void handlePayment(String userName, BalootServer balootServer)
//            throws Exception {
//        balootServer.handlePaymentUser(userName);
//    }
//
//    private static void handleIncreaseAmount(Context ctx, String userName,
//                                             BalootServer balootServer) throws Exception {
//        String amount = ctx.formParam("amount");
//        increaseCredit(userName, amount, balootServer);
//    }
//}
