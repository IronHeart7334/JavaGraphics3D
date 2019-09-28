package world;

import javafx.scene.paint.Material;

/**
 *
 * @author Matt
 */
public class CubeTemplate {
    Material cubeMat;
    
    public CubeTemplate(Material m){
        cubeMat = m;
    }
    
    public Cube create(int x, int y, int z){
        return new Cube(x, y, z, cubeMat);
    }
}
