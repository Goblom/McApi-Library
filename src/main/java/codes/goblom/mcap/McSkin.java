/*
 * Copyright 2014 Goblom.
 */
package codes.goblom.mcap;

import static codes.goblom.mcap.McApi.WEBSITE;
import codes.goblom.mcap.impl.MinecraftUser;
import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Goblom
 */
class McSkin implements MinecraftUser {
    
    private final String player;
    private final McApi api;
    private final boolean isHead;
    
    private int size = Integer.MIN_VALUE;
    private boolean helm = true;
    
    private boolean hasSize = false;
    private boolean hasHelm = false;
    
    McSkin(McApi api, boolean head, String player) {
        this.api = api;
        this.isHead = head;
        this.player = player;
    }
    
    private String getURL(int d) {
        return WEBSITE + (isHead ? "avatar" : "skin") + "/" + d + "d/" + player + "/";
    }
    
    @Override
    public MinecraftUser withSize(int size) {
        this.size = size;
        this.hasSize = true;
        return this;
    }

    @Override
    public MinecraftUser withHelmet(boolean hasHelmet) {
        this.helm = hasHelmet;
        this.hasHelm = true;
        return this;
    }

    @Override
    public Image get3d() {
        return getImage(3);
    }

    @Override
    public Image get2d() {
        return getImage(2);
    }
    
    private Image getImage(int d) {
        StringBuilder sb = new StringBuilder(getURL(d));
        
        if (hasSize) {
            sb.append(size);
            sb.append("/");
        }
        
        if (hasHelm) {
            sb.append(helm);
        }
                
        try {
            return ImageIO.read(api.openURL(sb.toString()).getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
