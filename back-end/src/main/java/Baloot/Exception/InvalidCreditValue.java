package Baloot.Exception;

public class InvalidCreditValue extends Exception{
    public InvalidCreditValue() {
        super("Error!: credit should be a positive number");
    }
}