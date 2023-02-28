package Baloot;

import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Scanner;

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

    private static void parseCommand(String commandInput, String jsonInput)
    {

        JSONParser jsonCommand = new JSONParser();
        Gson gsonParser = new GsonBuilder().create();
        try {

            JSONObject jsonParser = (JSONObject)jsonCommand.parse(jsonInput);

            switch(commandInput)
            {
                case Baloot.ADD_USER_COMMAND:
                    addUser(jsonParser, jsonInput, gsonParser);
                    break;

                case Baloot.ADD_PROVIDER_COMMAND:
                    addProvider(jsonParser, jsonInput, gsonParser);
                    break;
                case Baloot.ADD_COMMODITY:
                    addCommodity(jsonParser, jsonInput, gsonParser);
                    break;
                case Baloot.GET_COMMODITIES_LIST:
                    getCommoditiesList();
                    break;
                case Baloot.RATE_COMMODITY:
                    rateCommodity(jsonParser, jsonInput, gsonParser);
                    break;
                case Baloot.ADD_TO_BUYLIST:
                    addToBuyList(jsonParser, jsonInput, gsonParser);
                    break;
                case Baloot.REMOVE_FROM_BUYLIST:
                    removeFromBuyList(jsonParser, jsonInput, gsonParser);
                    break;
                case Baloot.GET_COMMODITY_BY_ID:
                    getCommodityById(jsonParser, jsonInput, gsonParser);
                    break;
                case Baloot.GET_COMMODITIES_BY_Category:
                    getCommoditiesByCategory(jsonParser, jsonInput, gsonParser);
                    break;
                case Baloot.GET_BUY_LIST:
                    getBuyList(jsonParser, jsonInput, gsonParser);
                    break;
            }
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addUser(JSONObject jsonParser, String jsonInp, Gson gsonParser)
    {
        User user = gsonParser.fromJson(jsonInp, User.class);
        String name = (String)jsonParser.get("username");

        if(balootDatabase.doesExist(name)) {
            balootDatabase.updateUser(name, user);
            printOutput(true, "User "+name+" updated");
        }
        else{
            balootDatabase.addUser(name, user);
            printOutput(true, "User "+name+" added");
        }
    }

    private static void addProvider(JSONObject jsonParser, String jsonInp, Gson gsonParser) {
        Provider newProvider = gsonParser.fromJson(jsonInp, Provider.class);
        String name = (String)jsonParser.get("name");
        int id = ((Number) jsonParser.get("id")).intValue();
        balootDatabase.addProvider(id, newProvider);
        printOutput(true, "Provider " + name + " added");
    }

    private static void addCommodity(JSONObject jsonParser, String jsonInp, Gson gsonParser) {

        int providerId = ((Number)jsonParser.get("providerId")).intValue();
        if(!balootDatabase.checkExistProvider(providerId))
        {
            printOutput(false, "Not exist provider");
        }
        else {
            int id = ((Number) jsonParser.get("id")).intValue();
            Commodity offering = gsonParser.fromJson(jsonInp, Commodity.class);
            balootDatabase.addCommidity(id, offering);
            printOutput(true, "Commodity " + id + "added");
        }
    }

    private static void getCommoditiesList() {

        ArrayList<Commodity> commodities = balootDatabase.getCommodityList();
        Gson gson = new Gson();
        String commoditiesInJson = gson.toJson(commodities);
        printOutput(true, commoditiesInJson);
    }

    private static void rateCommodity(JSONObject jsonParser, String jsonInp, Gson gsonParser) {


        String username = (String)jsonParser.get("username");
        int commodityId = ((Number) jsonParser.get("commodityId")).intValue();
        double score = ((Number) jsonParser.get("score")).doubleValue();
       // todo
    }

    private static void addToBuyList(JSONObject jsonParser, String jsonInp, Gson gsonParser) {


        String username = (String)jsonParser.get("username");
        int commodityId = ((Number) jsonParser.get("commodityId")).intValue();

        // todo
    }

    private static void removeFromBuyList(JSONObject jsonParser, String jsonInp, Gson gsonParser) {


        String username = (String)jsonParser.get("username");
        int commodityId = ((Number) jsonParser.get("commodityId")).intValue();

        // todo
    }

    private static void getCommodityById(JSONObject jsonParser, String jsonInp, Gson gsonParser) {
        int commodityId = ((Number) jsonParser.get("id")).intValue();

        // todo
    }

    private static void getCommoditiesByCategory(JSONObject jsonParser, String jsonInp, Gson gsonParser) {
        String category = (String)jsonParser.get("category");

        // todo
    }

    private static void getBuyList(JSONObject jsonParser, String jsonInp, Gson gsonParser) {
        String username = (String)jsonParser.get("username");

        // todo
    }

    public static void main(String[] args) {
        run();
    }

}
