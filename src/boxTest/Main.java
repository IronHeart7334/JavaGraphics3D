package boxTest;

import entities.AbstractEntity;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import props.Crate;
import props.Function;
import utilities.FollowingCamera;
import world.Cube;
import static world.Cube.CUBE_SIZE;
import world.World;

public class Main extends Application{
    private Group root;
    private World world;
    private FollowingCamera camera;
    
    private AbstractEntity player;
    private Group obstacles;
    
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
        obstacles.getChildren().add(new Crate(200, 800, 200, 100));
        
        Function sin = new Function(){
            @Override
            public double f(double x) {
                return CUBE_SIZE * Math.sin(x * 2 * Math.PI / (CUBE_SIZE * 5));
            }
        };
        
        Group integral = sin.getIntegralShellMethod(0, CUBE_SIZE * 5, Math.PI / 4);
        integral.setTranslateX(50);
        integral.setTranslateY(50);
        integral.setTranslateZ(50);
        g.getChildren().add(integral);
        
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
    }
    
    private void registerKeys(Scene scene, final Node root){
        scene.setOnKeyPressed((KeyEvent e) -> {
            switch(e.getCode()){
                case EQUALS:
                    camera.zoomIn();
                    break;
                case MINUS:
                    camera.zoomOut();
                    break;
                case W:
                    camera.tiltUp();
                    break;
                case S:
                    camera.tiltDown();
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
                default:
                    player.displayData();
                    break;
            }
        });
    }
}
