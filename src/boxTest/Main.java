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
import world.World;

public class Main extends Application implements EventHandler{
    private Group root;
    private EasyGroup world;
    private PerspectiveCamera camera;
    private EasyGroup boxGroup;
    
    private AbstractEntity player;
    private Group obstacles;
    
    private int camera_offset = 400;
    private final int CAMERA_OFFSET_SPEED = 10;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        world = new EasyGroup();
        obstacles = new Group();
        
        root.getChildren().add(world);
        root.getChildren().add(buildAxis());
        //root.getChildren().add(World.createTest().getGroup());
        
        buildCamera();
        //buildBox();
        
        
        world.getChildren().add(obstacles);
        //obstacles.getChildren().add(new Crate(200, 200, 200, 100));
        
        player = new AbstractEntity(0, 0, 0){};
        player.getChildren().add(camera);
        
        world.getChildren().add(player);
        //buildFloor();
        
        Scene scene = new Scene(root, 500, 500, true);
        scene.setFill(Color.LIGHTBLUE);
        scene.setCamera(camera);
        
        primaryStage.setTitle("Box Test");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        registerKeys(scene, root);
        //scene.setOnMouseMoved(this);
        
        /*
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(!checkForCollide(player)){
                    player.tY(10);
                }
            }
        }.start();*/
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private Group buildAxis(){
        Group axis = new Group();
        Box x = new Box(100, 1, 1);
        Box y = new Box(1, 100, 1);
        Box z = new Box(1, 1, 100);
        x.setTranslateX(50);
        y.setTranslateY(50);
        z.setTranslateZ(50);
        x.setMaterial(new PhongMaterial(Color.RED));
        y.setMaterial(new PhongMaterial(Color.GREEN));
        z.setMaterial(new PhongMaterial(Color.BLUE));
        axis.getChildren().add(x);
        axis.getChildren().add(y);
        axis.getChildren().add(z);
        return axis;
    }
    
    //move this
    public boolean checkForCollide(Node n){
        return obstacles
                .getChildren()
                .filtered(node -> !node.equals(n))
                .filtered(node -> node.getBoundsInParent().intersects(n.getBoundsInParent()))
                .size() != 0;
    }
    
    private void buildCamera(){
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.setTranslateY(-camera_offset * Math.sqrt(2) / 2);
        camera.setTranslateZ(-camera_offset * Math.sqrt(2) / 2);
        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(-45);
    }
    
    //remove this
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
                    System.out.println(camera.getRotate());
                    System.out.println(player.getRotate());
                    break;
                default:
                    player.logData();
                    break;
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
