package Baloot;

import com.beust.ah.A;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "Commodity")
public class Commodity {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @Column(name="name", nullable = false)
    private String name;

    @Column(insertable = false,updatable = false)
    private int providerId;

    @Column(name = "imageURL")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "providerId")
    private Provider provider;

    @Column(name="price",nullable = false)
    private double price;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(joinColumns = {@JoinColumn(name="id")}, inverseJoinColumns = {@JoinColumn(name="categoryId")})
    private Set<Category> categoriesSet = new HashSet<>();
    //
//
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "id")
    private Set<Rate> rates = new HashSet<>();


    @Column(name="rating")
    double rating;

    @Column(name="inStock", nullable = false)
    int inStock;

    @Transient
    private ArrayList<String> categories;

    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "id")
    Set<Comment> comments = new HashSet<>();
    public Commodity(int inputId, String inputName, int inputProviderId, double inputPrice,
                     ArrayList<String> inputCategories, double inputRating, int inputInStock, String imageURLAddress)
    {
        id = inputId;
        name = inputName;
        providerId = inputProviderId;
        price = inputPrice;

        rating = inputRating;
        inStock = inputInStock;
        image = imageURLAddress;

        categories= inputCategories;

    }

    public Commodity(int inputId, String inputName,Provider inputProvider, double inputPrice,
                     double inputRating, int inputInStock, String imageURLAddress)
    {
        id = inputId;
        name = inputName;
        provider = inputProvider;
        price = inputPrice;

        rating = inputRating;
        inStock = inputInStock;
        image = imageURLAddress;

    }

//    public Commodity(Commodity commodity) {
//        this(commodity.id,commodity.name, commodity.providerId, commodity.price, commodity.rating, commodity.inStock, commodity.image);
//    }

    public Commodity() {

    }



    //    public void setProviderName(String name){
//        providerName=name;
//    }
    public void addRating(String username, int newRating, EntityManager entityManager)
    {
        double sumBefore;
        if(rates.size() != 0)
            sumBefore = (rating * rates.size());
        else
            sumBefore = 0;
        Rate rate = new Rate(entityManager.find(User.class, username), this, newRating);
        entityManager.persist(rate);
        rates.add(rate);
        double newSum = sumBefore + newRating;
        rating = newSum / rates.size();
    }
    //
    private void removeRating(String username)
    {
        double sumBefore = 0;
        sumBefore = (rating * rates.size());
        double prev_rate = 0;
        for(Rate rate: rates)
        {
            if(Objects.equals(rate.getUser(), username)) {
                rates.remove(rate);
                prev_rate = rate.getRate();
                break;
            }
        }
        double newSum = sumBefore - prev_rate;
        if(rates.size() != 0)
            rating = newSum / rates.size();
        else
            rating = 0;
    }

    public double getRating()
    {
        return rating;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> ratings) {
        this.rates = ratings;
    }


    public Set<Rate> getRatings() {
        return rates;
    }

    public void updateRating(String username, int newRating, EntityManager entityManager)
    {
        removeRating(username);
        addRating(username, newRating, entityManager);
    }
    //
    public boolean hasRating(String userName)
    {
        for(Rate rating: rates)
        {
            if(Objects.equals(rating.getUser(), userName))
            {
                return true;
            }
        }
        return false;
    }

    public Provider getProvider() {
        return provider;
    }



    public boolean isAvailable()
    {
        return inStock != 0;
    }

    public void buyOne()
    {
        inStock -=1;
    }

    public void buy(int num)
    {
        inStock -= num;
    }

//    public void setProvider(String inputProviderName) {
//        providerName = inputProviderName;
//    }

    public boolean hasCategory(String categoryName) {
        for(Category category: categoriesSet)
        {
            if(Objects.equals(category.getName(), categoryName))
            {
                return true;
            }
        }
        return false;
    }
    //
    public boolean hasCategory(ArrayList<Category> listOfCategories)
    {
        for(Category cat : listOfCategories)
        {
            if(categoriesSet.contains(cat))
                return true;
        }
        return false;
    }

    public boolean nameContains(String searchName){
        return name.contains(searchName);
    }


//    public void setUserRatingsEmpty() {
//        userRatings = new HashMap<>();
//    }

    public int getProviderId() {
        return providerId;
    }

//    public String getProviderName() {
//        return providerName;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {return image;}

    public double getPrice() {
        return price;
    }

    public void decreaseStock(int outStock){inStock -= outStock;}

    public ArrayList<String> getCategoriesNames()
    {

        return categories;

    }

    public Set<Category> getCategories()
    {
        return categoriesSet;

    }


    public int getInStock()
    {
        return inStock;
    }

//    public String getImage(){return image;}

//    public String getStringCategories() {
//        String result = "";
//        boolean isComma = false;
//        for(String category: categories)
//        {
//            if(isComma)
//                result += ", ";
//            result += category;
//            isComma = true;
//        }
//        return result;
//    }

    //    public void setCommentsEmpty()
//    {
//        comments = new ArrayList<Comment>();
//    }
//
    public void addComment(Comment comment)
    {
        comments.add(comment);
        // comment.setUserName(name);
    }
//
//    public ArrayList<Comment> getComments()
//    {
//        return comments;
//    }
//
//    public boolean hasCommentId(int commentId) {
//        for(Comment comment: comments)
//        {
//            if(comment.getId() == commentId)
//                return true;
//        }
//        return false;
//    }
//
//
//    public void rateComment(int commentId, User user, int rate) {
//        for(Comment comment: comments)
//        {
//            if(comment.getId() == commentId)
//                comment.addRate(user, rate);
//        }
//    }
//
////    public int getUserRatingsSize()
////    {
////        return userRatings.size();
////    }
//
//    public Comment getComment(int commentId) {
//        for(Comment comment: comments)
//        {
//            if(comment.getId() == commentId)
//            {
//                return comment;
//            }
//        }
//        return null;
//    }
}