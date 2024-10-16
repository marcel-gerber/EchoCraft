package de.marcelgerber.echocraft.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Class for handling the information of the server
 */
public class ServerInfo {

    private static final String motd = "What's a ghast's favorite country? The Nether-Lands!";
    private static final int maxPlayers = 20;
    private static final int onlinePlayers = 1;
    private static final String versionName = "1.21.1";
    private static final int versionProtocol = 767;

    /**
     * Function that returns a JSON-String containing
     * current information about the server
     *
     * @return String
     */
    public static String getServerStatusJson() {
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();

        // create "description" object
        JsonObject description = new JsonObject();
        JsonArray extraArray = new JsonArray();
        JsonObject extraObject = new JsonObject();
        extraObject.addProperty("text", motd);
        extraArray.add(extraObject);
        description.add("extra", extraArray);
        description.addProperty("text", "");

        // create "players" object
        JsonObject players = new JsonObject();
        players.addProperty("max", maxPlayers);
        players.addProperty("online", onlinePlayers);

        // create "version" object
        JsonObject version = new JsonObject();
        version.addProperty("name", versionName);
        version.addProperty("protocol", versionProtocol);

        // Add all objects to the main object
        jsonObject.add("description", description);
        jsonObject.add("players", players);
        jsonObject.add("version", version);

        return gson.toJson(jsonObject);
    }

}
