package world;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;

/**
 *
 * @author Matt
 */
public class Cube extends Box{
    public static final int CUBE_SIZE = 100; 
    //todo add different sizes for merging stuff together
    public Cube(int x, int y, int z, Material m){
        super(CUBE_SIZE, CUBE_SIZE, CUBE_SIZE);
        setTranslateX(x * CUBE_SIZE);
        setTranslateY(y * CUBE_SIZE);
        setTranslateZ(z * CUBE_SIZE);
        setMaterial(m);
    }
}
