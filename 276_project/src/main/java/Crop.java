import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Crop extends Entity {

    int visible;
    int tileSize;

    public Crop(int x, int y, int visible, int tileSize) {
        this.x = x;
        this.y = y;
        this.visible = visible;
        this.tileSize = tileSize;
        getCropImage();
    }

    public void getCropImage() {
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Corn.jpg")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = up1;
        g2.drawImage(image, x, y, tileSize, tileSize, null);
    }
}
