import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Enemy extends Entity{

    GamePanel gp;
    public int level;
    public int steps_left;
    public int current_direction;

    public Enemy(GamePanel gp, int level, int x, int y) {
        this.level = level;
        this.gp = gp;
        Random r = new Random();
        this.x = x;
        this.y = y;
        this.current_direction = r.nextInt(4);
        this.steps_left = r.nextInt(5);
        getEnemyImage();
        speed = 2 * level;
    }

    public void getEnemyImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Bear_3.jpg")));
            // up2 = ImageIO.read(getClass().getResourceAsStream("Bear_4"));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update(){
        // check if there's steps left for current direction
        if (steps_left <= 0) {
            Random r = new Random();
            current_direction = r.nextInt(4);
            steps_left = r.nextInt(10);
        }

        //System.out.println(current_direction);
        //System.out.println(steps_left);
        switch (current_direction) {
            case(0):
                // left
                x -= speed;
                steps_left--;
                if (checkWall(x, y)) {
                    x += speed;
                }
                break;
            case(1):
                // up
                y -= speed;
                steps_left--;
                if (checkWall(x, y)) {
                    y += speed;
                }
                break;
            case(2):
                // right
                x += speed;
                steps_left--;
                if (checkWall(x, y)) {
                    x -= speed;
                }
                break;
            case(3):
                // down
                y += speed;
                steps_left--;
                if (checkWall(x, y)) {
                    y -= speed;
                }
        }
    }

    public boolean checkWall(int x, int y) {
        return x < gp.tileSize || x > gp.screenWidth - 2 * gp.tileSize ||
                y < gp.tileSize || y > gp.screenWidth - 2 * gp.tileSize;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = up1;
        // BufferedImage image2 = up2;
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}