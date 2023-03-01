import Baloot.*;
import Baloot.BalootServer;
import Baloot.Exception.*;
import org.json.simple.JSONObject;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;


public class RateCommodityTest {
    private static BalootServer balootServer;
    @Before
    public void setup() throws Exception {
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
    @Test(expected = UserNotExist.class)
    public void testUserNotExists() throws Exception
    {
        balootServer.rateCommodity("user4",1,"4");
    }

    @Test(expected = CommodityNotExist.class)
    public void testCommodityNotExists() throws Exception
    {
        balootServer.rateCommodity("user1",2,"4");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0","11","4.4", "a"})
    public void testInvalidRating(String rate) throws Exception {
        BalootServer balootServer1 = new BalootServer();
        balootServer1.addUser("user1",new User("user1","1234","user1@gmail.com","1977-09-15","add1",1500));
        balootServer1.addProvider(1,new Provider(1,"provider1","2023-09-15"));
        balootServer1.addCommidity(1,1,new Commodity(1,"Headphone",1,35000,new ArrayList<>(Arrays.asList("Technology","Phone")),0,50));
        assertThrows("testInvalidRating failed",InvalidRating.class, () -> balootServer1.rateCommodity("user1",1,rate));
   }

    @Test
    public void testRateWhenInitialRateIsZero() throws Exception
    {
        balootServer.rateCommodity("user1",1,"4");
        Commodity commodity = balootServer.getCommodityById(1);
        JSONObject commodityInfo = commodity.getJsonData();
        String rate = String.valueOf(commodityInfo.get("rating"));
        double expectedResult = (double)4;
        assertEquals("testRateWhenInitialRateIsZero failed",String.valueOf(expectedResult),rate);
    }

    @Test
    public void testRateWhenInitialRateIsNotZero() throws Exception
    {
        balootServer.rateCommodity("user1",1,"4");
        balootServer.rateCommodity("user2",1,"3");
        balootServer.rateCommodity("user3",1,"3");

        Commodity commodity = balootServer.getCommodityById(1);
        JSONObject commodityInfo = commodity.getJsonData();
        String rate = String.valueOf(commodityInfo.get("rating"));
        double expectedResult = (double)10/(double)3;
        assertEquals("testRateWhenInitialRateIsNotZero failed",String.valueOf(expectedResult),rate);
    }

    @Test
    public void testUpdateRate() throws Exception
    {
        balootServer.rateCommodity("user1",1,"4");
        balootServer.rateCommodity("user2",1,"3");
        balootServer.rateCommodity("user2",1,"6");
        Commodity commodity = balootServer.getCommodityById(1);
        JSONObject commodityInfo = commodity.getJsonData();
        String rate = String.valueOf(commodityInfo.get("rating"));
        double expectedResult = (double)10/(double)2;
        assertEquals("testUpdateRate failed",String.valueOf(expectedResult),rate);
    }








}
