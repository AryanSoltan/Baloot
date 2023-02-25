package Baloot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.util.Scanner;

public class CommandParser {

    CommandParser()
    {
    }

    private void run()
    {
        Scanner inputReader = new Scanner(System.in);
        while(true)
        {
            String name = inputReader.nextLine();
            System.out.println(name);
        }
    }

    public static void main(String[] args) {
        CommandParser commandParser = new CommandParser();
        commandParser.run();
    }

}
