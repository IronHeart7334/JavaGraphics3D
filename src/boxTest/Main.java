package boxTest;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

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
        addCrate(200, 200, 200);
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
        camera.setTranslateZ(-400);
        cameraGroup = new EasyGroup();
        cameraGroup.translate(0, 200, -400);
        cameraGroup.rotate(180, 160, 180);
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
    
    private void addCrate(int x, int y, int z){
    	//replace this with new class later
    	// very bad code!
    	EasyGroup g = new EasyGroup();
    	
    	PhongMaterial metal = new PhongMaterial();
    	metal.setDiffuseColor(Color.GREY);
    	metal.setSpecularColor(Color.WHITE);
    	
    	PhongMaterial wood = new PhongMaterial();
    	wood.setDiffuseColor(Color.SADDLEBROWN);
    	
    	// corners
    	Box[] corners = new Box[8];
    	for(int i = 0; i < 8; i++){
    		corners[i] = new Box(25, 25, 25);
    		corners[i].setMaterial(metal);
    		g.getChildren().add(corners[i]);
    	}
    	
    	// x coord
    	// 0, 1, 2, 3
    	for(int i = 0; i < 4; i++){
    		corners[i].setTranslateX(50);
    	}
    	// 4, 5, 6, 7
    	for(int i = 4; i < 8; i++){
    		corners[i].setTranslateX(-50);
    	}
    	
    	// y coord
    	// 0, 1, 6, 7
    	corners[0].setTranslateY(-50);
    	corners[1].setTranslateY(-50);
    	corners[6].setTranslateY(-50);
    	corners[7].setTranslateY(-50);
    	
    	// 2, 3, 4, 5
    	for(int i = 2; i <= 5; i++){
    		corners[i].setTranslateY(50);
    	}
    	
    	// z coord
    	for(int i = 0; i < 8; i+=2){
    		corners[i].setTranslateZ(50);
    	}
    	for(int i = 1; i < 8; i+=2){
    		corners[i].setTranslateZ(-50);
    	}
    	
    	
    	
    	//edges wrong
    	Box[] edges = new Box[12];
    	for(int i = 0; i < 4; i++){
    		edges[i] = new Box(100, 10, 10);
    		edges[i].setMaterial(metal);
    		edges[i].setTranslateX(-50);
    		edges[i].setTranslateY(50 * Math.pow(-1, i));
    		edges[i].setTranslateZ(50 * Math.pow(-1, i + 1));
    		
    		g.getChildren().add(edges[i]);
    	}
    	for(int i = 4; i < 8; i++){
    		edges[i] = new Box(10, 100, 10);
    		edges[i].setMaterial(metal);
    		edges[i].setTranslateX(50 * Math.pow(-1, i));
    		edges[i].setTranslateY(-50);
    		edges[i].setTranslateZ(50 * Math.pow(-1, i + 1));
    		
    		g.getChildren().add(edges[i]);
    	}
    	for(int i = 8; i < 12; i++){
    		edges[i] = new Box(10, 10, 100);
    		edges[i].setMaterial(metal);
    		edges[i].setTranslateX(50 * Math.pow(-1, i));
    		edges[i].setTranslateY(50 * Math.pow(-1, i + 1));
    		edges[i].setTranslateZ(-50);
    		
    		g.getChildren().add(edges[i]);
    	}
    	
    	
    	
    	//planks
    	Box[] planks = new Box[24];
    	
    	//floor
    	for(int i = 0; i < 4; i++){
    		planks[i] = new Box(100, 10, 24);//24 so there's a gap
    		planks[i].setMaterial(wood);
    		planks[i].setTranslateY(50);
    		planks[i].setTranslateZ(-37.5 + 25 * i);
    		g.getChildren().add(planks[i]);
    	}
    	//roof
    	for(int i = 4; i < 8; i++){
    		planks[i] = new Box(24, 10, 100);
    		planks[i].setMaterial(wood);
    		planks[i].setTranslateY(-50);
    		planks[i].setTranslateX(-37.5 + 25 * (i - 4));
    		g.getChildren().add(planks[i]);
    	}
    	//left
    	for(int i = 8; i < 12; i++){
    		planks[i] = new Box(10, 24, 100);
    		planks[i].setMaterial(wood);
    		planks[i].setTranslateX(-50);
    		planks[i].setTranslateY(-37.5 + 25 * (i - 8));
    		g.getChildren().add(planks[i]);
    	}
    	//right
    	for(int i = 12; i < 16; i++){
    		planks[i] = new Box(10, 100, 24);
    		planks[i].setMaterial(wood);
    		planks[i].setTranslateX(50);
    		planks[i].setTranslateZ(-37.5 + 25 * (i - 12));
    		g.getChildren().add(planks[i]);
    	}
    	//back
    	for(int i = 16; i < 20; i++){
    		planks[i] = new Box(100, 24, 10);
    		planks[i].setMaterial(wood);
    		planks[i].setTranslateZ(50);
    		planks[i].setTranslateY(-37.5 + 25 * (i - 16));
    		g.getChildren().add(planks[i]);
    	}
    	//front
    	for(int i = 20; i < 24; i++){
    		planks[i] = new Box(24, 100, 10);
    		planks[i].setMaterial(wood);
    		planks[i].setTranslateZ(-50);
    		planks[i].setTranslateX(-37.5 + 25 * (i - 20));
    		g.getChildren().add(planks[i]);
    	}
    	
    	
    	g.translate(x, y, z);
    	g.setVisible(true);
    	world.getChildren().add(g);
    }
    
    private void registerKeys(Scene scene, final Node root){
        int speed = 5;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e){
                switch(e.getCode()){
                    case W:
                    	//for some reason axis are swapped?
                        cameraGroup.rX(speed);
                        break;
                    case A:
                        cameraGroup.rY(speed);
                        break;
                    case S:
                        cameraGroup.rX(-speed);
                        break;
                    case D:
                        cameraGroup.rY(-speed);
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
                    default:
                    	cameraGroup.logData();
                    	break;
                }
            }
        });
    }
}
