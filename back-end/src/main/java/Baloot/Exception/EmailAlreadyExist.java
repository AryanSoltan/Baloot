package Baloot.Exception;

public class EmailAlreadyExist extends Exception{

    public EmailAlreadyExist(String email) {
        super("Error!: User with email " + email + " already exist");
    }
}
