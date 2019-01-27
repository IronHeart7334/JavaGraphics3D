package props;

import utilities.EasyGroup;
import utilities.Positioner;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class Crate extends EasyGroup {

    private static PhongMaterial metal = new PhongMaterial(Color.GREY);
    private static PhongMaterial wood = new PhongMaterial(Color.SADDLEBROWN);

    private int size;

    public Crate(int x, int y, int z, int s) {
        super();
        size = s;
        metal.setSpecularColor(Color.WHITE);

        //planks
        Box[] planks = new Box[24];
        int ps = 100; //plank size
        int small = ps / 10;
        int med = ps / 4 - 2;
        int large = ps;
        // better way?
        for (int i = 0; i < 24; i++) {
            switch (i / 4) {
                case 0:
                    //floor
                    planks[i] = new Box(large, small, med);
                    Positioner.repos(planks[i], 0, 50, (int) (-37.5 + 25 * i));
                    break;
                case 1:
                    //roof
                    planks[i] = new Box(med, small, large);
                    Positioner.repos(planks[i], (int) (-37.5 + 25 * (i - 4)), -50, 0);
                    break;
                case 2:
                    //left
                    planks[i] = new Box(small, med, large);
                    Positioner.repos(planks[i], -50, (int) (-37.5 + 25 * (i - 8)), 0);
                    break;
                case 3:
                    //right
                    planks[i] = new Box(small, large, med);
                    Positioner.repos(planks[i], 50, 0, (int) (-37.5 + 25 * (i - 12)));
                    break;
                case 4:
                    //back
                    planks[i] = new Box(large, med, small);
                    Positioner.repos(planks[i], 0, (int) (-37.5 + 25 * (i - 16)), 50);
                    break;
                case 5:
                    //front
                    planks[i] = new Box(med, large, small);
                    Positioner.repos(planks[i], (int) (-37.5 + 25 * (i - 20)), 0, -50);
                    break;
            }
            planks[i].setMaterial(wood);
            getChildren().add(planks[i]);
        }

        //edges
        Box[] edges = new Box[12];
        for (int i = 0; i < 12; i++) {
            edges[i] = new Box(
                    (i < 4) ? 100 : 10,
                    (i >= 4 && i < 8) ? 100 : 10,
                    (i >= 8) ? 100 : 10
            );
            edges[i].setMaterial(metal);
            // first check: should have shift of 0 for any dimension with length 100
            Positioner.repos(edges[i],
                    (i < 4) ? 0 : (i == 4 || i == 5 || i == 8 || i == 9) ? 51 : -51,
                    (i >= 4 && i < 8) ? 0 : (i == 0 || i == 1 || i == 8 || i == 10) ? 51 : -51,
                    (i >= 8) ? 0 : (i == 0 || i == 2 || i == 4 || i == 6) ? 51 : -51
            );
            getChildren().add(edges[i]);
        }

        //corners
        Box[] corners = new Box[8];
        for (int i = 0; i < 8; i++) {
            corners[i] = new Box(25, 25, 25);
            corners[i].setMaterial(metal);
            getChildren().add(corners[i]);
        }
        int cs = 50; //corner spacing

        for (int i = 0; i < 8; i++) {
            Positioner.repos(
                    corners[i],
                    (i < 4) ? cs : -cs,
                    (i >= 2 && i <= 5) ? cs : -cs,
                    (i % 2 == 0) ? cs : -cs
            );
        }
        translate(x, y, z);
        setVisible(true);
    }

    //replace sideNum with enum later
    private void generateSide(int sideNum) {
        EasyGroup sideGroup = new EasyGroup();
        int plankCount = 4; // makes it easier to change
        int s = size / 10;
        int m = (int) (size / (plankCount * 1.1));
        int l = size;

        for (int i = 0; i < plankCount; i++) {
            Box b = new Box(l, m, s);
            Positioner.repos(b, l, (-l / 2) + (m * i), s);
            b.setMaterial(wood);
            sideGroup.getChildren().add(b);
        }
        switch (sideNum) {
            case 0:
                // bottom
                sideGroup.rotate(0, -90, 0);
                break;
            case 1:
                // top
                sideGroup.rotate(0, 0, 90);
                break;
            case 2:
                //left
                sideGroup.rotate(90, 0, 0);
                break;
            case 3:
                //right
                sideGroup.rotate(-90, 0, 0);
                break;
            case 4:
                //back
                sideGroup.rotate(180, 0, 0);
                break;
            default:
            //front, no changes needed
            }
    }
}
