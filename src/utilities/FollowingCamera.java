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
    private final Group rotateBase;
    private final Group panGroup;
    private Node target;
    private final ChangeListener<Number> listenX;
    private final ChangeListener<Number> listenY;
    private final ChangeListener<Number> listenZ;
    private final ChangeListener<Number> updateRotate;
    
    public FollowingCamera(){
        super(true);
        rotateBase = new Group();
        rotateBase.setRotationAxis(Rotate.Y_AXIS);
        
        panGroup = new Group();
        panGroup.setRotationAxis(Rotate.X_AXIS);
        rotateBase.getChildren().add(panGroup);
        panGroup.getChildren().add(this);
        
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
        
        updateRotate = new ChangeListener<Number>() {
            //@Override
            public void changed(Number num) {
                rotateBase.setRotate(num.doubleValue());
            }

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                changed(newValue);
                updateX();
                updateY();
                updateZ();
            }
        };
        
        target = null;
    }
    
    public void setTarget(Node n){
        if(target != null){
            n.translateXProperty().removeListener(listenX);
            n.translateYProperty().removeListener(listenY);
            n.translateZProperty().removeListener(listenZ);
            n.rotateProperty().removeListener(updateRotate);
        }
        target = n;
        n.translateXProperty().addListener(listenX);
        n.translateYProperty().addListener(listenY);
        n.translateZProperty().addListener(listenZ);
        n.rotateProperty().addListener(updateRotate);
    }
    
    private void updateX(){
        if(target != null){
            rotateBase.setTranslateX(target.getTranslateX() - 400 * Math.sin(rotateBase.getRotate() * Math.PI / 180) * Math.cos(panGroup.getRotate() * Math.PI / 180));
        }
    }
    private void updateY(){
        if(target != null){
            rotateBase.setTranslateY(target.getTranslateY() + 400 * Math.sin(panGroup.getRotate() * Math.PI / 180));
        }
    }
    private void updateZ(){
        if(target != null){
            rotateBase.setTranslateZ(target.getTranslateZ() - 400 * Math.cos(rotateBase.getRotate() * Math.PI / 180) * Math.cos(panGroup.getRotate() * Math.PI / 180));
        }
    }
    
    public void setX(double x){
        rotateBase.setTranslateX(x);
    }
    public void setY(double y){
        rotateBase.setTranslateY(y);
    }
    public void setZ(double z){
        rotateBase.setTranslateZ(z);
    }
    
    public Group getBase(){
        return rotateBase;
    }
    
    public Group getPan(){
        return panGroup;
    }
    
    public FollowingCamera setBaseRotate(double degrees){
        rotateBase.setRotate(degrees);
        return this;
    }
}
