package Baloot;

import java.util.ArrayList;

public class Commodity {

    private int id;
    private String name;
    private int providerId;
    private double price;
    ArrayList<String> categories;
    double rating;
    int inStock;
    Commodity(int inputId, String inputName, int inputProviderId, double inputPrice,
              ArrayList<String> inputCategories, double inputRating, int inputInStock)
    {
        id = inputId;
        name = inputName;
        providerId = inputProviderId;
        price = inputPrice;
        categories = inputCategories;
        rating = inputRating;
        inStock = inputInStock;
    }


}
