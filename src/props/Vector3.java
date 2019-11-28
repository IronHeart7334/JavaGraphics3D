package props;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Matt
 */
public class Vector3 {
    private double rx;
    private double ry;
    private double rz;
    private double norm;
    private double theta;
    private double phi;
    private final Box box;
    private final Group base;
    
    public Vector3(double x, double y, double z, double magnitude, double theta, double phi){
        rx = x;
        ry = y;
        rz = z;
        norm = magnitude;
        
        //spherical coordinates
        this.theta = theta;
        this.phi = phi;
        base = new Group();
        base.setTranslateX(x);
        base.setTranslateY(y);
        base.setTranslateZ(z);
        base.setRotationAxis(Rotate.Z_AXIS);
        base.setRotate(theta);
        box = new Box(1, 1, norm);
        box.setMaterial(new PhongMaterial(Color.BLUE));
        box.setRotationAxis(Rotate.X_AXIS);
        box.setRotate(phi);
        base.getChildren().add(box);
    }
    public double getTheta(){
        return theta;
    }
    public double getPhi(){
        return phi;
    }
    public void setRotates(double theta, double phi){
        base.setRotate(theta);
        box.setRotate(phi);
        this.theta = theta;
        this.phi = phi;
    }
    
    public Group getBase(){
        return base;
    }
}
