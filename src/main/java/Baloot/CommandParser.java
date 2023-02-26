package Baloot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.*;
import org.json.simple.parser.*;

import java.util.Scanner;

public class CommandParser {

    static final int TYPE_COMMAND_INDEX = 0;
    static final int JSON_INDEX = 1;
    static final String ADD_USER_COMMAND = "addUser";

    private BalootDatabase baloot_data_base;
    CommandParser()
    {

    }

    private void run()
    {
        Scanner inputReader = new Scanner(System.in);
        while(true)
        {
            String new_command = inputReader.nextLine();
            String[] command_parsed = new_command.split(" ");
            parse_command(command_parsed[CommandParser.TYPE_COMMAND_INDEX],
                    command_parsed[CommandParser.JSON_INDEX]);
        }
    }

    private static void parse_command(String command_input, String json_input)
    {
        JSONObject json_command = new JSONObject(json_input);
        switch(command_input)
        {
            case CommandParser.ADD_USER_COMMAND:
                add_user(json_input);
        }
    }

    public static void add_user(String json_input)
    {

    }
    public static void main(String[] args) {
        CommandParser commandParser = new CommandParser();
        commandParser.run();
    }

}
