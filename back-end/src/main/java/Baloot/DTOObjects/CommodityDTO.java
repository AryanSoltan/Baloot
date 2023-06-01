package Baloot.DTOObjects;

import Baloot.Comment;
import Baloot.Commodity;

import Baloot.Category;

import java.util.List;

public class CommodityDTO {
    private long id;
    private String name;
    private long providerId;
    private double price;
    private List<String> categories;

    private List<CommentDTO> comments;
    private float rating;
    private long inStock;

    private long countInBuylist;
   // final private long inCart;
    final private long rateCount;
    final private String providerName;

    private String image;

    public CommodityDTO(Commodity commodity) {
        this.id = commodity.getId();
        this.name = commodity.getName();
        this.providerId = commodity.getProvider().getId();
        this.price = commodity.getPrice();
        this.setCategories(commodity.getCategories().stream().map(Category::getName).toList());
        this.inStock = commodity.getInStock();
        this.rateCount = commodity.getRatings().size();
        commodity.getRatings().forEach(r -> this.rating += r.getRate() / rateCount);
        this.providerName = commodity.getProvider().getName();
        this.image = commodity.getImage();
    }

    public CommodityDTO(Commodity commodity, long count) {
        this.id = commodity.getId();
        this.name = commodity.getName();
        this.providerId = commodity.getProvider().getId();
        this.price = commodity.getPrice();
        this.setCategories(commodity.getCategories().stream().map(Category::getName).toList());
        this.inStock = commodity.getInStock();
        this.countInBuylist=count;
        this.rateCount = commodity.getRatings().size();
        commodity.getRatings().forEach(r -> this.rating += r.getRate() / rateCount);
        this.providerName = commodity.getProvider().getName();
        this.image = commodity.getImage();
        this.countInBuylist = count;

    }

    public void setComments(List<CommentDTO> commentList){comments=commentList;}

    public List<CommentDTO> getComments(){return comments;}
    public long getId() {
        return id;
    }

    public long getcountInBuylist(){return countInBuylist;}

    public long getCommodityId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getCommodityName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getImage() {
        return image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getInStock() {
        return inStock;
    }

    public void setInStock(long inStock) {
        this.inStock = inStock;
    }

    public long getRateCount() {
        return rateCount;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setCountInBuylist(long count){countInBuylist = count;}
}
