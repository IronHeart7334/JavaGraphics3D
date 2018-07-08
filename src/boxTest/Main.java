package boxTest;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.lang.Math;

import utilities.EasyGroup;
import props.Crate;

public class Main extends Application {
    private Group root;
    private EasyGroup world;
    private PerspectiveCamera camera;
    private EasyGroup cameraBase;
    private EasyGroup cameraTilt;
    private EasyGroup boxGroup;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        world = new EasyGroup();
        
        root.getChildren().add(world);
        
        buildCamera();
        buildBox();
        
        world.getChildren().add(new Crate(200, 200, 200, 100));
        
        buildFloor();
        
        Scene scene = new Scene(root, 500, 500, true);
        scene.setFill(Color.LIGHTBLUE);
        scene.setCamera(camera);
        
        primaryStage.setTitle("Box Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        registerKeys(scene, root);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    //need to make two camera EasyGroups to fix up/down panning
    private void buildCamera(){
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.setTranslateZ(-400);
        cameraBase = new EasyGroup();
        cameraBase.translate(0, 200, -400);
        cameraBase.rotate(180, 160, 180);
        cameraTilt = new EasyGroup();
        cameraBase.getChildren().add(cameraTilt);
        cameraTilt.getChildren().add(camera);
        root.getChildren().add(cameraBase);
    }
    
    private void buildFloor(){
        EasyGroup groundGroup = new EasyGroup();
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(Color.SANDYBROWN);
        mat.setSpecularColor(Color.GOLD);
        
        groundGroup.translate(0, 400, 0);
        
        Box b = new Box(500, 100, 500);
        b.setMaterial(mat);
        
        groundGroup.getChildren().add(b);
        world.getChildren().add(groundGroup);
    }
    
    private void buildBox(){
        boxGroup = new EasyGroup();
        PhongMaterial mat = new PhongMaterial();
        mat.setDiffuseColor(Color.BROWN);
        mat.setSpecularColor(Color.GOLD);
        
        Box b = new Box(100, 100, 100);
        b.setMaterial(mat);
        
        boxGroup.translate(200, 300, 200);
        boxGroup.getChildren().add(b);
        boxGroup.setVisible(true);
        world.getChildren().add(boxGroup);
    }
    
    private void registerKeys(Scene scene, final Node root){
        int speed = 10;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e){
                double angle = (cameraBase.getRY() * Math.PI / 180);
                switch(e.getCode()){
                    case W:
                    	//for some reason axis are swapped?
                        cameraTilt.rX(speed);
                        break;
                    case A:
                        cameraBase.rY(speed);
                        break;
                    case S:
                        cameraTilt.rX(-speed);
                        break;
                    case D:
                        cameraBase.rY(-speed);
                        break;
                    case UP:
                        cameraBase.tX((int)(-speed * Math.cos(angle + Math.PI / 2)));
                        cameraBase.tZ((int)(-speed * Math.sin(angle + Math.PI / 2)));
                        break;
                    case DOWN:
                        cameraBase.tX((int)(speed * Math.cos(angle + Math.PI / 2)));
                        cameraBase.tZ((int)(speed * Math.sin(angle + Math.PI / 2)));
                        break;
                    case LEFT:
                        // why does this work?
                        cameraBase.tX((int)(speed * Math.cos(angle)));
                        cameraBase.tZ((int)(speed * Math.sin(angle)));
                        break;
                    case RIGHT:
                        cameraBase.tX((int)(-speed * Math.cos(angle)));
                        cameraBase.tZ((int)(-speed * Math.sin(angle)));
                        break;
                    case Z:
                        cameraBase.tY(-speed);
                        break;
                    case X:
                        cameraBase.tY(speed);
                        break;
                    default:
                    	cameraBase.logData();
                    	break;
                }
            }
        });
    }
}
