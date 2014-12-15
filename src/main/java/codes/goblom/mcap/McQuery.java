/*
 * Copyright 2014 Goblom.
 */
package codes.goblom.mcap;

import static codes.goblom.mcap.McApi.WEBSITE;
import codes.goblom.mcap.impl.Query;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Goblom
 */
public class McQuery implements Query {

    private final McApi api;
    private final String server;
    private final int port;

    private final Players players;
    private final Icon icon;
    private JSONObject queryObject;

    McQuery(McApi api, String server, int port) {
        this.api = api;
        this.server = server;
        this.port = port;

        this.players = new McPlayers();
        this.icon = new McIcon();

        Exception ex = null;
        try {
            this.queryObject = new JSONObject(getStringResult(api.openURL(getURL("info"))));
        } catch (Exception e) {
            ex = e;
        }

        if (queryObject == null && ex != null) {
            queryObject = new JSONObject();
            try {
                queryObject.put("error", ex.getMessage());
            } catch (JSONException ex1) {
                ex.printStackTrace();
            }
        } else {
            try {
                String software = getOptionFromJsonString("software", getStringResult(api.openURL(getURL("software"))), "");
                queryObject.put("software", software);
                
//                JSONObject players = queryObject.getJSONObject("players");
//                
//                URLConnection list = api.openURL(getURL("list"));
//                JSONObject player2 = getOptionFromJsonString("Players", getStringResult(list), new JSONObject());
//                List<String> value = (List<String>) player2.get("list");
//                
//                players.put("list", value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getURL(String sub) {
        return WEBSITE + "query/" + server + ":" + port + "/" + sub;
    }

    private String getStringResult(URLConnection con) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    private <T> T getOptionFromJsonString(String option, String jsonString, T def) {
        try {
            if (def instanceof JSONObject) {
                return (T) new JSONObject(jsonString).getJSONObject(option);
            }
            
            return (T) new JSONObject(jsonString).get(option);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return def;
    }

    private <T> T get(String option) {
        try {
            return (T) asJson().get(option);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean getStatus() {
        return get("status");
    }

    @Override
    public String getMotd() {
        return get("motd");
    }

    @Override
    public String getVersion() {
        return get("version");
    }

    @Override
    public Players players() {
        return players;
    }

    @Override
    public String getPing() {
        return get("ping");
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public String getSoftware() {
        return get("software");
    }

    @Override
    public String getError() {
        return get("error");
    }

    @Override
    public JSONObject asJson() {
        return this.queryObject;
    }

    class McPlayers implements Players {

        private JSONObject get() throws JSONException {
            return asJson().getJSONObject("players");
        }

        @Override
        public int getOnlineCount() {
            try {
                return get().getInt("online");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return -1;
        }

        @Override
        public int getMax() {
            try {
                return get().getInt("max");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return -1;
        }

        @Override
        public List<String> getOnline() {
//            try {
//                return (List<String>) get().get("list");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            return Collections.EMPTY_LIST;
        }

    }

    class McIcon implements Icon {

        private Image image;
        private String base;

        McIcon() {
            load();
        }

        private void load() {
            String imgUrl = getURL("icon");
            String baseUrl = getURL("icon/true");

            try {
                image = ImageIO.read(api.openURL(imgUrl).getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                base = getStringResult(api.openURL(baseUrl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Image asImage() {
            return image;
        }

        @Override
        public String asBase64() {
            return base;
        }

    }
}
