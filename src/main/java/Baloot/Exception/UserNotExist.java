package Baloot.Exception;

public class UserNotExist extends Exception {
    public UserNotExist(String username) {
        super("Error!: username " + username + "doesn't exist");
    }
}
