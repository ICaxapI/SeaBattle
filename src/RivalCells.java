import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class RivalCells extends JButton{

    byte x, y;
    byte index;
    boolean alreadyHit = false;
    static boolean canTurn = false;
    static boolean youTurn = false;
    String img = "pic/water1.png";

    public RivalCells() {
        setIMG(img);
        addActionListener(e -> {
            if (youTurn & !alreadyHit & canTurn){
                alreadyHit = true;
                Inet.sendTurn(x , y);
                youTurn = false;
            }
        });
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
                if (youTurn & !alreadyHit & canTurn){
                    setIcon(createIcon("/pic/crossfire.png"));
                }
            }
            public void mouseExited(MouseEvent e) {
                if (youTurn & !alreadyHit & canTurn){
                    setIcon(createIcon("pic/water1.png"));
                }
            }
        });

    }

    public void setIMG(String name){
        ImageIcon temp = createIcon(name);
        setIcon(temp);
        img = name;
    }
    public ImageIcon createIcon(String path) {
        URL imgURL = Gui.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("File not found " + path);
            return null;
        }
    }
}

