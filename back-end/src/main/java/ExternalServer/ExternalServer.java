//package ExternalServer;
//
//import Baloot.*;
//import Baloot.Exception.NotEnoughCredit;
//import Baloot.Exception.ProviderNotExist;
//import Baloot.Exception.UserNotExist;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.JSONParser;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Type;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class ExternalServer {
//
//    String serverAddress;
//    BalootServer balootServer;
//    public ExternalServer(String inputServerAddress, BalootServer inputBalootServer) //throws Exception
//    {
//        try {
//            serverAddress = inputServerAddress;
//            balootServer = inputBalootServer;
//            addProviders();
//
//            addCommodities();
//            addUsers();
//            addComments();
//            addDiscountCode();
//        }
//        catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    private void addComments() //throws Exception
//    {
//        try {
//            String pageComments = getRequest("/api/comments");
//            Gson gsonParser = new GsonBuilder().create();
//            List<Comment> comments = gsonParser.fromJson(pageComments,
//                    new TypeToken<List<Comment>>() {
//                    }.getType());
//            for (Comment comment : comments) {
//                balootServer.addComment(comment);
//            }
//        }
//        catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//
//
//    private void addUsers() //throws Exception
//    {
//        try {
//            String pageUsers = getRequest("/api/users");
//            Gson gsonParser = new GsonBuilder().create();
//            List<User> users = gsonParser.fromJson(pageUsers,
//                    new TypeToken<List<User>>() {
//                    }.getType());
//            for (User user : users) {
////                System.out.println(user.getName());
//                balootServer.addUser(user);
//            }
//        }
//        catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    public void addProviders()
//    {
//
//        String pageProviders = getRequest("/api/v2/providers");
//        Gson gsonParser = new GsonBuilder().create();
//        List<Provider> providers = gsonParser.fromJson(pageProviders,
//                new TypeToken<List<Provider>>() {}.getType());
//        for(Provider provider: providers)
//        {
//            provider.setCommoditiesEmpty();
//            balootServer.addProvider(provider);
//        }
//    }
//    public void addCommodities()// throws Exception
//    {
//        try {
//            String pageCommodities = getRequest("/api/v2/commodities");
//            Gson gsonParser = new GsonBuilder().create();
//            List<Commodity> commodities = gsonParser.fromJson(pageCommodities,
//                    new TypeToken<List<Commodity>>() {
//                    }.getType());
//            for (Commodity commodity : commodities) {
//
//                balootServer.addCommidity(commodity);
//            }
//        }
//        catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    public void addDiscountCode()// throws Exception
//    {
//        try {
//            String pageDiscounts = getRequest("/api/discount");
//            Gson gsonParser = new GsonBuilder().create();
//            List<DiscountCode> discountCodes = gsonParser.fromJson(pageDiscounts,
//                    new TypeToken<List<DiscountCode>>() {
//                    }.getType());
//            for (DiscountCode discountCode : discountCodes) {
//
//                balootServer.addDiscountCode(discountCode);
//            //    throw new UserNotExist(discountCode.getCode());
//            }
//
//
//        }
//        catch (Exception exception) {
//            System.out.println(exception.getMessage());
//        }
//    }
//
//    public String getRequest(String inputUrl)
//    {
//        String result = null;
//        try {
//            URL url = new URL(serverAddress + inputUrl);
//            HttpURLConnection urlConnection = (HttpURLConnection) url
//                    .openConnection();
//            urlConnection.setRequestMethod("GET");
//            InputStream inputStream = urlConnection.getInputStream();
//            result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return result;
//    }
//
//}
