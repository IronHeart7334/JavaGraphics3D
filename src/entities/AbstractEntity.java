package entities;

import javafx.scene.paint.*;
import javafx.scene.shape.*;

import utilities.EasyGroup;

public abstract class AbstractEntity extends EasyGroup{
    
    private static PhongMaterial blue = new PhongMaterial(Color.BLUE);
    static{
        blue.setSpecularColor(Color.WHITE);
    }
    
    private int size;
    private int speed;
    private int facing; //store as degrees, as radians are hard to read
    
    public AbstractEntity(int x, int y, int z){
        super();
        translate(x, y, z);
        setVisible(true);
        speed = 10;
        facing = 90;
        size = 100;
        
        Box b = new Box(size, size, size);
        b.setMaterial(blue);
        
        getChildren().add(b);
    }
}
