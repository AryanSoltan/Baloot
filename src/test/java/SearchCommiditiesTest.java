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
    public void setup() throws Exception {

        balootServer = new BalootServer();

        headphoneCommodity = new Commodity(1, "Headphone", 1,
                1000, new ArrayList<>(Arrays.asList("Technology", "Phone")), 0, 80);
        balootServer.addProvider(new Provider(1, "provider2", "2023-09-11"));
        balootServer = new BalootServer();
        balootServer.addCommidity(
                new Commodity(1, "Headphone", 1, 3000, new ArrayList<>(Arrays.asList("Technology", "Phone")), 0, 50));
        balootServer.addCommidity(new Commodity(1, "Headphone", 1, 2000,
                new ArrayList<>(Arrays.asList("Technology", "Phone")), 1, 0));
        balootServer.addCommidity(new Commodity(1, "Headphone", 1, 1000,
                new ArrayList<>(Arrays.asList("Technology", "Phone")), 1, 1));
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
