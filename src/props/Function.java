package props;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Matt
 */
public abstract class Function {
    public Group getIntegralShellMethod(double a, double b, double dx){
        Group functionGroup = new Group();
        PhongMaterial m = new PhongMaterial(Color.BLUE);
        Cylinder disk;
        
        for(double x = a; x < b; x += dx){
            disk = new Cylinder(Math.abs(f(x)), dx);
            disk.setMaterial(m);
            disk.setTranslateX(x);
            disk.setRotationAxis(Rotate.Z_AXIS);
            disk.setRotate(90);
            functionGroup.getChildren().add(disk);
        }
        
        return functionGroup;
    }
    public abstract double f(double x);
}
