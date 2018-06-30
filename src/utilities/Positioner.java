package utilities;

import javafx.scene.shape.Shape3D;

public class Positioner {
	public static void repos(Shape3D s, int x, int y, int z){
		s.setTranslateX(x);
		s.setTranslateY(y);
		s.setTranslateZ(z);
	}
}
