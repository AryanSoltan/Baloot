import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Exception.CommodityNotExist;
import Baloot.Exception.ProviderNotExist;
import Baloot.Exception.UserNotExist;
import Baloot.Provider;
import Baloot.User;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class SearchCommiditiesTest {
    private BalootServer balootServer;
    private Commodity headphoneCommodity;

    @Before
    public void setup() throws ProviderNotExist {

        balootServer = new BalootServer();

        headphoneCommodity = new Commodity(1, "Headphone", 1,
                1000, new ArrayList<>(Arrays.asList("Technology", "Phone")), 0, 80);
        balootServer.addProvider(1, new Provider(1, "provider2", "2023-09-11"));
        balootServer.addCommidity(1, 1, new Commodity(1, "Headphone", 1,
                1000, new ArrayList<>(Arrays.asList("Technology", "Phone")), 0, 80));
        balootServer.addCommidity(1, 2, new Commodity(2, "Bed", 1, 3500,
                new ArrayList<>(Arrays.asList("House")), 0, 70));
        balootServer.addCommidity(1, 3, new Commodity(3, "PS3", 1, 350, new ArrayList<>(Arrays.asList("Game")), 0, 50));
        balootServer.addCommidity(1, 4, new Commodity(4, "SonyD101", 1, 50.5, new ArrayList<>(Arrays.asList("Camera")), 0, 40));
    }

    @After
    public void teardown() {
        balootServer = null;
        headphoneCommodity = null;
    }

    @Test(expected = CommodityNotExist.class)
    public void testCommodityNotExists() throws Exception {
        balootServer.findCommodity(10);
    }

    @Test
    public void testCommodityWithoutError() throws Exception {
        Commodity commodityNeeded = balootServer.findCommodity(4);
        commodityNeeded.equals(headphoneCommodity);
    }

}
