import Baloot.*;
import Baloot.BalootServer;
import Baloot.Exception.CommodityNotExist;
import Baloot.Exception.InvalidRating;
import Baloot.Exception.ProviderNotExist;
import Baloot.Exception.UserNameNotValid;
import org.json.simple.JSONObject;
import org.junit.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;


//import org.junit.params.ParameterizedTest;

public class rateCommodityTest {
    private BalootServer balootServer;
    @Before
    public void setup() throws ProviderNotExist {
        balootServer = new BalootServer();
        balootServer.addUser("user1",new User("user1","1234","user1@gmail.com","1977-09-15","add1",1500));
        balootServer.addUser("user2",new User("user2","1234","user2@gmail.com","1977-09-15","add2",500));
        balootServer.addUser("user3",new User("user3","1234","user3@gmail.com","1977-09-15","add3",500));
        balootServer.addProvider(1,new Provider(1,"provider1","2023-09-15"));
        balootServer.addCommidity(1,1,new Commodity(1,"Headphone",1,35000,new ArrayList<>(Arrays.asList("Technology","Phone")),0,50));

    }

    @After
    public void teardown() {
        balootServer=null;
    }
    @Test(expected = UserNameNotValid.class)
    public void testUserNotExists() throws Exception
    {
        balootServer.rateCommodity("user4",1,"4");
    }

    @Test(expected = CommodityNotExist.class)
    public void testCommodityNotExists() throws Exception
    {
        balootServer.rateCommodity("user1",2,"4");
    }

//    @ParameterizedTest
//    @ValueSource(strings = {"0","11","4.4", "a"})
//    @Test(expected = InvalidRating.class)
//    public void testInvalidRating(String rate) throws Exception
//    {
//        balootServer.rateCommodity("user1",1,rate);
//    }

    @Test
    public void testRateWhenInitialRateIsZero() throws Exception
    {
        balootServer.rateCommodity("user1",1,"4");
        Commodity commodity = balootServer.getCommodityById(1);
        JSONObject commodityInfo = commodity.getJsonData();
        String rate = (String)commodityInfo.get("rating");
        assertEquals("testRateWhenInitialRateIsZero failed","4",rate);
    }

    @Test
    public void testRateWhenInitialRateIsNotZero() throws Exception
    {
        balootServer.rateCommodity("user1",1,"4");
        balootServer.rateCommodity("user2",1,"3");
        balootServer.rateCommodity("user3",1,"3");

        Commodity commodity = balootServer.getCommodityById(1);
        JSONObject commodityInfo = commodity.getJsonData();
        String rate = (String)commodityInfo.get("rating");
        assertEquals("testRateWhenInitialRateIsNotZero failed",(double)10/(double)3,rate);
    }

    @Test
    public void testUpdateRate() throws Exception
    {
        balootServer.rateCommodity("user1",1,"4");
        balootServer.rateCommodity("user2",1,"3");
        balootServer.rateCommodity("user2",1,"6");
        Commodity commodity = balootServer.getCommodityById(1);
        JSONObject commodityInfo = commodity.getJsonData();
        String rate = (String)commodityInfo.get("rating");
        assertEquals("testUpdateRate failed","5",rate);
    }








}
