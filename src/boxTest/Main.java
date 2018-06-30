/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxTest;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Crows
 */
public class Main extends Application {
    private Group root;
    private EasyGroup world;
    private PerspectiveCamera camera;
    private EasyGroup cameraGroup;
    private EasyGroup boxGroup;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        world = new EasyGroup();
        
        root.getChildren().add(world);
        
        buildCamera();
        buildBox();
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
    
    private void buildCamera(){
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.setTranslateZ(-250);
        cameraGroup = new EasyGroup();
        cameraGroup.rotate(0, -90, 180);
        cameraGroup.getChildren().add(camera);
        root.getChildren().add(cameraGroup);
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
        int speed = 5;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e){
                switch(e.getCode()){
                    case W:
                        cameraGroup.rY(-speed);
                        break;
                    case A:
                        cameraGroup.rX(-speed);
                        break;
                    case S:
                        cameraGroup.rY(speed);
                        break;
                    case D:
                        cameraGroup.rX(speed);
                        break;
                    case UP:
                        cameraGroup.tY(-speed);
                        break;
                    case DOWN:
                        cameraGroup.tY(speed);
                        break;
                    case LEFT:
                        cameraGroup.tX(-speed);
                        break;
                    case RIGHT:
                        cameraGroup.tX(speed);
                        break;
                    case Z:
                        cameraGroup.tZ(speed);
                        break;
                    case X:
                        cameraGroup.tZ(-speed);
                        break;
                }
            }
        });
    }
}
