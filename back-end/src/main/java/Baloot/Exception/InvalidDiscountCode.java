package Baloot.Exception;

public class InvalidDiscountCode extends Exception{
    public InvalidDiscountCode(String code) {
        super("Error!: Discount code "+code +" is not correct.");
    }
}
