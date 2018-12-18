import com.google.gson.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;


import static org.apache.http.protocol.HTTP.USER_AGENT;

public class App {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static Gson gson = new Gson();

    private static <T> T makeRequest(String urlString, Class<T> clazz) throws IOException {


        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());

        T parsed = gson.fromJson(reader, clazz);
        return parsed;
    }

    /*
    public static void main(String[] args) throws IOException, JSONException {

    }
*/

    public static void main(String[] args) throws JSONException {
    //public void Teste() {
        HttpClient client = HttpClients.createDefault();
        String apiKey = "RGAPI-304961db-c399-441e-bcac-616bf39bd3fb";
        HttpGet request = new HttpGet("https://br1.api.riotgames.com/lol/champion-mastery/v3/champion-masteries/by-summoner/2585384?api_key="+apiKey);
        //HttpGet request = new HttpGet("http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json");

        HttpResponse response;
        try {
            response = client.execute(request);

            // Get the response
            BufferedReader br;

            br = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));

          //  System.out.println(br.readLine());
            String str = br.readLine();
            str = str.replace("[","");
            str = str.replace("\"","");
            final String[] split = str.split("\\}\\,\\{");
/*
            for(int i=0;i<split.length;i++){
                System.out.println(split[i]);
            }


*/
            URL url = new URL("https://br1.api.riotgames.com/lol/champion-mastery/v3/champion-masteries/by-summoner/2585384?api_key="+apiKey);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            int data = reader.read();
            while(data != -1){
                data = reader.read();

            }
            //System.out.print(data);
            //System.out.println(reader.read()); //teste

            JSONObject json = readJsonFromUrl("http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json");
            //System.out.println(json.get("data"));

            Gson gson = new Gson();

            Champion parsed = gson.fromJson(reader, Champion.class);

            //System.out.println(parsed);

            System.out.println(makeRequest("http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json",Champion.class));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    public class Champion {
        // String
        public String id;
        // Numeric
        public String key;
        public String name;
    }
}
