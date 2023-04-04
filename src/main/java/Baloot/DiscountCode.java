package Baloot;

public class DiscountCode {
    String discountCode;
    int discount;

    public String getCode() {return discountCode; }

    public int getPercentage(){return discount;}

    public DiscountCode(String inputCode, int inputPercentage){
        discountCode = inputCode;
        discount = inputPercentage;

    }
}
