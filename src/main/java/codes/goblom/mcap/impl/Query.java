/*
 * Copyright 2014 Goblom.
 */
package codes.goblom.mcap.impl;

import java.awt.Image;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Goblom
 */
public interface Query {
    
    boolean getStatus();
    
    String getMotd();
    
    String getVersion();
    
    Players players();
    
    String getPing();
    
    Icon getIcon();
    
    String getSoftware();
    
    String getError();
    
    JSONObject asJson();
    
    public interface Players {
        
        int getOnlineCount();
        
        int getMax();
        
        List<String> getOnline();
    }
    
    public interface Icon {
        
        Image asImage();
        
        String asBase64();
    }
}
