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
        /**File file = null;
        try {
            File modDirectory = FabricLoader.getInstance().getGameDir().resolve("mods").toFile();
            file = new File(modDirectory, "items.json");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedInputStream in = new BufferedInputStream(new URL("https://item-guide.com/api/items.php").openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }**/
        JSONParser parser = new JSONParser();
        try {
            //Object obj = parser.parse(new FileReader(file));
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
            System.out.println(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
