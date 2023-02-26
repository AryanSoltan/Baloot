package Baloot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.Scanner;

public class CommandParser {

    static final int TYPE_COMMAND_INDEX = 0;
    static final int JSON_INDEX = 1;
    static final String ADD_USER_COMMAND = "addUser";
    static final int MAX_PARSE_COMMAND = 2;

    private BalootDatabase balootDatabase;
    CommandParser()
    {

    }

    private void run()
    {
        Scanner inputReader = new Scanner(System.in);
        while(true)
        {
            String new_command = inputReader.nextLine();
            String[] command_parsed = new_command.split(" ", MAX_PARSE_COMMAND);
            parse_command(command_parsed[CommandParser.TYPE_COMMAND_INDEX],
                    command_parsed[CommandParser.JSON_INDEX]);
        }
    }

    private static void parse_command(String command_input, String json_input)
    {
        JSONParser jsonCommand = new JSONParser();
        try {
            JSONObject jsonParser = (JSONObject)jsonCommand.parse(json_input);
            switch(command_input)
            {
                case CommandParser.ADD_USER_COMMAND:
                    add_user(jsonParser);
            }
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void add_user(JSONObject jsonParser)
    {
        String name = (String)jsonParser.get("username");
        String password = (String)jsonParser.get("password");
        String email = (String)jsonParser.get("email");
        String birthDate = (String)jsonParser.get("birthDate");
        String address = (String)jsonParser.get("address");
        String credit = (String)jsonParser.get("credit");
        BalootDatabase.addUser(name, password, email, birthDate, address, credit);
    }
    public static void main(String[] args) {
        CommandParser commandParser = new CommandParser();
        commandParser.run();
    }

}
