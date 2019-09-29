package entities;

import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;

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
        facing = 0;
        size = 100;
        setRotationAxis(Rotate.Y_AXIS);
        Box b = new Box(size, size, size);
        b.setMaterial(blue);
        
        getChildren().add(b);
    }
    
    public int getSize(){
        return size;
    }
    
    public void turnLeft(){
        facing = (facing - 10) % 360;
        setRotate(facing);
    }
    
    public void turnRight(){
        facing = (facing + 10) % 360;
        setRotate(facing);
    }

    public void moveForward() {
        setTranslateX(getTranslateX() + speed * Math.sin(facing * Math.PI / 180));
        setTranslateZ(getTranslateZ() + speed * Math.cos(facing * Math.PI / 180));
    }
    
    public void moveBackward(){
        setTranslateX(getTranslateX() - speed / 2 * Math.sin(facing * Math.PI / 180));
        setTranslateZ(getTranslateZ() - speed / 2 * Math.cos(facing * Math.PI / 180));
    }
}
