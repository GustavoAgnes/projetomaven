import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;

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

    public static void main(String[] args) throws JSONException, IOException {
        HttpClient client = HttpClients.createDefault();
        String apiKey = "RGAPI-e2066da1-82e3-458f-a7db-164009982d6f";
        HttpGet request = new HttpGet("https://br1.api.riotgames.com/lol/champion-mastery/v3/champion-masteries/by-summoner/2584538?api_key=" + apiKey);
        //HttpGet request = new HttpGet("http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json");

        HttpResponse response;
        try {
            response = client.execute(request);
            BufferedReader br;
            String str = "";
            br = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));
            String line = "";

            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                str = line;
            }

            str = str.replace("[", "");
            str = str.replace("\"", "");
            final String[] split = str.split("\\}\\,\\{");
            System.out.println(readJsonFromUrl("http://ddragonexplorer.com/cdn/8.22.1/data/pt_BR/champion.json").getJSONObject("data").getString("Jax"));

            for (int i = 0; i < split.length; i++) {
                System.out.println(split[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }

        DDragonManager dDragonManager = new DDragonManager();
        System.out.println(dDragonManager.retornaCampeao(157));
    }
}
