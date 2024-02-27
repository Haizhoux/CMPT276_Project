import org.junit.Assert;
import org.junit.Test;


public class EntityTest {
    int test_level = 3;
    GamePanel gp = new GamePanel(test_level);
    keyHandler key = new keyHandler();
    Player p = new Player(gp, key, test_level);


    @Test
    public void playerSetDefaultValueTest(){
        Assert.assertEquals(gp.tileSize + 1, p.x);
        Assert.assertEquals(gp.tileSize + 1, p.y);
        Assert.assertEquals(5, p.speed);
    }

    @Test
    public void playerUpdateTest() {
        int x = p.x;
        int y = p.y;
        p.key.downPress = true;
        p.update();
        Assert.assertEquals(y + p.speed, p.y);
        p.key.downPress = false;
        p.key.rightPress = true;
        p.update();
        Assert.assertEquals(x + p.speed, p.x);
    }

    @Test
    public void playerCheckWallTest(){
        for (int i = 0; i < gp.walls.length; i++) {
            Assert.assertTrue(p.checkWall(gp.walls[i].x, gp.walls[i].y));
        }

        for (int i = 0; i < gp.crops.length; i++) {
            Assert.assertFalse(p.checkWall(gp.crops[i].x, gp.crops[i].y));
        }
    }

    @Test
    public void playerCheckCropTest(){
        Assert.assertFalse(p.checkCrop(2,3));
    }

    @Test
    public void playerCheckHoneyTest(){
        Assert.assertFalse(p.checkHoney(2,3));
    }

    @Test
    public void playerCheckBearTest(){

        for (int i = 0; i < gp.bears.length; i++) {
            Assert.assertTrue(p.checkBear(gp.bears[i].x, gp.bears[i].y));
            Assert.assertFalse(p.checkBear(gp.bears[i].x + gp.tileSize + 1, gp.bears[i].y + gp.tileSize + 1));
        }

    }

    @Test
    public void playerCheckWinTest(){
        p.honeyScore = 2 * test_level * (int)Math.floor(gp.num_honey * 0.8);
        p.cropScore = test_level * (int)Math.floor(gp.num_crop * 0.8);
        Assert.assertFalse(p.checkWin());
        p.honeyScore = 2 * test_level * (int)Math.floor(gp.num_honey * 0.9);
        p.cropScore = test_level * (int)Math.floor(gp.num_crop * 0.9);
        Assert.assertTrue(p.checkWin());

    }

    @Test
    public void enemyCreationTest() {
        Assert.assertEquals(gp.bears[0].level, test_level);
        Assert.assertTrue(gp.bears[0].current_direction < 4);
        Assert.assertTrue(gp.bears[1].steps_left < 5);
        Assert.assertEquals(2 * test_level, gp.bears[2].speed);
    }

    @Test
    public void enemyUpdateTest() {
        gp.bears[0].current_direction = 0;
        gp.bears[0].steps_left = 4;
        int x = gp.bears[0].x;
        int y = gp.bears[0].y;
        int speed = gp.bears[0].speed;
        gp.bears[0].update();
        Assert.assertEquals(x - speed, gp.bears[0].x);
        Assert.assertEquals(y, gp.bears[0].y);
    }

    @Test
    public void enemyCheckWallTest() {

    }



}
