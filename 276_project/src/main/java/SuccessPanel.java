import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class SuccessPanel extends JPanel {
    ImageIcon icon;
    Image img;
    public SuccessPanel() {

        icon=new ImageIcon(getClass().getResource("/success.png"));
        img=icon.getImage();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
    }
}
