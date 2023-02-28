package Baloot.Exception;

public class InvalidRating extends Exception{
    public InvalidRating() {
        super("Error!: Rating should be a natural number between 1 to 10");
    }
}
