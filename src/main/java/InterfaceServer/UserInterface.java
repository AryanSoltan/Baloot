package InterfaceServer;

import Baloot.BalootServer;
import Baloot.User;
import io.javalin.Context;
import io.javalin.Javalin;

import java.util.LinkedHashMap;

import static InterfaceServer.ProviderInterface.idNamesProvider;

public class UserInterface
{
    public static void addUserResponse(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.get("/users/:userId", ctx -> {
            String userId = ctx.param("userId");
            try {
                ctx.html(createUserPage(userId, balootServer));
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(Integer.toString(ctx.status()) + ":| " + e.getMessage());
            }
        });
    }
    private static String createUserPage(String userId, BalootServer balootServer) throws Exception {
        String userHTMLPage = HTMLWriter.readHTMLFile("UserHeader.html");
        userHTMLPage += "<body>";
        User neededUser = balootServer.findUser(userId);
        LinkedHashMap<String, String> providerList = getUserListValue(neededUser);
        userHTMLPage += HTMLWriter.writeList(idNamesProvider, providerList);
        userHTMLPage += "</body></html>";
        return userHTMLPage;
    }


    private static LinkedHashMap<String, String> getUserListValue(User neededUser)
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
        userValues.put(String.format(" <form action=\"increaseAmount/%s\" method=\"POST\" >\n" +
                "                <label>Increase Credit</label>\n" +
                "                <input id=\"amount\" type=\"number\" name=\"amount\">\n" +
                "                <button type=\"submit\">Increase</button>\n" +
                "            </form>", neededUser.getName()), "");
        return userValues;
    }

    public static void addcreditIncreaseResponse(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.get("/addCredit/:userName/:credit", ctx -> {
            try {
                String userName = ctx.param("userName");
                String credit = ctx.param("credit");
                increaseCredit(userName, credit, balootServer);
                ctx.html(HTMLWriter.readHTMLFile("200.html"));
            } catch (Exception e){
                System.out.println(e.getMessage());
                ctx.status(502).result(Integer.toString(ctx.status()) + ":| " + e.getMessage());
            }
        });
    }

    public static void increaseCredit(String userName, String credit,
                                      BalootServer balootServer) throws Exception {
        User neededUser = balootServer.findUser(userName);
        neededUser.addCredit(Double.parseDouble(credit));
    }


    public static void addPostUserPage(Javalin serverJavalin, BalootServer balootServer)
    {
        serverJavalin.post("/users/:typeCommand/:userName", ctx -> {
            try {
                String typeCommand = ctx.param("typeCommand");
                String userName = ctx.param("userName");
                switch (typeCommand)
                {
                    case("increaseAmount"):
                        handleIncreaseAmount(ctx, userName, balootServer);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            ctx.redirect("/users/" + ctx.param("userName"));
        });
    }

    private static void handleIncreaseAmount(Context ctx, String userName,
                                             BalootServer balootServer) throws Exception {
        String amount = ctx.formParam("amount");
        increaseCredit(userName, amount, balootServer);
    }
}
