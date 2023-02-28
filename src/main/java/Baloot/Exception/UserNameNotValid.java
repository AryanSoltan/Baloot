package Baloot.Exception;

public class UserNameNotValid extends Exception{
    public UserNameNotValid(String username) {
        super("Error!: username " + username + " contatins invalid characters");
    }
}
