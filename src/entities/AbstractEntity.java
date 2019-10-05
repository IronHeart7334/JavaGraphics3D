package entities;

import static java.lang.System.out;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;

import utilities.EasyGroup;

public abstract class AbstractEntity extends Box{
    
    private static PhongMaterial blue = new PhongMaterial(Color.BLUE);
    static{
        blue.setSpecularColor(Color.WHITE);
    }
    
    private int size;
    private int speed;
    private int facing; //store as degrees, as radians are hard to read
    
    public static final int GRAVITY = 10; //placeholder
    
    public AbstractEntity(int x, int y, int z){
        super(100, 100, 100);
        translate(x, y, z);
        setVisible(true);
        speed = 10;
        facing = 0;
        size = 100;
        setRotationAxis(Rotate.Y_AXIS);
        
        //Box b = new Box(size, size, size);
        setMaterial(blue);
        
        //getChildren().add(b);
    }
    
    public void translate(int x, int y, int z){
        setTranslateX(x);
        setTranslateY(y);
        setTranslateZ(z);
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
    
    public void jump(){
        setTranslateY(getTranslateY() - speed);
    }
    
    public void fall(){
        setTranslateY(getTranslateY() + GRAVITY);
    }
    
    public void displayData(){
        out.println("X: " + getTranslateX());
        out.println("Y: " + getTranslateY());
        out.println("Z: " + getTranslateZ());
    }
}
