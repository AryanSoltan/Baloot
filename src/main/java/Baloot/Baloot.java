package Baloot;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.Scanner;
import java.util.*;
public class Baloot {

    static final int TYPE_COMMAND_INDEX = 0;
    static final int JSON_INDEX = 1;
    static final String ADD_USER_COMMAND = "addUser";

    static final String ADD_PROVIDER_COMMAND = "addProvider";

    static final String ADD_COMMODITY = "addCommodity";

    static final String GET_COMMODITIES_LIST="getCommoditiesList";
    static final String RATE_COMMODITY="rateCommodity";
    static final String ADD_TO_BUYLIST="addToBuyList";

    static final String REMOVE_FROM_BUYLIST="removeFromBuyList";

    static final String GET_COMMODITY_BY_ID="getCommodityById";

    static final String GET_COMMODITIES_BY_Category="getCommoditiesByCategory";
    static final String GET_BUY_LIST="fetBuyList";

    //static final String SUCCESSFUL = "addUser";
    static final int MAX_PARSE_COMMAND = 2;


    //private static BalootDatabase balootDatabase;
    private static BalootDatabase balootDatabase = new BalootDatabase();
    Baloot()
    {

    }

    private static void printOutput(boolean successful, String data) {

        JSONObject jsonObject = new JSONObject();

        if (successful){
            jsonObject.put("success", successful);
            jsonObject.put("data", data);

        }
        System.out.println(jsonObject.toString());

    }
    private static void run()
    {
        Scanner inputReader = new Scanner(System.in);
        while(true)
        {
            String new_command = inputReader.nextLine();
            String[] command_parsed = new_command.split(" ", MAX_PARSE_COMMAND);
            String data="{}";
            if(command_parsed.length == MAX_PARSE_COMMAND)
                data = command_parsed[Baloot.JSON_INDEX];

             parseCommand(command_parsed[Baloot.TYPE_COMMAND_INDEX], data);
        }
    }

    private static void parseCommand(String command_input, String json_input)
    {
        JSONParser jsonCommand = new JSONParser();
        try {

            JSONObject jsonParser = (JSONObject)jsonCommand.parse(json_input);

            switch(command_input)
            {
                case Baloot.ADD_USER_COMMAND:
                    addUser(jsonParser);
                    break;

                case Baloot.ADD_PROVIDER_COMMAND:
                    addProvider(jsonParser);
                    break;
                case Baloot.ADD_COMMODITY:
                    addCommodity(jsonParser);
                    break;
                case Baloot.GET_COMMODITIES_LIST:
                    getCommoditiesList();
                    break;
                case Baloot.RATE_COMMODITY:
                    rateCommodity(jsonParser);
                    break;
                case Baloot.ADD_TO_BUYLIST:
                    addToBuyList(jsonParser);
                    break;
                case Baloot.REMOVE_FROM_BUYLIST:
                    removeFromBuyList(jsonParser);
                    break;
                case Baloot.GET_COMMODITY_BY_ID:
                    getCommodityById(jsonParser);
                    break;
                case Baloot.GET_COMMODITIES_BY_Category:
                    getCommoditiesByCategory(jsonParser);
                    break;
                case Baloot.GET_BUY_LIST:
                    getBuyList(jsonParser);
                    break;
            }
        }
        catch (ParseException e) {
            System.out.println("hehe");
        }
    }

    private static void addUser(JSONObject jsonParser)
    {

        String name = (String)jsonParser.get("username");
        String password = (String)jsonParser.get("password");
        String email = (String)jsonParser.get("email");
        String birthDate = (String)jsonParser.get("birthDate");
        String address = (String)jsonParser.get("address");
        double credit = ((Number)jsonParser.get("credit")).doubleValue();


        //System.out.println("hihi");
        if(balootDatabase.doesExist(name)) {
            balootDatabase.updateUser(name, password, email, birthDate, address, credit);
            printOutput(true, "User "+name+" updated");
        }
        else{
            balootDatabase.addUser(name, password, email, birthDate, address, credit);
            printOutput(true, "User "+name+" added");
        }
    }

    private static void addProvider(JSONObject jsonParser) {

        int id = ((Number) jsonParser.get("id")).intValue();
        String name = (String)jsonParser.get("name");
        String date = (String) jsonParser.get("registryDate");
        balootDatabase.addProvider(id, name, date);
        printOutput(true, "Provider " + name + " added");
    }

    private static void addCommodity(JSONObject jsonParser) {

        int id = ((Number) jsonParser.get("id")).intValue();
        String name = (String)jsonParser.get("name");
        int providerId = ((Number) jsonParser.get("providerId")).intValue();
        double price = ((Number) jsonParser.get("price")).doubleValue();

        String categories = (String)jsonParser.get("categories");
        double rating = ((Number) jsonParser.get("rating")).doubleValue();
        int inStock = ((Number) jsonParser.get("inStock")).intValue();
       // balootDatabase.addCommodity(id, name, providerId,price,categories, rating,inStock);
        printOutput(true, "Commodity added");
    }

    private static void getCommoditiesList() {

     //   String data = balootDatabase.getCommoditiesList();
       // printOutput(true, data);
    }

    private static void rateCommodity(JSONObject jsonParser) {


        String username = (String)jsonParser.get("username");
        int commodityId = ((Number) jsonParser.get("commodityId")).intValue();
        double score = ((Number) jsonParser.get("score")).doubleValue();
       // todo
    }

    private static void addToBuyList(JSONObject jsonParser) {


        String username = (String)jsonParser.get("username");
        int commodityId = ((Number) jsonParser.get("commodityId")).intValue();

        // todo
    }

    private static void removeFromBuyList(JSONObject jsonParser) {


        String username = (String)jsonParser.get("username");
        int commodityId = ((Number) jsonParser.get("commodityId")).intValue();

        // todo
    }

    private static void getCommodityById(JSONObject jsonParser) {
        int commodityId = ((Number) jsonParser.get("id")).intValue();

        // todo
    }

    private static void getCommoditiesByCategory(JSONObject jsonParser) {
        String category = (String)jsonParser.get("category");

        // todo
    }

    private static void getBuyList(JSONObject jsonParser) {
        String username = (String)jsonParser.get("username");

        // todo
    }

    public static void main(String[] args) {
        run();
    }

}
