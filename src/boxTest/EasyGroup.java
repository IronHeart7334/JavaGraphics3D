package boxTest;

import javafx.scene.Group;
import javafx.scene.transform.*;
import static java.lang.System.out;

public class EasyGroup extends Group{
    private Translate t;
    private Translate p; // what's a pivot?
    private Translate ip; // this is also some pivot thing
    private Rotate[] r;
    private Scale s;
    
    public EasyGroup(){
        super();
        
        t = new Translate();
        p = new Translate();
        ip = new Translate();
        
        s = new Scale();
        
        getTransforms().addAll(t, s);
        
        r = new Rotate[3];
        r[0] = new Rotate();
        r[0].setAxis(Rotate.X_AXIS);
        r[1] = new Rotate();
        r[1].setAxis(Rotate.Y_AXIS);
        r[2] = new Rotate();
        r[2].setAxis(Rotate.Z_AXIS);
        
        getTransforms().addAll(r);
    }
    
    public void translate(int x, int y, int z){
        t.setX(x);
        t.setY(y);
        t.setZ(x);
    }
    
    public void tX(int x){
        t.setX(t.getX() + x);
    }
    public void tY(int y){
        t.setY(t.getY() + y);
    }
    public void tZ(int z){
        t.setZ(t.getZ() + z);
    }
    
    public void rotate(int x, int y, int z){
        r[0].setAngle(x);
        r[1].setAngle(y);
        r[2].setAngle(z);
    }
    public void rX(int x){
        r[0].setAngle(r[0].getAngle() + x);
    }
    public void rY(int y){
        r[1].setAngle(r[1].getAngle() + y);
    }
    public void rZ(int z){
        r[2].setAngle(r[2].getAngle() + z);
    }
    
    public void scale(int i){
        s.setX(i);
        s.setY(i);
        s.setZ(i);
    }
    
    public void setPivot(int x, int y, int z){
        p.setX(x);
        p.setY(y);
        p.setZ(z);
        ip.setX(-x);
        ip.setY(-y);
        ip.setZ(-z);
    }
    public void logData(){
    	out.println(t.getX() + ", " + t.getY() + ", " + t.getZ());
    	out.println(r[0].getAngle() + ", " + r[1].getAngle() + ", " + r[2].getAngle());
    }
}
