package Baloot.Managers;

import Baloot.Commodity;
import Baloot.DiscountCode;
import Baloot.Exception.InvalidDiscountCode;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

//    public boolean discountCodeIsValid(String code, EntityManager entityManager)
//    {
//        var codes = entityManager.createQuery("select d FROM DiscountCode d WHERE d.discountCode =: code").setParameter("code", code).getResultList();
//        if (codes.isEmpty())
//            return false;
//        else
//            return true;
//
//    }

    public DiscountCode getDiscountCode(String code,EntityManager entityManager) throws Exception
    {
        System.out.println("heree");
        var codes = entityManager.createQuery("select d FROM DiscountCode d WHERE d.discountCode =: code").setParameter("code", code).getResultList();
        System.out.println("heree"+codes);
        System.out.println("heree"+(DiscountCode) codes.get(0));
        if (codes.isEmpty())
            throw new InvalidDiscountCode(code);
        else
            return (DiscountCode) codes.get(0);

    }
}
