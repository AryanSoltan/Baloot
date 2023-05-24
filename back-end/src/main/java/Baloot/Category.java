package Baloot;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long categoryId;

    String categoryName;
    public Category(String categoryName)
    {
        this.categoryName = categoryName;
    }
}
