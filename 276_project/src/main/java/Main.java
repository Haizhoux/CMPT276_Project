import javax.swing.JFrame;
public class Main {
    public Main(int i){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setSize(1000, 1000);
        window.setResizable(false);
        window.setTitle("2D Game");

        GamePanel gameP = new GamePanel(i);
        window.add(gameP);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gameP.startGameThread();
    }

    public static void main(String[] args){
        int a1 = 1;
        int a2 = 2;
        int a3 = 3;
        new Main(a1);
        new Main(a2);
        new Main(a3);
    }
}
