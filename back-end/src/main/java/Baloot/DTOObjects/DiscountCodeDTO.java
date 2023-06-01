package Baloot.DTOObjects;

import Baloot.DiscountCode;

public class DiscountCodeDTO {

    String discountCode;
    int discount;

    public String getCode() {return discountCode; }

    public int getPercentage(){return discount;}

    public DiscountCodeDTO(DiscountCode inputdiscountCode){
        discountCode = inputdiscountCode.getCode();
        discount =inputdiscountCode.getPercentage();

    }

}
