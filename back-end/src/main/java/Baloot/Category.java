package Baloot;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import java.util.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Category")
public class Category {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long categoryId;
    @Id
    String categoryName;

    @ManyToMany(mappedBy = "categoriesSet")
    private Set<Commodity> commoditySet = new HashSet<>();

    public Category(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Category() {
    }

    public Set<Commodity> getCommoditySet() {
        return commoditySet;
    }

    public String getName() {
        return categoryName;
    }
}
