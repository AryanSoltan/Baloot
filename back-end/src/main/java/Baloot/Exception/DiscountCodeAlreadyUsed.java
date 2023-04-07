package Baloot.Exception;

public class DiscountCodeAlreadyUsed extends Exception {
    public DiscountCodeAlreadyUsed(String code){super("Error!: You have already used " + code+ " discount code.");}

}
