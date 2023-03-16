import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Exception.CommodityNotExist;
import Baloot.Exception.CommodityOutOfStock;
import Baloot.Exception.ProviderNotExist;
import Baloot.Exception.UserNotExist;
import Baloot.Provider;
import Baloot.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.Arrays;

public class AddToBuyListTest {
    BalootServer balootServer;

    @Before
    public void setup() throws Exception {
        balootServer = new BalootServer();
        balootServer.addUser(new User("user1",
                "1234","user1@gmail.com","1977-09-15","add1",1500));
        balootServer.addUser(new User("user2",
                "1234","user2@gmail.com","1979-09-15","add2",1500));
        balootServer.addProvider(new Provider(1,"provider1","2023-09-15"));
        balootServer.addCommidity(
                new Commodity(1,"Headphone",1,35000,new ArrayList<>(Arrays.asList("Technology","Phone")),0,50));
        balootServer.addCommidity(new Commodity(1,"Headphone",1,35000,
                new ArrayList<>(Arrays.asList("Technology","Phone")),1,0));
        balootServer.addCommidity(new Commodity(1,"Headphone",1,35000,
                new ArrayList<>(Arrays.asList("Technology","Phone")),1,1));

    }

    @After
    public void teardown() {
        balootServer=null;
    }

    @Test(expected = CommodityNotExist.class)
    public void commodityNotExist() throws Exception{
        balootServer.addCommidityToUserBuyList("user1", 3);
    }

    @Test(expected = CommodityOutOfStock.class)
    public void commodityOutOfStock() throws Exception {
        balootServer.addCommidityToUserBuyList("user1", 2);
    }

    @Test(expected = CommodityOutOfStock.class)
    public void commodityStockDecrease() throws Exception{
        balootServer.addCommidityToUserBuyList("user1", 4);
        balootServer.addCommidityToUserBuyList("user2", 4);
    }

    @Test(expected = UserNotExist.class)
    public void userNotExist() throws Exception{
        balootServer.addCommidityToUserBuyList("user5", 4);
    }

}
