import NoCanPlace.NoCanPlace;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class MyCells extends JButton {
    byte x, y;
    byte index;
    String img = "pic/water1.png";
    boolean canBePut = true;
    boolean thenShip = false;
    static boolean rotate = true;
    static int typeShip = 1;
    static boolean thisCellNotFit;
    Ship myShip;

    public MyCells() {
        setIMG(img);
        addActionListener(e -> {
            if (typeShip != 0 & Main.connected){
                if (!thisCellNotFit){
                    if (canBePut) {
                        try {
                            boolean checkx = y >= 10 - typeShip + 1;
                            boolean checky = x >= 10 - typeShip + 1;
                            thisCellNotFit = false;
                            if (!checkx & !rotate) {
                                SetShips.areaSet(x, y,  (byte) typeShip, rotate, "pic/water_Gray.png", SetShips.cellsX, SetShips.cellsY, true, false);
                            } else if (!checky & rotate) {
                                SetShips.areaSet(x, y,  (byte) typeShip, rotate, "pic/water_Gray.png", SetShips.cellsX, SetShips.cellsY, true, false);
                            }
                        } catch (NoCanPlace ex) {
                            try {
                                thisCellNotFit = true;
                                SetShips.areaSet(x, y,  (byte) typeShip, rotate, "pic/water1.png", SetShips.cellsX, SetShips.cellsY, false, true);
                            } catch (NoCanPlace ex2) {
                            }
                        }
                    }
                }
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
                if (typeShip != 0){
                    if (canBePut) {
                        try {
                            boolean checkx = y >= 10 - typeShip + 1;
                            boolean checky = x >= 10 - typeShip + 1;
                            thisCellNotFit = false;
                            if (!checkx & !rotate) {
                                SetShips.areaSet(x, y,  (byte) typeShip, rotate, "pic/water_Gray.png", SetShips.cellsX, SetShips.cellsY, false, false);
                            } else if (!checky & rotate) {
                                SetShips.areaSet(x, y,  (byte) typeShip, rotate, "pic/water_Gray.png", SetShips.cellsX, SetShips.cellsY, false, false);
                            }
                        } catch (NoCanPlace ex) {
                            try {
                                thisCellNotFit = true;
                                SetShips.areaSet(x, y,  (byte) typeShip, rotate, "pic/water1.png", SetShips.cellsX, SetShips.cellsY, false, true);
                            } catch (NoCanPlace ex2) {
                            }
                        } finally {
                        }
                    }
                }
            }
            public void mouseExited(MouseEvent e) {
                if (typeShip != 0) {
                    if (canBePut) {
                        try {
                            SetShips.areaSet(x, y,  (byte) typeShip, rotate, "pic/water1.png", SetShips.cellsX, SetShips.cellsY, false, true);
                        } catch (NoCanPlace ex) {
                        }
                    }
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
