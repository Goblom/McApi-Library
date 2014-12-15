/*
 * Copyright 2014 Goblom.
 */
package codes.goblom.mcap.testing;

import codes.goblom.mcap.McApi;
import codes.goblom.mcap.impl.MinecraftUser;
import codes.goblom.mcap.impl.Query;

/**
 *
 * @author Goblom
 */
public class Testing {
    
    private static McApi instance;
    
    public static McApi getInstance() {
        if (instance == null) {
            instance = new McApi("Test-Component");
        }
        
        return instance;
    }
    
    public static void setDebug(boolean b) {
        getInstance().setDebugMode(b);
    }
    
    public static MinecraftUser getAvatar(String name) {
        return getInstance().getAvatar(name);
    }
    
    public static MinecraftUser getSkin(String name) {
        return getInstance().getSkin(name);
    }
    
    public static Query query(String server) {
        return getInstance().query(server);
    }
    
    public static Query query(String server, int port) {
        return getInstance().query(server, port);
    }
}
