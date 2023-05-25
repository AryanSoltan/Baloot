package Baloot;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Provider")
public class Provider {

    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String registryDate;

    //todo add this to image field of other objs
    @Column(columnDefinition = "text")
    String image;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "providerId")
    private Set<Commodity> commoditySet = new HashSet<>();;


    public Provider(int inputId, String inputName, String inputRegistryDate, String imageURL)
    {
        id = inputId;
        name = inputName;
        registryDate = inputRegistryDate;
        image=imageURL;
    }

    public Provider(Provider provider) {
        this(provider.id, provider.name, provider.registryDate, provider.image);
    }

    public Provider() {

    }

//    public void addCommodity(Commodity newCommodity)
//    {
//        commodities.add(newCommodity);
//    }
//
//    public String getProviderName() {
//        return name;
//    }
//
    public int getId() {
        return id;
    }
//
//    public String getRegistryDate() {
//        return registryDate;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public ArrayList<Commodity> getCommodities() {
//        return commodities;
//    }
//
//    public void setCommoditiesEmpty() {
//        commodities = new ArrayList<Commodity>();
//    }
}
