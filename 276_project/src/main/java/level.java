import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class level {
    public JFrame jf;

    public level(){
        jf = new JFrame("level");
        jf.setSize(850,700);
        JButton jb1 = new JButton("Level1");
        jb1.setBounds(350,200,100,50);
        JButton jb2 = new JButton("Level2");
        jb1.setBounds(400,200,100,50);
        JButton jb3 = new JButton("Level3");
        jb1.setBounds(550,200,100,50);

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main(1);
                jf.dispose();
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main(2);
                jf.dispose();
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main(3);
                jf.dispose();
            }
        });
        JPanel jp = new JPanel();
        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);
        jf.add(jp);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    public static void main(String[] args){
        new level();
    }
}
