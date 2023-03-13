package ExternalServer;

import Baloot.BalootServer;
import Baloot.Commodity;
import Baloot.Exception.ProviderNotExist;
import Baloot.Provider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExternalServer {

    String serverAddress;
    BalootServer balootServer;
    public ExternalServer(String inputServerAddress, BalootServer inputBalootServer) throws Exception {
        serverAddress = inputServerAddress;
        balootServer = inputBalootServer;
        addProviders();
        addCommodities();
    }

    public void addProviders()
    {
        String pageProviders = getRequest("/api/providers");
        Gson gsonParser = new GsonBuilder().create();
        List<Provider> providers = gsonParser.fromJson(pageProviders,
                new TypeToken<List<Provider>>() {}.getType());
        for(Provider provider: providers)
        {
            balootServer.addProvider(provider);
        }
    }
    public void addCommodities() throws Exception {
        String pageCommodities = getRequest("/api/commodities");
        Gson gsonParser = new GsonBuilder().create();
        List<Commodity> commodities = gsonParser.fromJson(pageCommodities,
                new TypeToken<List<Commodity>>() {}.getType());
        for(Commodity commodity: commodities)
        {
            balootServer.addCommidity(commodity);
        }
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
