import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONReader {

    public static void main(String[] args) throws IOException {
    String sURL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json"; //just a string

    // Connect to the URL using java's native library
    URL url = new URL(sURL);
    URLConnection request = url.openConnection();

    // Convert to a JSON object to print data
    JsonParser jp = new JsonParser(); //from gson
    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
    JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
    String zipcode = rootobj.get("data").toString(); //just grab the zipcode
        System.out.println(zipcode);

    }
}