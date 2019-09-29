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
import javafx.scene.transform.Rotate;

import utilities.EasyGroup;
import props.Crate;
import world.Cube;
import world.World;

public class Main extends Application{
    private Group root;
    private World world;
    private PerspectiveCamera camera;
    private EasyGroup boxGroup;
    
    private AbstractEntity player;
    private Group obstacles;
    
    private int camera_offset = 400;
    private final int CAMERA_OFFSET_SPEED = 10;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        world = World.createTest();
        Group g = world.getGroup();
        obstacles = new Group();
        
        root.getChildren().add(g);
        g.getChildren().add(buildAxis(Cube.CUBE_SIZE * 10));
        
        buildCamera();
        //buildBox();
        
        
        g.getChildren().add(obstacles);
        //obstacles.getChildren().add(new Crate(200, 200, 200, 100));
        
        player = new AbstractEntity(0, 0, 0){};
        player.getChildren().add(camera);
        
        g.getChildren().add(player);
        
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
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(Cube.CUBE_SIZE * 20);
        camera.setTranslateY(-camera_offset * Math.sqrt(2) / 2);
        camera.setTranslateZ(-camera_offset * Math.sqrt(2) / 2);
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(-45);
    }
    
    //remove this
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
        int speed = 5;
        scene.setOnKeyPressed((KeyEvent e) -> {
            switch(e.getCode()){
                case EQUALS:
                    camera_offset -= CAMERA_OFFSET_SPEED;
                    if(camera_offset <= player.getSize() * 3){
                        camera_offset = player.getSize() * 3;
                    }
                    camera.setTranslateY(camera_offset * Math.sin(camera.getRotate() * Math.PI / 180));
                    camera.setTranslateZ(-camera_offset * Math.cos(camera.getRotate() * Math.PI / 180));
                    break;
                case MINUS:
                    camera_offset += CAMERA_OFFSET_SPEED;
                    if(camera_offset >= 1000){
                        camera_offset = 1000;
                    }
                    camera.setTranslateY(camera_offset * Math.sin(camera.getRotate() * Math.PI / 180));
                    camera.setTranslateZ(-camera_offset * Math.cos(camera.getRotate() * Math.PI / 180));
                    break;
                case W:
                    camera.setRotate(camera.getRotate() - speed);
                    if(camera.getRotate() <= -90.0){
                        camera.setRotate(-90.0);
                    }
                    camera.setTranslateY(camera_offset * Math.sin(camera.getRotate() * Math.PI / 180));
                    camera.setTranslateZ(-camera_offset * Math.cos(camera.getRotate() * Math.PI / 180));
                    break;
                case S:
                    camera.setRotate(camera.getRotate() + speed);
                    if(camera.getRotate() >= 0.0){
                        camera.setRotate(0.0);
                    }
                    camera.setTranslateY(camera_offset * Math.sin(camera.getRotate() * Math.PI / 180));
                    camera.setTranslateZ(-camera_offset * Math.cos(camera.getRotate() * Math.PI / 180));
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
                    System.out.println(camera.getRotate());
                    System.out.println(player.getRotate());
                    break;
                default:
                    player.logData();
                    break;
            }
        });
    }
}
