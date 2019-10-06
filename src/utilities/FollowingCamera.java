package utilities;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Matt
 */
public class FollowingCamera extends PerspectiveCamera{
    private final Group pan;
    private final Group tilt;
    private Node target;
    private final ChangeListener<Number> listenX;
    private final ChangeListener<Number> listenY;
    private final ChangeListener<Number> listenZ;
    private final ChangeListener<Number> listenRotate;
    
    private double tiltSpeed;
    private int zoomSpeed;
    private int zoom;
    
    public FollowingCamera(){
        super(true);
        pan = new Group();
        pan.setRotationAxis(Rotate.Y_AXIS);
        
        tilt = new Group();
        tilt.setRotationAxis(Rotate.X_AXIS);
        pan.getChildren().add(tilt);
        tilt.getChildren().add(this);
        
        listenX = new ChangeListener<Number>() {
            //@Override
            public void changed(Number num) {
                updateX();
            }

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changed(newValue);
            }
        };
        
        listenY = new ChangeListener<Number>() {
            //@Override
            public void changed(Number num) {
                updateY();
            }

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changed(newValue);
            }
        };
        
        listenZ = new ChangeListener<Number>() {
            //@Override
            public void changed(Number num) {
                updateZ();
            }

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changed(newValue);
            }
        };
        
        listenRotate = new ChangeListener<Number>() {
            //@Override
            public void changed(Number num) {
                updateRotate();
            }

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changed(newValue);
                updatePos();
            }
        };
        
        target = null;
        
        tiltSpeed = 10.0;
        zoomSpeed = 10;
        zoom = 400;
    }
    
    public void setTarget(Node n){
        if(target != null){
            n.translateXProperty().removeListener(listenX);
            n.translateYProperty().removeListener(listenY);
            n.translateZProperty().removeListener(listenZ);
            n.rotateProperty().removeListener(listenRotate);
        }
        target = n;
        n.translateXProperty().addListener(listenX);
        n.translateYProperty().addListener(listenY);
        n.translateZProperty().addListener(listenZ);
        n.rotateProperty().addListener(listenRotate);
        
        updateRotate();
        updatePos();
    }
    
    private void updateX(){
        if(target != null){
            pan.setTranslateX(target.getTranslateX() - zoom * Math.sin(pan.getRotate() * Math.PI / 180) * Math.cos(tilt.getRotate() * Math.PI / 180));
        }
    }
    private void updateY(){
        if(target != null){
            pan.setTranslateY(target.getTranslateY() + zoom * Math.sin(tilt.getRotate() * Math.PI / 180));
        }
    }
    private void updateZ(){
        if(target != null){
            pan.setTranslateZ(target.getTranslateZ() - zoom * Math.cos(pan.getRotate() * Math.PI / 180) * Math.cos(tilt.getRotate() * Math.PI / 180));
        }
    }
    private void updateRotate(){
        if(target != null){
            pan.setRotate(target.getRotate());
        }
    }
    private void updatePos(){
        updateX();
        updateY();
        updateZ();
    }
    
    public Group getBase(){
        return pan;
    }
    
    public void tiltUp(){
        tilt.setRotate(tilt.getRotate() - tiltSpeed);
        if(tilt.getRotate() <= -90.0){
            tilt.setRotate(-90.0);
        }
        updatePos();
    }
    
    public void tiltDown(){
        tilt.setRotate(tilt.getRotate() + tiltSpeed);
        if(tilt.getRotate() >= 0.0){
            tilt.setRotate(0.0);
        }
        updatePos();
    }
    
    public void zoomIn(){
        zoom -= zoomSpeed;
        if(zoom <= 0){
            zoom = 0;
        }
        updatePos();
    }
    public void zoomOut(){
        zoom += zoomSpeed;
        if(zoom >= getFarClip()){
            zoom = (int) getFarClip();
        }
        updatePos();
    }
}
