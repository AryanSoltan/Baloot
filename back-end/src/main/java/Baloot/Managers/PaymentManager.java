package Baloot.Managers;

import Baloot.DiscountCode;
import Baloot.Exception.InvalidDiscountCode;
import java.util.HashMap;
import java.util.Map;

public class PaymentManager {


    static Map<String, DiscountCode> discountCodesList;

    public PaymentManager()
    {
        discountCodesList = new HashMap<String,DiscountCode>();
    }

    public void addNewDiscountCode(DiscountCode discountCode)
    {
        discountCodesList.put(discountCode.getCode(),discountCode);
    }

    public boolean discountCodeIsValid(String code)
    {
        if(discountCodesList.containsKey(code))
            return true;
        return false;
    }

    public DiscountCode getDiscountCode(String code) throws Exception
    {
        if(!discountCodesList.containsKey(code))
        {
            throw new InvalidDiscountCode(code);
        }
        return discountCodesList.get(code);
    }
}
