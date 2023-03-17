import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Provider;
import Baloot.User;
import InterfaceServer.InterfaceServer;
import com.mashape.unirest.http.Unirest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class CheckViewBuyListTest
{
    BalootServer balootServer;
    InterfaceServer interfaceServer;
    @Before
    public void setup() throws Exception {
        balootServer = new BalootServer();
        interfaceServer = new InterfaceServer(
                8080, balootServer);
        balootServer.addUser(new User("user1",
                "1234","user1@gmail.com","1977-09-15","add1",1500));
        balootServer.addUser(new User("user2",
                "1234","user2@gmail.com","1979-09-15","add2",1500));
        balootServer.addProvider(new Provider(1,"provider1","2023-09-15"));
        balootServer.addCommidity(
                new Commodity(1,"Headphone",1,35000,new ArrayList<>(Arrays.asList("Technology","Phone")),0,50));
        balootServer.addCommidity(new Commodity(2,"Headphone",1,35000,
                new ArrayList<>(Arrays.asList("Technology","Phone")),1,0));
        balootServer.addCommidity(new Commodity(3,"Headphone",1,35000,
                new ArrayList<>(Arrays.asList("Technology","Phone")),1,1));

    }

    @After
    public void teardown() {

        balootServer=null;
        interfaceServer.closeConnection();
        interfaceServer = null;
    }

    @Test
    public void buyListEmptyTest() throws Exception {
        ArrayList<Commodity> buyListEmpty = balootServer.getUserBuyList("user1");
        assertEquals("Size should be zero", 0, buyListEmpty.size());
    }

    @Test
    public void buyListNotEmptyTest() throws Exception
    {
        balootServer.addCommidityToUserBuyList("user1", 1);
        ArrayList<Commodity> buyListNotEmpty = balootServer.getUserBuyList("user1");
        assertEquals("Size should be one", 1, buyListNotEmpty.size());
    }

    @Test
    public void buyListNotSideEffect() throws Exception
    {
        balootServer.addCommidityToUserBuyList("user1", 1);
        Commodity commodity = balootServer.getCommodityById(1);
        assertEquals("In stock changed after buy view", 50, commodity.getInStock());
    }

    @Test
    public void checkFrontEnd() throws Exception
    {

        Unirest.get("http://localhost:8080/addToBuyList/user1/1").asString();
        ArrayList<Commodity> buyListNotEmpty = balootServer.getUserBuyList("user1");
        assertEquals("Size should be one", 1, buyListNotEmpty.size());
    }
}
