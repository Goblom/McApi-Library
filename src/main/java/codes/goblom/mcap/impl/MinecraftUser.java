/*
 * Copyright 2014 Goblom.
 */
package codes.goblom.mcap.impl;

import java.awt.Image;

/**
 *
 * @author Goblom
 */
public interface MinecraftUser {
    
    MinecraftUser withSize(int size);

    MinecraftUser withHelmet(boolean hasHelmet);
    
    Image get3d();
    
    Image get2d();
}
