package ExternalServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExternalServer {

    String serverAddress;
    public ExternalServer(String inputServerAddress)
    {
        serverAddress = inputServerAddress;
    }

    public void getRequest(String inputUrl)
    {
        try {
            URL url = new URL(serverAddress + inputUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            while(true)
            {
                System.out.println(inputStream.read());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
