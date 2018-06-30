package boxTest;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import utilities.EasyGroup;
import utilities.Positioner;

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
    
    //need to make two camera EasyGroups to fix up/down panning
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
    	
    	
    	
    	//planks
    	Box[] planks = new Box[24];
    	int ps = 100; //plank size
    	int s = ps / 10;
    	int m = ps / 4 - 2;
    	int l = ps;
    	
    	//more efficient way?
    	for(int i = 0; i < 24; i++){
    		switch(i / 4){
    		case 0:
    			//floor
    			planks[i] = new Box(l, s, m);//24 so there's a gap
        		Positioner.repos(planks[i], 0, 50, (int)(-37.5 + 25 * i));
        		break;
    		case 1:
    			//roof
    			planks[i] = new Box(m, s, l);
    			Positioner.repos(planks[i], (int)(-37.5 + 25 * (i - 4)), -50, 0);
        		break;
    		case 2:
    			//left
    			planks[i] = new Box(s, m, l);
    			Positioner.repos(planks[i], -50, (int)(-37.5 + 25 * (i - 8)), 0);
        		break;
    		case 3:
    			//right
    			planks[i] = new Box(s, l, m);
    			Positioner.repos(planks[i], 50, 0, (int)(-37.5 + 25 * (i - 12)));
        		break;
    		case 4:
    			//back
    			planks[i] = new Box(l, m, s);
    			Positioner.repos(planks[i], 0, (int)(-37.5 + 25 * (i - 16)), 50);
        		break;
    		case 5:
    			//front
    			planks[i] = new Box(m, l, s);
    			Positioner.repos(planks[i], (int)(-37.5 + 25 * (i - 20)), 0, -50);
        		break;
    		}
    		planks[i].setMaterial(wood);
    		g.getChildren().add(planks[i]);
    	}
    	

    	
    	
    	
    	
    	//edges yay it works
    	Box[] edges = new Box[12];
    	for(int i = 0; i < 12; i++){
    		edges[i] = new Box(
    				(i < 4) ? 100 : 10, 
    				(i >= 4 && i < 8) ? 100 : 10, 
    				(i >= 8) ? 100 : 10
    						);
    		edges[i].setMaterial(metal);
    		// first check: should have shift of 0 for any dimension with length 100
    		Positioner.repos(edges[i], 
    				(i < 4) ?           0 : (i == 4 || i == 5 || i == 8 || i == 9) ? 51 : -51, 
    				(i >= 4 && i < 8) ? 0 : (i == 0 || i == 1 || i == 8 || i == 10) ? 51 : -51, 
    				(i >= 8) ?          0 : (i == 0 || i == 2 || i == 4 || i == 6) ? 51 : -51
    				);
    		g.getChildren().add(edges[i]);
    	}
    	
    	
    	
    	// corners
    	Box[] corners = new Box[8];
    	for(int i = 0; i < 8; i++){
    		corners[i] = new Box(25, 25, 25);
    		corners[i].setMaterial(metal);
    		g.getChildren().add(corners[i]);
    	}
    	int cs = 50; //corner spacing
    	
    	for(int i = 0; i < 8; i++){
    		Positioner.repos(
    				corners[i], 
    				(i < 4) ? cs : -cs, 
    				(i >= 2 && i <= 5) ? cs : -cs, 
    				(i % 2 == 0) ? cs : -cs 
    				);
    	}
    	
    	g.translate(x, y, z);
    	g.setVisible(true);
    	world.getChildren().add(g);
    }
    
    private void registerKeys(Scene scene, final Node root){
        int speed = 10;
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
