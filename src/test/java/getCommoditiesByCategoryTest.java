import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Exception.ProviderNotExist;
import Baloot.Provider;
import Baloot.User;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.Arrays;

public class getCommoditiesByCategoryTest {
    private BalootServer balootServer;
    @BeforeClass
    public void setup() throws ProviderNotExist {
        balootServer = new BalootServer();

        balootServer.addProvider(1,new Provider(1,"provider1","2023-09-15"));
        balootServer.addCommidity(1,1,new Commodity(1,"Headphone",1,1000,new ArrayList<>(Arrays.asList("Technology","Phone")),0,50));
        balootServer.addCommidity(1,2,new Commodity(2,"TV",1,3500,new ArrayList<>(Arrays.asList("Technology")),0,50));
        balootServer.addCommidity(1,3,new Commodity(3,"iphone",1,350,new ArrayList<>(Arrays.asList("Phone")),0,50));
        balootServer.addCommidity(1,4,new Commodity(4,"SonyD101",1,50.5,new ArrayList<>(Arrays.asList("Camera")),0,50));


    }
}
