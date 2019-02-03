package pkg3dintegration;

//import java.lang.Math;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;


public abstract class Function {
    
    public Group getIntegral(double a, double b, double dx, double theta, double dTheta){
        //move this to function
        Group functionGroup = new Group();
        PhongMaterial m = new PhongMaterial(Color.GREEN);
        Box box;
        Group slice;
        
        for(double angle = 0; angle < theta; angle += dTheta){
            
            slice = new Group();
            slice.setRotationAxis(Rotate.X_AXIS);
            slice.setRotate(angle);
            
            for(double x = a; x < b; x += dx){
                box = new Box(dx, 1, 1);
                box.setMaterial(m);
                box.setTranslateX(x);
                box.setTranslateY(f(x));
                slice.getChildren().add(box);
            }
            functionGroup.getChildren().add(slice);
        }
        
        return functionGroup;
    }
    
    public abstract double f(double x);
}
