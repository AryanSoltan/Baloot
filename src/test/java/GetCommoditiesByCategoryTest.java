import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Exception.ProviderNotExist;
import Baloot.Provider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class GetCommoditiesByCategoryTest {
    private BalootServer balootServer;

    Commodity headphoneCommodity;
    Commodity TVCommodity;
    Commodity iphoneCommodity;
    Commodity cameraCommodity;
    @Before
    public void setup() throws ProviderNotExist {
        balootServer = new BalootServer();
        balootServer.addProvider(1,new Provider(1,"provider1","2023-09-15"));
        headphoneCommodity = new Commodity(1,"Headphone",1,
                1000,new ArrayList<>(Arrays.asList("Technology","Phone")),0,50);
        TVCommodity = new Commodity(2,"TV",1,
                3500,new ArrayList<>(Arrays.asList("Technology")),0,50);
        iphoneCommodity = new Commodity(3,"iphone",1,350,
                new ArrayList<>(Arrays.asList("Phone")),0,50);
        cameraCommodity = new Commodity(4,"SonyD101",1,50.5,
                new ArrayList<>(Arrays.asList("Camera")),0,50);
        balootServer.addCommidity(1,1,headphoneCommodity);
        balootServer.addCommidity(1,2, TVCommodity);
        balootServer.addCommidity(1,3, iphoneCommodity);
        balootServer.addCommidity(1,4, cameraCommodity);
    }
    @After
    public void teardown() {
        balootServer=null;
    }

    @Test
    public void emptyListCategories()
    {
        ArrayList<Commodity> commoditiesEmpty = balootServer.getCommoditiesByCategory("Not a category");
        assertEquals("The output list should be empty", 0, commoditiesEmpty.size());
    }

    @Test
    public void getCategoryWithOneOutput()
    {
        ArrayList<Commodity> commoditiesAnswer = balootServer.getCommoditiesByCategory("Camera");
        assertEquals("Size should be one", 1, commoditiesAnswer.size());
        assertEquals("Object output is not correct", true, commoditiesAnswer.get(0).equals(cameraCommodity));

    }

    @Test
    public void getCategoryWithMoreOneOutput()
    {
        ArrayList<Commodity> commoditiesAnswer = balootServer.getCommoditiesByCategory("Phone");
        assertEquals("Size should be two", 2, commoditiesAnswer.size());
        assertEquals("Object output is not correct", true, commoditiesAnswer.get(0).
                equals(headphoneCommodity));
        assertEquals("Object output is not correct", true, commoditiesAnswer.get(1).
                equals(iphoneCommodity));
    }
}
