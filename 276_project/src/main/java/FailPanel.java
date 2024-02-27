import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class FailPanel extends JPanel {
    ImageIcon icon;
    Image img;
    public FailPanel() {

        icon=new ImageIcon(getClass().getResource("/fail.png"));
        img=icon.getImage();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
    }
}
