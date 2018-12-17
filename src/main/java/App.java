import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import static org.apache.http.protocol.HTTP.USER_AGENT;

public class App {

    public static void main(String[] args) {
        HttpClient client = HttpClients.createDefault();
        String apiKey = "RGAPI-b3bee612-a228-492c-b74f-e293a2f3b17f";
        //HttpGet request = new HttpGet("https://br1.api.riotgames.com/lol/summoner/v3/summoners/by-name/AIemao?api_key="+apiKey);
        HttpGet request = new HttpGet("https://br1.api.riotgames.com/lol/champion-mastery/v3/champion-masteries/by-summoner/2585384?api_key="+apiKey);

        HttpResponse response;
        try {
            response = client.execute(request);

            // Get the response
            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));

            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }
}
