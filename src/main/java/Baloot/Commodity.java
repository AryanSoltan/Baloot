package Baloot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Commodity {

    private int id;
    private String name;
    private int providerId;
    private double price;
    ArrayList<String> categories;
    Map<String, Integer> userRatings;
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

    public void addRating(String username, int newRating)
    {
        double sumBefore;
        if(userRatings.size() != 0)
            sumBefore = (rating * userRatings.size());
        else
            sumBefore = 0;
        userRatings.put(username, newRating);
        double newSum = sumBefore + newRating;
        rating = newSum / userRatings.size();
    }

    private void removeRating(String username)
    {
        double sumBefore = 0;
        sumBefore = (rating * userRatings.size());
        double newSum = sumBefore - userRatings.get(username);
        userRatings.remove(username);
        if(userRatings.size() != 0)
            rating = newSum / userRatings.size();
        else
            rating = 0;
    }

    public double getRating()
    {
        return rating;
    }

    public void updateRating(String username, int newRating)
    {
        removeRating(username);
        addRating(username, newRating);
    }

    public boolean hasRating(String userName)
    {
        return userRatings.containsKey(userName);
    }
}
