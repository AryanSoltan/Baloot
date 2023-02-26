package Baloot;

import java.util.HashMap;
import java.util.Map;

public class BalootDatabase {

    Map<String, User> users;
    BalootDatabase()
    {
        users = new HashMap<String, User>();
    }

    public void addUser(String name, String password, String email,
                         String birthDate, String address, double credit)
    {
        if(!users.containsKey(name))
        {
            User newUser = new User(name, password, email, birthDate, address, credit);
            users.put(name, newUser);
        }

        //bad scenario must write
    }

}
