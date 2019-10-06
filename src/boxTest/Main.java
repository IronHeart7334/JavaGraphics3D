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
import static java.lang.System.out;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

import utilities.EasyGroup;
import props.Crate;
import utilities.FollowingCamera;
import world.Cube;
import world.World;

public class Main extends Application{
    private Group root;
    private World world;
    private FollowingCamera camera;
    
    private AbstractEntity player;
    private Group obstacles;
    
    private int cameraOffset = 400;
    private final int CAMERA_OFFSET_SPEED = 10;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        world = World.createTest();
        Group g = world.getGroup();
        obstacles = new Group();
        player = new AbstractEntity(0, 0, 0){};
        root.getChildren().add(g);
        g.getChildren().add(buildAxis(Cube.CUBE_SIZE * 10));
        
        buildCamera();
        
        g.getChildren().add(obstacles);
        g.getChildren().add(player);
        //obstacles.getChildren().add(new Crate(200, 200, 200, 100));
        
        g.getChildren().add(camera.getBase());
        camera.setTarget(player);
        
        
        Scene scene = new Scene(root, 500, 500, true);
        scene.setFill(Color.LIGHTBLUE);
        scene.setCamera(camera);
        
        primaryStage.setTitle("Box Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        registerKeys(scene, root);
        
        
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(!world.checkForCollisions(player)){
                    player.fall();
                }
                updateCamera();
            }
        }.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private Group buildAxis(int axisSize){
        Group axis = new Group();
        Box x = new Box(axisSize, 1, 1);
        Box y = new Box(1, axisSize, 1);
        Box z = new Box(1, 1, axisSize);
        x.setTranslateX(axisSize / 2);
        y.setTranslateY(axisSize / 2);
        z.setTranslateZ(axisSize / 2);
        x.setMaterial(new PhongMaterial(Color.RED));
        y.setMaterial(new PhongMaterial(Color.GREEN));
        z.setMaterial(new PhongMaterial(Color.BLUE));
        axis.getChildren().add(x);
        axis.getChildren().add(y);
        axis.getChildren().add(z);
        return axis;
    }
    
    private void buildCamera(){
        camera = new FollowingCamera();
        camera.setNearClip(1);
        camera.setFarClip(Cube.CUBE_SIZE * 20);
        updateCamera();
    }
    
    private void updateCamera(){
        //camera.setBaseRotate(player.getRotate());
        //camera.setX(player.getTranslateX() - cameraOffset * Math.sin(player.getRotate() * Math.PI / 180) * Math.cos(camera.getPan().getRotate() * Math.PI / 180));
        //camera.setY(player.getTranslateY() + cameraOffset * Math.sin(camera.getPan().getRotate() * Math.PI / 180));
        //camera.setZ(player.getTranslateZ() - cameraOffset * Math.cos(player.getRotate() * Math.PI / 180) * Math.cos(camera.getPan().getRotate() * Math.PI / 180));
    }
    
    private void registerKeys(Scene scene, final Node root){
        int speed = 5;
        scene.setOnKeyPressed((KeyEvent e) -> {
            switch(e.getCode()){
                case EQUALS:
                    cameraOffset -= CAMERA_OFFSET_SPEED;
                    if(cameraOffset <= player.getSize() * 3){
                        cameraOffset = player.getSize() * 3;
                    }
                    break;
                case MINUS:
                    cameraOffset += CAMERA_OFFSET_SPEED;
                    if(cameraOffset >= 1000){
                        cameraOffset = 1000;
                    }
                    break;
                case W:
                    camera.getPan().setRotate(camera.getPan().getRotate() - speed);
                    if(camera.getPan().getRotate() <= -90.0){
                        camera.getPan().setRotate(-90.0);
                    }
                    break;
                case S:
                    camera.getPan().setRotate(camera.getPan().getRotate() + speed);
                    if(camera.getPan().getRotate() >= 0.0){
                        camera.getPan().setRotate(0.0);
                    }
                    break;
                case UP:
                    player.moveForward();
                    break;
                case DOWN:
                    player.moveBackward();
                    break;
                case LEFT:
                    player.turnLeft();
                    break;
                case RIGHT:
                    player.turnRight();
                    break;
                case SPACE:
                    player.jump();
                    break;
                case Q:
                    System.out.println(String.format("Camera: %f, %f, %f (%f degrees)", camera.getTranslateX(), camera.getTranslateY(), camera.getTranslateZ(), camera.getRotate()));
                    System.out.println(String.format("Player: %f, %f, %f (%f degrees)", player.getTranslateX(), player.getTranslateY(), player.getTranslateZ(), player.getRotate()));
                    break;
                default:
                    player.displayData();
                    break;
            }
        });
    }
}
