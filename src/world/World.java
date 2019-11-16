package world;

import entities.AbstractEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    
    public boolean checkForCollisions(AbstractEntity e){
        boolean collide = false;
        //get the entities position on this map array
        int x = (int) (e.getTranslateX() / Cube.CUBE_SIZE);
        int y = (int) (e.getTranslateY() / Cube.CUBE_SIZE);
        int z = (int) (e.getTranslateZ() / Cube.CUBE_SIZE);
        if(
            x >= 0 && x < width && y >= 0 && y + 1 < height && z >= 0 && z < depth
            && map[x][y + 1][z] != 0
        ){
            //tile below is tangible
            collide = true;
            e.setTranslateY(y * Cube.CUBE_SIZE);
        }
        
        return collide;
    }
    
    //untested
    public World addCubesFromCSV(InputStream stream) throws IOException{
        BufferedReader buff = new BufferedReader(new InputStreamReader(stream));
        String[] line;
        int key;
        int x;
        int y;
        int z;
        while(buff.ready()){
            line = buff.readLine().split(",");
            key = Integer.parseInt(line[0]);
            x = Integer.parseInt(line[1]);
            y = Integer.parseInt(line[2]);
            z = Integer.parseInt(line[3]);
            addCube(key, x, y, z);
        }
        return this;
    }
    
    public Group getGroup(){
        return cubes;
    }
    
    public static World createTest(){
        World w = new World(10, 10, 10);
        w
            .addTemplate(1, new CubeTemplate(new PhongMaterial(Color.RED)))
            .addTemplate(2, new CubeTemplate(new PhongMaterial(Color.GREEN)))
            .addTemplate(3, new CubeTemplate(new PhongMaterial(Color.BLUE)));
        for(int x = 0; x < w.width; x++){
            for(int z = 0; z < w.depth; z++){
                w.addCube((x + z) % 3 + 1, x, w.height - 1, z);
            }
        }
        w
            .addCube(1, 0, 8, 0)
            .addCube(1, 1, 8, 0)
            .addCube(1, 0, 8, 1);
        return w;
    }
}
