package Baloot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Provider")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long providerId;
    private int id;
    private String name;
    private String registryDate;

    String image;

    @OneToMany(mappedBy = "commodityId")
    private List<Commodity> commodities = new ArrayList<>();
    public Provider(int inputId, String inputName, String inputRegistryDate, String imageURL)
    {
        id = inputId;
        name = inputName;
        registryDate = inputRegistryDate;
        image=imageURL;
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

    public String getImage() {
        return image;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    public void setCommoditiesEmpty() {
        commodities = new ArrayList<Commodity>();
    }
}
