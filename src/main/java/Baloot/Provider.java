package Baloot;

import java.util.ArrayList;

public class Provider {

    private int id;
    private String name;
    private String registryDate;

    private ArrayList<Commodity> commodities;
    public Provider(int inputId, String inputName, String inputRegistryDate)
    {
        id = inputId;
        name = inputName;
        registryDate = inputRegistryDate;
    }

    public void addCommodity(Commodity newCommodity)
    {
        commodities.add(newCommodity);
    }

    public String getProviderName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    public void setCommoditiesEmpty() {
        commodities = new ArrayList<Commodity>();
    }
}
