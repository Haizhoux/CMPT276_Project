import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Trap extends Entity{

    GamePanel gp;
    int visible;

    public Trap(GamePanel gp, int x, int y, int visible) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.visible = visible;
        getTrapImage();
    }

    public void getTrapImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Trap.jpg")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = up1;
        // BufferedImage image2 = up2;
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}