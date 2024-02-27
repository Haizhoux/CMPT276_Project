import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable {
    private static final int FPS = 60;
    final int originalTileSize = 15;

    // default screen parameters
    final int scale = 3;
    public int screen_size_scale = 10;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public int screenWidth = tileSize * screen_size_scale;
    public int screenHeight = tileSize * screen_size_scale;
    public int numWalls = 4 * screenWidth / tileSize - 4;


    public int num_crop = 0;
    public int num_honey = 0;
    public int num_trap = 0;
    public int level = 1;

    TileManager tileM = new TileManager(this);
    keyHandler key = new keyHandler();
    Thread gameThread;
    private boolean exit = false;
    public boolean win = false;

    // game entities
    Player player;
    Crop[] crops;
    Honey[] honeys;
    Wall[] walls;
    Enemy[] bears;
    Trap[] traps;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public GamePanel(int level) {
        this.level = level;
        this.num_crop = 5 + level * 5;
        this.num_honey = 5 * level;
        if (this.level != 1){
            this.num_trap = 2 * this.level;
        }
        else{
            this.num_trap = 1;
        }

        this.screenWidth = tileSize * screen_size_scale * level;
        this.screenHeight = tileSize * screen_size_scale * level;
        this.numWalls = 4 * screenWidth / tileSize - 4;
        this.crops = new Crop[num_crop];
        this.honeys = new Honey[num_honey];
        this.bears = new Enemy[this.level];
        this.walls = new Wall[numWalls];
        this.traps = new Trap[num_trap];
        this.player = new Player(this, key, level);

        //System.out.println("width: " + screenWidth);
        //System.out.println("numwall: " + numWalls);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        //System.out.println(screenWidth + "\t" + screenHeight);
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);

        // create crops and honeys
        generateCropHoneyBearTrap();
        // create walls
        createWall();
    }

    public void createWall() {
        int i = 0;
        // North side
        for (; i < screenWidth / tileSize; i++) {
            walls[i] = new Wall(i * tileSize, 0, tileSize);
            //System.out.println("create wall " + i);
        }
        // South side
        for (int j = 0; j < screenWidth / tileSize; j++) {
            walls[i] = new Wall(j * tileSize, screenWidth - tileSize, tileSize);
            //System.out.println("create wall " + i);
            i++;
        }
        // West side
        for (int j = 1; j < screenHeight / tileSize - 1; j++) {
            walls[i] = new Wall(0, j * tileSize, tileSize);
            i++;
        }
        // East side
        for (int j = 1; j < screenHeight / tileSize - 1; j++) {
            walls[i] = new Wall(screenWidth - tileSize, j * tileSize, tileSize);
            i++;
        }
    }

    public boolean contains(int[][] coordinates, int x, int y) {
        for (int[] coordinate : coordinates) {
            // check left upper corner
            if ((x >= coordinate[0] && x <= coordinate[0] + tileSize)
                    && (y >= coordinate[1] && y <= coordinate[1] + tileSize)) {
                return true;
            }
            // check left bottom corner
            if ((x >= coordinate[0] && x <= coordinate[0] + tileSize)
                    && ((y + tileSize) >= coordinate[1] && (y + tileSize) <= coordinate[1] + tileSize)) {
                return true;
            }
            // check right upper corner
            if (((x + tileSize) >= coordinate[0] && (x + tileSize) <= coordinate[0] + tileSize)
                    && (y >= coordinate[1] && y <= coordinate[1] + tileSize)) {
                return true;
            }
            // check right bottom corner
            if (((x + tileSize) >= coordinate[0] && (x + tileSize) <= coordinate[0] + tileSize)
                    && ((y + tileSize) >= coordinate[1] && (y + tileSize) <= coordinate[1] + tileSize)) {
                return true;
            }
        }
        return false;
    }

    public int[][] generateCoordinates(int size) {
        Random rn = new Random();
        int[][] set_coordinates = new int[size][2];
        int i = 0;
        while (i < size) {
            int x = tileSize + rn.nextInt(screenWidth - 3 * tileSize);
            int y = tileSize + rn.nextInt(screenWidth - 3 * tileSize);
            if (!contains(set_coordinates, x, y)) {
                set_coordinates[i][0] = x;
                set_coordinates[i][1] = y;
                i++;
            }
        }
        return set_coordinates;
    }

    public void generateCropHoneyBearTrap() {
        int[][] set_coordinates = generateCoordinates(num_crop + num_honey + level + num_trap);
        int i = 0;
        for (; i < num_crop; i++) {
            //System.out.println(set_coordinates[i][0] + "\t" + set_coordinates[i][1]);
            crops[i] = new Crop(set_coordinates[i][0], set_coordinates[i][1], 1, tileSize);
        }
        for (int j = 0; j < num_honey; j++) {
            //System.out.println(set_coordinates[i][0] + "\t" + set_coordinates[i][1]);
            honeys[j] = new Honey(set_coordinates[i][0], set_coordinates[i][1], 1, tileSize);
            i++;
        }
        for (int j = 0; j < level; j++) {
            bears[j] = new Enemy(this, this.level, set_coordinates[i][0], set_coordinates[i][1]);
            i++;
        }
        for (int j = 0; j < num_trap; j++) {
            traps[j] = new Trap(this, set_coordinates[i][0], set_coordinates[i][1], 1);
            i++;
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (!exit) {

            update();
            repaint();
            if (player.game_over || this.win) {
                showResult(win);
                stop();
            }
            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        exit = true;
    }

    public void update() {
        player.update();
        for (int i = 0; i < level; i++) {
            bears[i].update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        // draw crops and honeys
        for (int i = 0; i < num_crop; i++) {
            if (crops[i].visible == 1) {
                crops[i].draw(g2);
            }
        }
        for (int i = 0; i < num_honey; i++) {
            if (honeys[i].visible == 1) {
                honeys[i].draw(g2);
            }
        }
        // Draw trap
        for (int i = 0; i < num_trap; i++) {
            if (traps[i].visible == 1) {
                traps[i].draw(g2);
            }
        }
        // draw wall
        for (int i = 0; i < numWalls; i++) {
            walls[i].draw(g2);
        }
        // draw player and enemy
        player.draw(g2);
        for (int i = 0; i < level; i++) {
            bears[i].draw(g2);
        }
        g2.dispose();
    }

    public void showResult(boolean win) {
        int total_score = player.cropScore + player.honeyScore;
        if (win) {
            JFrame jf = new JFrame();

            JPanel jp = new SuccessPanel();
            JButton jb1 = new JButton("Try again!");
            JButton jb2 = new JButton("Exit");
            jb1.setBounds(250,400,70,30);
            jb2.setBounds(400,300,70,30);
            jb1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Demo03();
                    jf.dispose();
                }
            });
            jb2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            jp.add(jb1);
            jp.add(jb2);
            jf.add(jp);
            jf.setSize(750,600);
            jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
            jf.setVisible(true);

            System.out.println("Congratulations, you win!!!");
            System.out.println("You have reached more than 80% of the total score!!");
        } else {
            JFrame jf = new JFrame();

            JPanel jp = new FailPanel();
            JButton jb1 = new JButton("Yes");
            JButton jb2 = new JButton("No");
            jb1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Demo03();
                    jf.dispose();
                }
            });
            jb2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            jb1.setBounds(250,400,70,30);
            jb2.setBounds(400,300,70,30);
            jp.add(jb1);
            jp.add(jb2);
            jf.add(jp);
            jf.setSize(750,600);
            jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
            jf.setVisible(true);
            System.out.println("Game Over!");
        }
        System.out.println("Your score is: " + total_score);
    }
}
