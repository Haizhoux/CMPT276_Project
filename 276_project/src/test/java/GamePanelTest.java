import org.junit.Assert;
import org.junit.Test;

public class GamePanelTest {
    int test_level = 3;
    GamePanel gp = new GamePanel(test_level);

    @Test
    public void gamePanelCreationTest() {
        //TODO: xxxxxxxxxxxxxxxxxx
        int num_crop = 5 + test_level * 5;
        int num_honey = 5 *test_level;
        Assert.assertEquals(num_crop, gp.num_crop);
        Assert.assertEquals(num_honey, gp.num_honey);



    }

    @Test
    public void createWallTest() {
        for (int i = 0; i < gp.walls.length; i++) {
            Assert.assertEquals(0, gp.walls[i].x % gp.tileSize);
            Assert.assertEquals(0, gp.walls[i].y % gp.tileSize);
        }
    }

    @Test
    public void generateCoordinatesTest() {
        int[][] set_coordinates = gp.generateCoordinates(30);
        for (int i = 0; i < set_coordinates.length; i++) {
            for (int j = 0; j < set_coordinates.length; j++) {
                if (i != j) {
                    boolean valid = set_coordinates[i][0] != set_coordinates[j][0] ||
                            set_coordinates[i][1] != set_coordinates[j][1];
                    Assert.assertTrue(valid);
                }
            }
        }
    }

    @Test
    public void containsTest() {
        int[][] test_set_coordinates = new int[10][2];
        for (int i = 0; i < test_set_coordinates.length; i++) {
            test_set_coordinates[i][0] = 50;
            test_set_coordinates[i][1] = i * 20;
        }
        // should inside tile of test_set_coordinates
        // general overlap cases
        Assert.assertTrue(gp.contains(test_set_coordinates, 60, 40));
        Assert.assertTrue(gp.contains(test_set_coordinates, 55, 110));
        Assert.assertTrue(gp.contains(test_set_coordinates, 70, 210));
        Assert.assertFalse(gp.contains(test_set_coordinates, 0, 210));
        Assert.assertFalse(gp.contains(test_set_coordinates, 100, 0));
        // edge cases, touch another entity's edge
        Assert.assertTrue(gp.contains(test_set_coordinates, 50 - gp.tileSize, 0));
        Assert.assertTrue(gp.contains(test_set_coordinates, 50 + gp.tileSize, 0));
        Assert.assertTrue(gp.contains(test_set_coordinates, 50 - gp.tileSize, gp.tileSize));
        Assert.assertTrue(gp.contains(test_set_coordinates, 50, 180 + gp.tileSize));

    }

}
