package props;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 *
 * @author Matt
 */
public abstract class Function3D {
    //since Y goes up and down
    public Group getShape(double x1, double x2, double z1, double z2, double dx, double dz){
        Group g = new Group();
        PhongMaterial m = new PhongMaterial(Color.BLUE);
        Box b;
        for(double x = x1; x < x2; x += dx){
            for(double z = z1; z < z2; z += dz){
                b = new Box(dx, Math.abs(f(x - dx, z - dz) - f(x + dx, z + dz)), dz);
                b.setMaterial(m);
                b.setTranslateX(x);
                b.setTranslateZ(z);
                b.setTranslateY(f(x, z));
                g.getChildren().add(b);
            }
        }
        return g;
    }
    public abstract double f(double x, double z);
}
