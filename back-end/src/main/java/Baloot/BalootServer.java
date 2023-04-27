package Baloot;

import Baloot.Exception.*;
import Baloot.Managers.CommodityManager;
import Baloot.Managers.PaymentManager;
import Baloot.Managers.ProviderManager;
import Baloot.Managers.UserManager;
import java.util.ArrayList;

public class BalootServer {

    private UserManager userManager;
    private ProviderManager providerManager;
    private CommodityManager commodityManager;
    private PaymentManager paymentManager;
    static int commentIdNow;
    private static BalootServer instance = null;

    public BalootServer()
    {
        commentIdNow = 0;
        userManager = new UserManager();
        providerManager = new ProviderManager();
        commodityManager = new CommodityManager();
        paymentManager = new PaymentManager();
    }


    public static BalootServer getInstance()
    {
        if(instance == null)
            instance = new BalootServer();
        return instance;

    }

    public int getNumofUsers() throws Exception
    {
        return userManager.getNumOfUsers();
    }

    public void addUser(User newUser) throws Exception
    {
        String name = newUser.getName();
        if(userManager.doesExist(name))
            userManager.updateUser(name, newUser);
        else
            userManager.addNewUser(newUser);
    }

    public void addProvider(Provider newProvider)
    {
        providerManager.addNewProvider(newProvider);
    }

    public void addCommidity(Commodity newCommidity) throws Exception
    {
        int providerId = newCommidity.getProviderId();
        Provider provider = findProvider(providerId);
        commodityManager.addNewCommodity(newCommidity , provider.getProviderName());
        Commodity neededCommodity = commodityManager.getCommodityByID(newCommidity.getId());
        providerManager.addNewCommodity(neededCommodity,  provider);
    }

    public void addDiscountCode(DiscountCode discountCode)
    {
        paymentManager.addNewDiscountCode(discountCode);
    }

    public Provider findProvider(int providerId) throws Exception {
        return providerManager.getProviderByID(providerId);
    }

    public User findUser(String username) throws Exception {
        return userManager.getUserByUsername(username);
    }

    public Commodity getCommodityById(int commodityId) throws Exception {
        return commodityManager.getCommodityByID(commodityId);
    }

    public ArrayList<Commodity> getCommodityList()
    {
        return commodityManager.getAllCommodities();
    }

    public void rateCommodity(String username, int commodityId, String scoreStr) throws Exception
    {
        if(!scoreStr.matches("-?(0|[1-9]\\d*)"))
            throw new InvalidRating();
        int score = Integer.parseInt(scoreStr);
        if(score < 1 || score > 10)
            throw new InvalidRating();
        if( !userManager.doesExist( username))
            throw new UserNotExist(username);
        commodityManager.rateCommodity(username, commodityId, score);
    }

    public void addCredit(String username, String credit) throws Exception
    {
        if(!credit.matches("-?(0|[1-9]\\d*)"))
            throw new InvalidCreditValue();
        double creditVal = Double.parseDouble(credit);
        if(creditVal < 1)
            throw new InvalidCreditValue();
        userManager.addCredit(username,creditVal);
    }

    public void addCommidityToUserBuyList(String username, int commodityId) throws Exception { //done
        Commodity neededCommodity = getCommodityById(commodityId);
        if(!neededCommodity.isAvailable())
            throw new CommodityOutOfStock(commodityId);
        if(userManager.userHasBoughtCommodity(username,commodityId))
            throw new CommodityAlreadyAdded(commodityId);
        userManager.addCommidityToUserBuyList(username,neededCommodity);
    }

    public void removeFromBuyList(String username, int commodityId) throws Exception { //done
        userManager.removeCommodityFromBuyList(username,commodityId);
    }

    public ArrayList<Commodity> getCommoditiesByCategory(String category) { //done
       return commodityManager.getCommoditiesByCategory(category);
    }

    public BuyList getUserBuyList(String userName) throws Exception { //done
        return userManager.getUserBuyList(userName);
    }


    public ArrayList<Commodity> getCommodityRangePrice(double startPrice, double endPrice) { //done
        return commodityManager.getCommodityByRangePrice(startPrice,endPrice);
    }

    public void addComment(Comment comment) throws Exception { //done
        User user = userManager.getUserByUseremail(comment.getUserEmail());
        commentIdNow += 1;
        commodityManager.addCommentToCommodity(comment, commentIdNow, user.getName());
    }

    public void addRatingToComment(int commentId, String userName, int rate) throws Exception { //done
        User user = findUser(userName);
        commodityManager.rateCommoditiesComment(commentId , user, rate);
    }

    public void handlePaymentUser(String userName) throws Exception //done
    {
        User user = findUser(userName);
        BuyList userBuyList = user.getBuyList();
        commodityManager.checkIfAllCommoditiesAreAvailabel(userBuyList);
        double totalPrice = userBuyList.getBuylistPrice();
        if(user.getCredit() < totalPrice)
            throw new NotEnoughCredit();

        commodityManager.decreaseStock(userBuyList);
        userManager.userBoughtBuyList(user,totalPrice);
    }

    public ArrayList<Commodity> getSuggestedCommodities(int commodityID) throws Exception
    {
        Commodity targetCommodity = getCommodityById(commodityID);
        return CommodityManager.getMostSimilarCommodities(targetCommodity,3);
    }

    public void logIn(String username, String password) throws Exception
    {
        userManager.login(username,password);
    }
    public void logOut()
    {
        userManager.logOut();
    }

    public User getLoggedInUser()
    {
        return userManager.getLoggedInUser();
    }

    public void setSearchFilters(String searchContent, String filterType)
    {
        commodityManager.setFilterContent(searchContent);
        commodityManager.setSearchFilter();
        commodityManager.setFilterType(filterType);
    }

    public void clearFilters()
    {
        commodityManager.clearSearchFilter();
    }

    public void setSortFilters(String sortType)
    {
        commodityManager.setSortBy(sortType);
        commodityManager.setSortFilter();
    }

    public ArrayList<Commodity> getFilteredCommodities()
    {
        return commodityManager.getFilteredCommodities();
    }

    public void applyDiscountCode(String username, String code) throws Exception
    {
        if(!paymentManager.discountCodeIsValid(code))
            throw new InvalidDiscountCode(code);
        DiscountCode discountCode = paymentManager.getDiscountCode(code);
        User user = findUser(username);
        userManager.addDiscountCodeToUserBuyList(user, discountCode);
    }
}
