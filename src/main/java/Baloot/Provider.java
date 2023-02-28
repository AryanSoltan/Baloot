package Baloot;

import java.util.ArrayList;

public class Provider {

    private int id;
    private String name;
    private String registryDate;

    private ArrayList<Commodity> commodities;
    Provider(int inputId, String inputName, String inputRegistryDate)
    {
        id = inputId;
        name = inputName;
        registryDate = inputRegistryDate;
    }

    public void addCommodity(Commodity newCommodity)
    {
        commodities.add(newCommodity);
    }

    public String getName() {
        return name;
    }
}
