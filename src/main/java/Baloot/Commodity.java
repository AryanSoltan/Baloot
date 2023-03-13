package Baloot;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Commodity {

    private int id;
    private String name;
    private int providerId;

    private String providerName;
    private double price;
    private ArrayList<String> categories;
    private Map<String, Integer> userRatings;
    double rating;
    int inStock;
    public Commodity(int inputId, String inputName, int inputProviderId, double inputPrice,
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

    public void setProviderName(String name){
        providerName=name;
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

    public JSONObject getJsonData(){
        JSONObject jObj = new JSONObject();
        jObj.put("id",id);
        jObj.put("name",name);
        jObj.put("provider",providerName);
        jObj.put("providerId",providerId);
        jObj.put("price",price);
        jObj.put("categories",categories);
        jObj.put("rating",rating);
        return jObj;
    }



    public boolean isAvailable()
    {
        return inStock != 0;
    }

    public void buyOne()
    {
        inStock -= 1;
    }

    public void setProvider(String inputProviderName) {
        providerName = inputProviderName;
    }

    public boolean hasCategory(String category) {
        return categories.contains(category);
    }

    public void setUserRatingsEmpty() {
        userRatings = new HashMap<>();
    }

    public int getProviderId() {
        return providerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getCategories()
    {
        return categories;
    }

    public int getInStock()
    {
        return inStock;
    }

    public String getStringCategories() {
        String result = "";
        boolean isComma = false;
        for(String category: categories)
        {
            if(isComma)
                result += ", ";
            result += category;
            isComma = true;
        }
        return result;
    }
}
