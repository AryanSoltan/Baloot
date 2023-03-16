package InterfaceServer;

import Baloot.BalootServer;
import Baloot.Comment;
import Baloot.Commodity;
import io.javalin.Context;
import io.javalin.Javalin;
import org.junit.jupiter.api.BeforeAll;

import java.util.*;

public class CommodityInterface
{
    final static List<String> idNamesCommidity =  Arrays.asList("id", "name", "providerId", "price",
            "categories", "rating", "inStock");
    final static List<String> colNameComments = Arrays.asList("username", "comment", "date", "", "");

    private static String createCommodityPage(String commodityId,
                                              BalootServer balootServer) throws Exception {

        String commodityHTMLPage = HTMLWriter.readHTMLFile("CommodityHeader.html");
        commodityHTMLPage += "<html>";
        commodityHTMLPage += "<body>";
        Commodity neededCommodity = balootServer.findCommodity(Integer.parseInt(commodityId));

        LinkedHashMap<String, String> listCommodityAttributes = getCommodityValues(neededCommodity);
        commodityHTMLPage += HTMLWriter.writeList(idNamesCommidity, listCommodityAttributes);
        commodityHTMLPage += String.format(
                "    <form action=\"commodityRate/%d\" method=\"POST\">\n" +
                        "  <label>Your ID:</label>\n" +
                        "    <input type=\"text\" name=\"userName\"/>\n" +
                        "    <br><br>\n" +
                "      <label>Rate(between 1 and 10):</label>\n" +
                "      <input type=\"number\" id=\"quantity\" name=\"rate\" min=\"1\" max=\"10\">\n" +
                "      <button type=\"submit\">Rate</button>\n" +
                "    </form>\n" +
                "    <br>\n" +
                "    <form action=\"addBuyList/%d\" method=\"POST\">\n" +
                        "  <label>Your ID:</label>\n" +
                        "    <input type=\"text\" name=\"userName\"/>\n" +
                        "    <br><br>\n" +
                "      <button type=\"submit\">Add to BuyList</button>\n" +
                "    </form>\n" +
                "    <br />", neededCommodity.getId(), neededCommodity.getId());
        ArrayList<ArrayList<String>> tableComments = makeTableComments(commodityId, balootServer);
        commodityHTMLPage += HTMLWriter.writeTable(tableComments, "");
        commodityHTMLPage += "</html>";
        commodityHTMLPage += "</body>";
        return commodityHTMLPage;
    }

    private static LinkedHashMap<String, String> getCommodityValues(Commodity commodity)
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

    public static void addCommodityResponse(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.get("/commodities/:commodityId", ctx -> {
            String commodityId = ctx.param("commodityId");
            try {
                ctx.html(createCommodityPage(commodityId, balootServer));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                ctx.html("<html><body><h1>" + e.getMessage() + "</h1></body></html>");
            }
        });
    }

    private static ArrayList<String> createNewCommentRow(Comment comment)
    {
        ArrayList<String> newCommentRow = new ArrayList<String>();
        newCommentRow.add(comment.getUserName());
        newCommentRow.add(comment.getText());
        newCommentRow.add(comment.getDate());
        int numLikes = comment.getLikes();
        int numDisLikes = comment.getDisLikes();
        newCommentRow.add(String.format(" <form action=\"rate/%d/%d\" method=\"POST\">\n" +
                "            User Name: <input type=\"text\" name=\"userName\">" +
                "            <input\n" +
                "              id=\"form_comment_id\"\n" +
                "              type=\"radio\"\n" +
                "              name=\"rate\"\n" +
                "              value=\"like\"\n" +
                "            />%d like\n" +
                "            <input\n" +
                "              id=\"form_comment_id\"\n" +
                "              type=\"radio\"\n" +
                "              name=\"rate\"\n" +
                "              value=\"dislike\"\n" +
                "            />%d dislike<br>\n" +
                "            <button type=\"submit\">submit</button>\n" +
                "          </form>", comment.getId(), comment.getCommodityId(),numLikes, numDisLikes));
        return newCommentRow;
    }

    private static ArrayList<ArrayList<String>> makeTableComments(String commodityId,
                                                                  BalootServer balootServer) throws Exception
    {
        ArrayList<ArrayList<String> > commentRows = new ArrayList<ArrayList<String>>();
        ArrayList<String> colName = new ArrayList<String>(colNameComments);
        colName = HTMLWriter.makeAllTh(colName);
        commentRows.add(colName);
        Commodity commodity = balootServer.getCommodityById(Integer.parseInt(commodityId));
        ArrayList<Comment> comments =  commodity.getComments();
        for(Comment comment: comments)
        {
            ArrayList<String> newRow = createNewCommentRow(comment);
            newRow = HTMLWriter.makeAllTd(newRow);
            commentRows.add(newRow);
        }
        return commentRows;
    }

    public static void addVoteComment(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.get("/voteComment/:userName/:commentId/:rate", ctx -> {
            String commentId = ctx.param("commentId");
            String userName = ctx.param("userName");
            String rate = ctx.param("rate");
            balootServer.addRatingToComment(Integer.parseInt(commentId), userName, Integer.parseInt(rate));
            ctx.html(HTMLWriter.readHTMLFile("200.html"));
        });
    }

    public static void addPostCommodityPage(Javalin serverJavalin, BalootServer balootServer)
    {
        postRateComment(serverJavalin, balootServer);
        postRateandAddCommodity(serverJavalin, balootServer);
    }

    private static void handleAddToBuyList(Context ctx, String commodityId, BalootServer balootServer)
            throws Exception {
            String userId = ctx.formParam("userName");
            balootServer.addCommidityToUserBuyList(userId, Integer.parseInt(commodityId));
    }

    private static void postRateandAddCommodity(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.post("/commodities/:typeCommand/:commodityId", ctx -> {
            try {
                String typeCommand = ctx.param("typeCommand");
                String commodityId = ctx.param("commodityId");
                switch (typeCommand)
                {
                    case("commodityRate"):
                        handleRateCommodity(ctx, commodityId, balootServer);
                        break;
                    case("addBuyList"):
                        handleAddToBuyList(ctx, commodityId, balootServer);
                        break;
                }
                ctx.redirect("/commodities/" + ctx.param("commodityId"));
            } catch (Exception e) {
                ctx.html("<html><body><h1>" + e.getMessage() + "</h1></body></html>");
            }
        });
    }

    private static void handleRateCommodity(Context ctx, String commodityId, BalootServer balootServer)
            throws Exception {
        String userName = ctx.formParam("userName");
        String rating = ctx.formParam("rate");
        balootServer.rateCommodity(userName, Integer.parseInt(commodityId), rating);
    }

    private static void postRateComment(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.post("/commodities/:typeCommand/:commentId/:commodityId", ctx -> {
            try {
                String typeCommand = ctx.param("typeCommand");
                String commentId = ctx.param("commentId");
                switch (typeCommand)
                {
                    case("rate"):
                        handleRateComment(ctx, commentId, balootServer);
                        break;
                }
                ctx.redirect("/commodities/" + ctx.param("commodityId"));

            } catch (Exception e) {
                System.out.println(e.getMessage());
                ctx.html("<html><body><h1>" + e.getMessage() + "</h1></body></html>");

            }
        });
    }

    private static void handleRateComment(Context ctx, String commentId, BalootServer balootServer)
            throws Exception {
        int commentIdInt = Integer.parseInt(commentId);
        String userName = ctx.formParam("userName");
        String likeOrDislike = ctx.formParam("rate");
        int rate = -1;
        if(Objects.equals(likeOrDislike, "like"))
        {
            rate = 1;
        }
        balootServer.addRatingToComment(commentIdInt, userName, rate);
    }

    public static void addRateCommodityResponse(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.get("/rateCommodity/:userName/:commodityId/:rate", ctx -> {
            String commodityId = ctx.param("commodityId");
            String userName = ctx.param("userName");
            String rate = ctx.param("rate");
            balootServer.rateCommodity(userName, Integer.parseInt(commodityId),
                    rate);
            ctx.html(HTMLWriter.readHTMLFile("200.html"));
        });
    }

    public static void addGetBuyPage(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.get("/addToBuyList/:userName/:commodityId", ctx -> {
            String commodityId = ctx.param("commodityId");
            String userName = ctx.param("userName");
            balootServer.addCommidityToUserBuyList(userName, Integer.parseInt(commodityId));
            ctx.html(HTMLWriter.readHTMLFile("200.html"));
        });
    }

    public static void addGetRemoveCommodity(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.get("/removeFromBuyList/:userName/:commodityId", ctx -> {
            String commodityId = ctx.param("commodityId");
            String userName = ctx.param("userName");
            balootServer.removeFromBuyList(userName, Integer.parseInt(commodityId));
            ctx.html(HTMLWriter.readHTMLFile("200.html"));
        });
    }
}
