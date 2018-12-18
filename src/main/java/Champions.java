import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;
import net.rithms.riot.api.endpoints.champion.dto.Champion;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Champions {

    private static Gson gson = new Gson();
    // A cache of parsed responses, keyed by DDragon URL
    private static Map<String, Object> cache = new HashMap<>();

    private static <T> T makeRequest(String urlString, Class<T> clazz, boolean checkCache) throws IOException {
        if (checkCache && cache.containsKey(urlString)) {
            return (T) cache.get(urlString);
        }

        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());

        T parsed = gson.fromJson(reader, clazz);
        cache.put(urlString, parsed);
        return parsed;
    }

    public static DDragonManager.ChampionList getChampionList(String version) throws IOException {
        return makeRequest("https://ddragon.leagueoflegends.com/cdn/" + version + "/data/en_US/champion.json", DDragonManager.ChampionList.class, true);
    }


    /**
     * Gets a long version (e.g. "7.20.3") used by DDragon that corresponds to a short version (e.g. "7.20"). Uses the latest version available for the specified short version.
     *
     * @return A long version, or `null` if no matching version could be found
     */
    public static String getLongVersion(String shortVersion) throws IOException {
        // Append "." to the string to prevent partial matches (e.g. "7.2" matching to '7.20.1")
        shortVersion += ".";
        String[] versions = makeRequest("https://ddragon.leagueoflegends.com/api/versions.json", String[].class, true);
        for (String longVersion : versions) {
            if (longVersion.startsWith(shortVersion)) {
                return longVersion;
            }
        }

        return null;
    }

    public static String convertToShortVersion(String longVersion) {
        return longVersion.substring(0, longVersion.indexOf(".", longVersion.indexOf(".") + 1));
    }

    /**
     * Gets the most recent DDragon version
     *
     * @return The latest long version (e.g. "7.20.3")
     */
    public static String getLatestVersion() throws IOException {
        String[] versions = makeRequest("https://ddragon.leagueoflegends.com/api/versions.json", String[].class, true);
        return versions[0];
    }

    /**
     * Gets the IDs of all available champions for a certain DDragon version
     */
    public static List<Short> getChampionIds(String ddragonVersion) throws IOException {
        return getChampionList(ddragonVersion).data.values().stream().map(champion -> Short.parseShort(champion.key)).collect(Collectors.toList());
    }


    // https://ddragon.leagueoflegends.com/cdn/7.24.1/data/en_US/champion.json
    public class ChampionList {

        public Map<String, DDragonManager.ChampionList.Champion> data;

        public class Champion {
            public String id;
            public String key;
            public String name;
            //public DDragonManager.ChampionList.Champion.ChampionImage image;
        }
    }

    public static void main(String args[]) throws IOException {
        System.out.println(getChampionList(getLatestVersion()).data.values().stream().map(champion -> Short.parseShort(champion.key)).collect(Collectors.toList()));
    }
}
