import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    keyHandler key;
    public int honeyScore;
    public int cropScore;
    public int level;
    public int num_visible_crop;
    public int num_visible_honey;
    public int num_visible_trap;
    public boolean game_over = false;

    public Player(GamePanel gp, keyHandler key, int level){
        this.level = level;
        this.gp = gp;
        this.key = key;
        this.honeyScore = 0;
        this.cropScore = 0;
        this.num_visible_crop = gp.num_crop;
        this.num_visible_honey = gp.num_honey;
        this.num_visible_trap = gp.num_trap;
        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue(){
        x = gp.tileSize + 1;
        y = gp.tileSize + 1;
        speed = 5;
        direction = "down";
    }
    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Farmer_7.jpg")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Farmer_8.jpg")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Farmer_1.jpg")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Farmer_2.jpg")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Farmer_3.jpg")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Farmer_4.jpg")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Farmer_5.jpg")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Farmer_6.jpg")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        if(key.upPress)
        {
            direction = "up";
            y -= speed;
            if (checkWall(x, y)) {
                y += speed;
            }
            //System.out.println("x: " + x + ", y: " + y);

        }
        else if(key.downPress){
            direction = "down";
            y += speed;
            if (checkWall(x, y)) {
                y -= speed;
            }
            //System.out.println("x: " + x + ", y: " + y);

        }
        else if(key.leftPress){
            direction = "left";
            x -= speed;
            if (checkWall(x, y)) {
                x += speed;
            }
            //System.out.println("x: " + x + ", y: " + y);

        }
        else if(key.rightPress){
            direction = "right";
            x += speed;
            if (checkWall(x, y)) {
                x -= speed;
            }
            //System.out.println("x: " + x + ", y: " + y);
        }
        if (checkCrop(x, y)) {
            cropScore += level;
            System.out.println("Crop detected! Crop score: " + cropScore);
        }
        if (checkHoney(x, y)) {
            honeyScore += level * 2;
            System.out.println("Honey detected! Honey score: " + honeyScore);
        }
        if (checkTrap(x, y)) {
            honeyScore -= level * 5;
        }
        if (checkLoose()) {
            this.game_over = true;
        }
        if (checkWin()) {
            gp.win = true;
        }
        if (checkBear(x, y)) {
            this.game_over = true;
        }
        spriteCounter++;
        if(spriteCounter > 8) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean checkWall(int x, int y) {
        return x < gp.tileSize || x > gp.screenWidth - 2 * gp.tileSize ||
                y < gp.tileSize || y > gp.screenWidth - 2 * gp.tileSize;
    }

    public boolean checkTrap(int x, int y) {
        int center_x = x + gp.tileSize / 2;
        int center_y = y + gp.tileSize / 2;
        for (int i = 0; i < gp.num_trap; i++) {
            if ((center_x >= gp.traps[i].x && center_x <= (gp.traps[i].x + gp.tileSize))
                    && (center_y >= gp.traps[i].y && center_y <= (gp.traps[i].y + gp.tileSize))
                    && gp.traps[i].visible == 1) {
                gp.traps[i].visible = 0;
                this.num_visible_trap --;
                return true;
            }
        }
        return false;
    }

    public boolean checkCrop(int x, int y) {
        int center_x = x + gp.tileSize / 2;
        int center_y = y + gp.tileSize / 2;
        for (int i = 0; i < gp.num_crop; i++) {
            if ((center_x >= gp.crops[i].x && center_x <= (gp.crops[i].x + gp.tileSize))
                    && (center_y >= gp.crops[i].y && center_y <= (gp.crops[i].y + gp.tileSize))
                    && gp.crops[i].visible == 1) {
                gp.crops[i].visible = 0;
                this.num_visible_crop--;
                return true;
            }
        }
        return false;
    }

    public boolean checkHoney(int x, int y) {
        int center_x = x + gp.tileSize / 2;
        int center_y = y + gp.tileSize / 2;
        for (int i = 0; i < gp.num_honey; i++) {
            if ((center_x >= gp.honeys[i].x && center_x <= (gp.honeys[i].x + gp.tileSize))
                    && (center_y >= gp.honeys[i].y && center_y <= (gp.honeys[i].y + gp.tileSize))
                    && gp.honeys[i].visible == 1) {
                gp.honeys[i].visible = 0;
                this.num_visible_honey--;
                return true;
            }
        }
        return false;
    }

    public boolean checkBear(int x, int y) {
        int center_x = x + gp.tileSize / 2;
        int center_y = y + gp.tileSize / 2;
        for (int i = 0; i < gp.level; i++) {
            if ((center_x >= gp.bears[i].x && center_x <= (gp.bears[i].x + gp.tileSize))
                    && (center_y >= gp.bears[i].y && center_y <= (gp.bears[i].y + gp.tileSize))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLoose(){
        double winScore = 0.8 * (level * gp.num_crop + level * 2 * gp.num_honey);
        int left_honey_score = num_visible_honey * level * 2;
        int left_crop_score = num_visible_crop * level;
        System.out.println(left_honey_score);
        System.out.println(left_crop_score);
        System.out.println(winScore);

        if(honeyScore + cropScore + left_honey_score + left_crop_score < winScore){
            return true;
        }
        return false;
    }

    public boolean checkWin() {
        double winScore = 0.8 * (level * gp.num_crop + level * 2 * gp.num_honey);
        return honeyScore + cropScore > winScore;
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        //System.out.println(x + " " + y);

        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
