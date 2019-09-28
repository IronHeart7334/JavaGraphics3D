package world;

import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

/**
 *
 * @author Matt
 */
public class World {
    private final HashMap<Integer, CubeTemplate> keyToCube;
    private final int[][][] map; //used for collision checking
    private final int width;
    private final int height;
    private final int depth;
    private final Group cubes;
    
    public World(int x, int y, int z){
        width = x;
        height = y;
        depth = z;
        map = new int[x][y][z]; //auto-inits all to 0
        keyToCube = new HashMap<>();
        cubes = new Group();
    }
    
    public World addTemplate(int idx, CubeTemplate template){
        keyToCube.put(idx, template);
        return this;
    }
    
    public World addCube(int cubeKey, int x, int y, int z){
        if(!keyToCube.containsKey(cubeKey)){
            throw new IllegalArgumentException("No cube template with key " + cubeKey);
        }
        cubes.getChildren().add(keyToCube.get(cubeKey).create(x, y, z));
        map[x][y][z] = cubeKey;
        return this;
    }
    
    public Group getGroup(){
        return cubes;
    }
    
    public static World createTest(){
        World w = new World(5, 5, 5);
        PhongMaterial[] mats = new PhongMaterial[]{
            new PhongMaterial(Color.RED),
            new PhongMaterial(Color.BLUE)
        };
        w
            .addTemplate(1, new CubeTemplate(mats[0]))
            .addTemplate(2, new CubeTemplate(mats[1]));
        for(int x = 0; x < 5; x++){
            for(int z = 0; z < 5; z++){
                w.addCube((x + z) % 2 + 1, x, 0, z);
            }
        }
        w
            .addCube(1, 0, 1, 0)
            .addCube(1, 1, 1, 0)
            .addCube(1, 0, 1, 1);
        return w;
    }
}
