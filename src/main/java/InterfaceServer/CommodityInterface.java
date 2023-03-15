package InterfaceServer;

import Baloot.BalootServer;
import Baloot.Comment;
import Baloot.Commodity;
import io.javalin.Javalin;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

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
        commodityHTMLPage += HTMLWriter.readHTMLFile("CommodityFooter.html");
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
                ctx.status(502).result(Integer.toString(ctx.status()) + ":| " + e.getMessage());
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
        newCommentRow.add(String.format(" <form action=\"\" method=\"POST\">\n" +
                "            <label for=\"\">%d</label>\n" +
                "            <input\n" +
                "              id=\"form_comment_id\"\n" +
                "              type=\"hidden\"\n" +
                "              name=\"comment_id\"\n" +
                "              value=\"01\"\n" +
                "            />\n" +
                "            <button type=\"submit\">like</button>\n" +
                "          </form>", numLikes));
        newCommentRow.add(String.format(" <form action=\"\" method=\"POST\">\n" +
                "            <label for=\"\">%d</label>\n" +
                "            <input\n" +
                "              id=\"form_comment_id\"\n" +
                "              type=\"hidden\"\n" +
                "              name=\"comment_id\"\n" +
                "              value=\"01\"\n" +
                "            />\n" +
                "            <button type=\"submit\">dislike</button>\n" +
                "          </form>", numDisLikes));
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

}
