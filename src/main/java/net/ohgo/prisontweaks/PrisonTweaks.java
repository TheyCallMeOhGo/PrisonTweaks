package net.ohgo.prisontweaks;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class PrisonTweaks implements ModInitializer {
	public static final String MOD_ID = "prisontweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int nextLevelXp = 0;

	public static int currentXp = 0;

	public static int level = 0;

	public static boolean levelSet = false;

	public static boolean xpSet = false;

	public static HashMap<String, String> items = new HashMap<String, String>();

	@Override
	public void onInitialize() {
        LOGGER.info("Prison Tweaks");
        JSONParser parser = new JSONParser();
        try {
            URL url = new URL("https://item-guide.com/api/items.php");
            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while((line = in.readLine()) != null) {
                content.append(line);
            }
            in.close();
            Object obj = parser.parse(content.toString());
            JSONArray jsonArray = (JSONArray) obj;
            jsonArray.forEach((i) -> {
                JSONObject jsonObject = (JSONObject) i;
                String name = jsonObject.get("name").toString().toLowerCase().replaceAll("'", "").replaceAll(" ", "_");
                String desc = jsonObject.get("description").toString();
                items.put(name, desc);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
