package Repository;
import Baloot.*;
import Baloot.Exception.CommodityNotExist;
import Baloot.Exception.ProviderNotExist;
import Baloot.Exception.UserNotExist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ExternalRepository {
    private final EntityManagerFactory entityManagerFactory;

    String serverAddress;
    private BalootServerRepo userRepo;

    public ExternalRepository(String inputServerAddress) throws ParseException {
        serverAddress = inputServerAddress;
        var registry = new StandardServiceRegistryBuilder().configure().build();

        entityManagerFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        try {
         importUsers();
         importProviders();

         importCommodities();
         importDiscountCodes();
         importComments();

        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void importUsers(){
        try {
            String pageUsers = getRequest("/api/users");
            Gson gsonParser = new GsonBuilder().create();
            List<User> users = gsonParser.fromJson(pageUsers, new TypeToken<List<User>>() {}.getType());
            for (User user : users) {
                addUser(user.getName(), user.getPassword(), user.getEmail(), user.getBirthDate(), user.getAddress(), user.getCredit());
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void importProviders(){
        try {

            String pageUsers = getRequest("/api/v2/providers");
            Gson gsonParser = new GsonBuilder().create();
            List<Provider> providers = gsonParser.fromJson(pageUsers, new TypeToken<List<Provider>>() {}.getType());

            for (Provider provider : providers) {

                EntityManager entityManager = entityManagerFactory.createEntityManager();
                entityManager.getTransaction().begin();
                try {

                    entityManager.persist(provider);
                } catch (Exception e) {
                    entityManager.getTransaction().rollback();
                    throw new Exception(e.getMessage());
                }
                entityManager.getTransaction().commit();
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void importComments(){
        try {

            String pageUsers = getRequest("/api/comments");
            Gson gsonParser = new GsonBuilder().create();
            List<Comment> comments = gsonParser.fromJson(pageUsers, new TypeToken<List<Comment>>() {}.getType());

            for (Comment comment : comments) {

                EntityManager entityManager = entityManagerFactory.createEntityManager();
                entityManager.getTransaction().begin();

                User user = getUserByEmail(comment.getUserEmail(), entityManager);

                if (user == null) {
                    entityManager.getTransaction().rollback();
                    throw new UserNotExist(comment.getUserEmail());
                }
                Commodity commodity = entityManager.find(Commodity.class, comment.getCommodityId());
                if (commodity == null) {
                    entityManager.getTransaction().rollback();
                    throw new CommodityNotExist(comment.getCommodityId());
                }

                try {
                    Comment com = new Comment(user,  commodity, comment.getText(), comment.getDate());
                    entityManager.persist(com);
                } catch (Exception e) {
                    entityManager.getTransaction().rollback();
                    throw new Exception(e.getMessage());
                }
                entityManager.getTransaction().commit();
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private User getUserByEmail(String userEmail,EntityManager entityManager) throws Exception{
        List users = entityManager.createQuery("SELECT u FROM User u where u.email=:userEmail")
                .setParameter("userEmail", userEmail).getResultList();
        if (users.isEmpty()) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new Exception();
        }
        return (User) users.get(0);
    }

    public void importDiscountCodes(){
        try {

            String pageUsers = getRequest("/api/discount");
            Gson gsonParser = new GsonBuilder().create();
            List<DiscountCode> codes = gsonParser.fromJson(pageUsers, new TypeToken<List<DiscountCode>>() {}.getType());

            for (DiscountCode code : codes) {

                EntityManager entityManager = entityManagerFactory.createEntityManager();
                entityManager.getTransaction().begin();
                try {

                    entityManager.persist(code);
                } catch (Exception e) {
                    entityManager.getTransaction().rollback();
                    throw new Exception(e.getMessage());
                }
                entityManager.getTransaction().commit();
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void importCommodities(){
        try {

            String pageUsers = getRequest("/api/v2/commodities");
            Gson gsonParser = new GsonBuilder().create();
            List<Commodity> commodities = gsonParser.fromJson(pageUsers, new TypeToken<List<Commodity>>() {}.getType());

            for (Commodity commodity : commodities) {
                addCommodity(commodity.getId(),commodity.getName(), commodity.getProviderId(),commodity.getPrice(), commodity.getRating(), commodity.getInStock(), commodity.getImage(),commodity.getCategoriesNames());
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void addCommodity(int id, String name, int providerID, double price,double rating , int inStock, String imgURL, ArrayList<String> categories) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"+providerID);
        Provider provider = entityManager.find(Provider.class, providerID);
        if (provider == null) {
            entityManager.getTransaction().rollback();
            throw new ProviderNotExist(providerID);
        }

        try {

            Commodity commodity = new Commodity(id, name, provider, price, rating, inStock, imgURL);

            for (var categoryName : categories) {

                Category cat = entityManager.find(Category.class, categoryName);
                if(cat == null)
                {
                    cat = new Category(categoryName);
                    entityManager.persist(cat);
                }
                cat.getCommoditySet().add(commodity);
                commodity.getCategories().add(cat);
            }
            entityManager.persist(commodity);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new Exception(e.getMessage());
        }
        entityManager.getTransaction().commit();
    }

    public void addUser(String username, String password, String email,String birthDate, String address, double credit) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {

            User user = new User(username, password, email, birthDate, address, credit);
            entityManager.persist(user.getPurchased());
            entityManager.persist(user.getBought());
            entityManager.persist(user);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new Exception(e.getMessage());
        }
        entityManager.getTransaction().commit();
    }
    public String getRequest(String inputUrl)
    {
        String result = null;
        try {
            URL url = new URL(serverAddress + inputUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }





}


