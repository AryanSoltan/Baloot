import Baloot.*;
import Baloot.Exception.CommodityNotExist;
import InterfaceServer.InterfaceServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class SearchCommodityByPriceTest {
    BalootServer balootServer;

    Commodity headphoneCommodity;
    Commodity TVCommodity;
    Commodity iphoneCommodity;
    Commodity cameraCommodity;
    int PORT_NUM = 8080;
    InterfaceServer interfaceServer;
    @Before
    public void setup() throws Exception {

        balootServer = new BalootServer();
        balootServer.addProvider(new Provider(1,"provider1","2023-09-15"));
        headphoneCommodity = new Commodity(1,"Headphone",1,
                1000,new ArrayList<>(Arrays.asList("Technology","Phone")),0,50);
        TVCommodity = new Commodity(2,"TV",1,
                3500,new ArrayList<>(Arrays.asList("Technology")),0,50);
        iphoneCommodity = new Commodity(3,"iphone",1,5000,
                new ArrayList<>(Arrays.asList("Phone")),0,50);
        cameraCommodity = new Commodity(4,"SonyD101",1,4000,
                new ArrayList<>(Arrays.asList("Camera")),0,50);
        balootServer.addCommidity(headphoneCommodity);
        balootServer.addCommidity( TVCommodity);
        balootServer.addCommidity(iphoneCommodity);
        balootServer.addCommidity(cameraCommodity);
    }

    @After
    public void teardown() {
        balootServer=null;
    }

    @Test
    public void noCommodityInRange() throws Exception{
        ArrayList<Commodity> commodities = balootServer.getCommodityRangePrice(100, 200);
        assertEquals("Search with 0 results failed",0,commodities.size());
    }

    @Test
    public void wrongRange() throws Exception{
        ArrayList<Commodity> commodities = balootServer.getCommodityRangePrice(300, 200);
        assertEquals("Search with 0 results failed",0,commodities.size());
    }

    @Test
    public void someCommodityInRange() throws Exception{
        ArrayList<Commodity> commodities = balootServer.getCommodityRangePrice(1000, 2000);
        assertEquals("Range in case one commodity is wrong",
                true,commodities.get(0).equals(headphoneCommodity));
    }

    @Test
    public void allCommodityInRange() throws Exception{
        ArrayList<Commodity> commodities = balootServer.getCommodityRangePrice(1000, 7000);
        assertEquals("Range in case all commodity is wrong",
                commodities.size(), 4);
    }

}
