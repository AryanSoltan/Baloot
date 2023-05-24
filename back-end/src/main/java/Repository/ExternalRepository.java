package Repository;
import Baloot.User;
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
import java.util.List;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExternalRepository {
    private final EntityManagerFactory entityManagerFactory;

    String serverAddress;
    public ExternalRepository(String inputServerAddress) throws ParseException {
        serverAddress = inputServerAddress;
        var registry = new StandardServiceRegistryBuilder().configure().build();

        entityManagerFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        try {
           importUsers();

        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    public void importUsers(){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String pageUsers = getRequest("/api/users");
            Gson gsonParser = new GsonBuilder().create();
            List<User> users = gsonParser.fromJson(pageUsers,
                    new TypeToken<List<User>>() {
                    }.getType());
            for (User user : users) {
                System.out.println("nameeeeeeeeeeeeeeeeee is "+ user.getName());
                // balootServer.addUser(user);
                addUser(user.getName(), user.getPassword(), user.getEmail(), user.getBirthDate(), user.getAddress(), user.getCredit());
            }
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void addUser(String username, String password, String email,String birthDate, String address, double credit) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            System.out.println("name is "+ username);
            User user = new User(username, password, email, birthDate, address, credit);
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


