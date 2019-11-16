package pkg3dintegration;

//import java.lang.Math;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;


public abstract class Function {
    /**
     * Generates a 3D graph of the function's volume when rotated
     * around the x-axis using the disk method
     * @param a the starting point of the graph, an x value
     * @param b the ending point of the graph, a y value
     * @param dx the width of each disk. The lower this is, the more accurate the graph is.
     * @return a group of cylinders, representing the area under the graph.
     */
    public Group getIntegral(double a, double b, double dx){
        Group functionGroup = new Group();
        PhongMaterial m = new PhongMaterial(Color.GREEN);
        Cylinder disk;
        
        for(double x = a; x < b; x += dx){
            disk = new Cylinder(Math.abs(f(x)), dx); //radius is f(x), height is dx
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
