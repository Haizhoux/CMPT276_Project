import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Demo03 {
    public JFrame frame;
    public ImageIcon carIcon;
    public JLabel myLable;
    public JButton jb;

    public Demo03(){
        carIcon = new ImageIcon(this.getClass().getResource("/background.png"));
        myLable = new JLabel(carIcon);
        myLable.setSize(850, 700);

        jb = new JButton("Start");
        jb.setBounds(700,600,100,50);

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new level();
                frame.dispose();
            }
        });


        myLable.add(jb);

        frame = new JFrame("Game");
        frame.add(myLable);
        frame.setSize(850,700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args){
        new Demo03();
    }
}
