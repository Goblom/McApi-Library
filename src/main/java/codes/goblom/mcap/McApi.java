/*
 * Copyright 2014 Goblom.
 */
package codes.goblom.mcap;

import codes.goblom.mcap.impl.MinecraftUser;
import codes.goblom.mcap.impl.Query;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Goblom
 */
public class McApi {
    
    protected static String WEBSITE =  "http://mcapi.ca/";
    
    protected final String clientName;
    private boolean debug = false;
    
    public McApi(String clientName) {
        this.clientName = clientName;
    }
    
    public void setDebugMode(boolean b) {
        this.debug = b;
    }
    
    public MinecraftUser getAvatar(String playerName) {
        return new McSkin(this, true, playerName);
    }
    
    public MinecraftUser getSkin(String playerName) {
        return new McSkin(this, false, playerName);
    }
    
    public Query query(String server) {
        return query(server, 25565);
    }
    
    public Query query(String server, int port) {
        return new McQuery(this, server, port);
    }
    
    protected URLConnection openURL(String url) throws MalformedURLException, IOException {
        if (debug) {
            System.out.println("Connecting to: " + url);
        }
        
        URLConnection connection = new URL(url).openConnection();
                      connection.setRequestProperty("User-Agent", clientName);
        return connection;
    }
}
