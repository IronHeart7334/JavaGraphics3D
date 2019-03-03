package boxTest;

import entities.AbstractEntity;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.lang.Math;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

import utilities.EasyGroup;
import props.Crate;

public class Main extends Application implements EventHandler{
    private Group root;
    private EasyGroup world;
    private PerspectiveCamera camera;
    private EasyGroup cameraBase;
    private EasyGroup cameraTilt;
    private EasyGroup boxGroup;
    
    private AbstractEntity player;
    private Group obstacles;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        world = new EasyGroup();
        obstacles = new Group();
        
        root.getChildren().add(world);
        
        buildCamera();
        buildBox();
        
        
        world.getChildren().add(obstacles);
        obstacles.getChildren().add(new Crate(200, 200, 200, 100));
        
        player = new AbstractEntity(0, 0, 0){};
        player.getChildren().add(cameraBase);
        
        world.getChildren().add(player);
        buildFloor();
        
        Scene scene = new Scene(root, 500, 500, true);
        scene.setFill(Color.LIGHTBLUE);
        scene.setCamera(camera);
        
        primaryStage.setTitle("Box Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        registerKeys(scene, root);
        //scene.setOnMouseMoved(this);
        
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(!checkForCollide(player)){
                    player.tY(10);
                }
            }
        }.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean checkForCollide(Node n){
        return obstacles
                .getChildren()
                .filtered(node -> !node.equals(n))
                .filtered(node -> node.getBoundsInParent().intersects(n.getBoundsInParent()))
                .size() != 0;
    }
    
    //need to make two camera EasyGroups to fix up/down panning
    private void buildCamera(){
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.setTranslateZ(-400);
        cameraBase = new EasyGroup();
        cameraBase.translate(0, 200, -400);
        cameraBase.rotate(180, 90, 180);
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
        obstacles.getChildren().add(groundGroup);
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
        obstacles.getChildren().add(boxGroup);
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
                        player.moveForward();
                        break;
                    case DOWN:
                        cameraBase.tX((int)(speed * Math.cos(angle + Math.PI / 2)));
                        cameraBase.tZ((int)(speed * Math.sin(angle + Math.PI / 2)));
                        break;
                    case LEFT:
                        player.turnLeft();
                        break;
                    case RIGHT:
                        player.turnRight();
                        break;
                    case Z:
                        cameraBase.tY(-speed);
                        break;
                    case X:
                        cameraBase.tY(speed);
                        break;
                    case SPACE:
                        System.out.println(cameraBase.getRotate());
                        break;
                    default:
                    	cameraBase.logData();
                    	break;
                }
            }
        });
    }

    @Override
    public void handle(Event event) {
        if(event instanceof MouseEvent){
            MouseEvent e = (MouseEvent)event;
            System.out.println(e.getScreenX() + ", " + e.getScreenY());
            //x seems too big
        }
    }
}
