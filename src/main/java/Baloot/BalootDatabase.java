package Baloot;

import Baloot.Exception.UserAlreadyExistError;

import java.net.UnknownServiceException;
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
        User newUser = new User(name, password, email, birthDate, address, credit);
        users.put(name, newUser);
    }

    public boolean doesExist(String userName)
    {
        return users.containsKey(userName);
    }


}
