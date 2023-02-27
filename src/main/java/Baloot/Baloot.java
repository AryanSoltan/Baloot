package Baloot;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.Scanner;

public class Baloot {

    static final int TYPE_COMMAND_INDEX = 0;
    static final int JSON_INDEX = 1;
    static final String ADD_USER_COMMAND = "addUser";

    static final String ADD_PROVIDER_COMMAND = "addProvider";

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
            parseCommand(command_parsed[Baloot.TYPE_COMMAND_INDEX],
                    command_parsed[Baloot.JSON_INDEX]);
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
            }
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addUser(JSONObject jsonParser)
    {

        String name = (String)jsonParser.get("username");
        String password = (String)jsonParser.get("password");
        String email = (String)jsonParser.get("email");
        String birthDate = (String)jsonParser.get("birthDate");
        String address = (String)jsonParser.get("address");
        double credit = (double)jsonParser.get("credit");

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

        int id = (int) jsonParser.get("id");
        String name = (String)jsonParser.get("name");
        String date = (String) jsonParser.get("registryDate");
        balootDatabase.addProvider(id, name, date);
        printOutput(true, "Provider " + name + " added");
    }
    public static void main(String[] args) {
        run();
    }

}
