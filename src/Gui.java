

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

import static javax.swing.JFrame.*;

public class Gui extends JFrame {
    static MyCells myCellsList[][] = new MyCells[10][10];
    static RivalCells rivalCellsList[][] = new RivalCells[10][10];
    private byte xGrid;
    static MJRadioButton bGroup[] = new MJRadioButton[4];                       //Группа для кнопок выбора типа корабля
    static JLabel lGroup[] = new JLabel[4];
    private JPanel radio;
    public static JButton rotB;
    public static JButton resB;
    public static JButton goB;
    private JTextField textField;
    private JButton send;
    public JScrollPane pane;
    private JPanel chat;
    public JTextArea incomingText;
    private static byte countButton;
    private static byte countLabel;
    String notThisPLZ;
    private Date time;
    public static JTextArea incomingTextPub;

    public Gui(String title){
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000 , 620);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        paintCells(30, 30, 40, 40, false);                                             //20x,20y - координаты отрисовки первой ячейки. 40x,40y - размеры кнопок.
        paintCells(getWidth() - 425, 30, 40, 40, true);
        paintRadio();
        paintChat();
        paintButtons();
        initSystemLookAndFeel();
        setVisible(true);
    }

    public void initSystemLookAndFeel() {
        try {
            String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            // устанавливаем LookAndFeel
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        } catch (UnsupportedLookAndFeelException e) {
        } catch (Exception e) {
        }
    }

    public void paintRadio(){                                                    //Метод для отрисовки всех RadioButton
        radio = new JPanel();                                                    //Создаём панель для RadioButton
        radio.setSize(200,130);
        radio.setLocation(10,450);
        add(radio);
        addRadioButton("Однопалубный", true, (byte) 1, (byte) 4);
        addJLabel("4");
        addRadioButton("Двухпалубный", false, (byte)  2, (byte)  3);
        addJLabel("3");
        addRadioButton("Трёхпалубный", false, (byte)  3, (byte)  2);
        addJLabel("2");
        addRadioButton("Четырёхпалубный", false, (byte)  4, (byte)  1);
        addJLabel("1");
        radio.setBorder(BorderFactory.createEtchedBorder());                    //Делаем рамку на панели с RadioButton
    }

    public void addJLabel(String name){
        MJLabel jLabel = new MJLabel(name);
        lGroup[countLabel] = jLabel;
        countLabel++;
        radio.add(jLabel);
    }
    public  void paintChat(){
        chat = new JPanel();                                                    //Создаём панель для RadioButton
        chat.setSize(545,130);
        chat.setLocation(440,450);
        chat.setLayout(null);
        add(chat);
        incomingText = new JTextArea(1,1);
        incomingText.setLineWrap(true);
        incomingText.setWrapStyleWord(true);
        incomingText.setEditable(false);
        incomingTextPub = incomingText;
        pane = new JScrollPane(incomingText);
        pane.setSize(545,110);
        pane.setLocation(0,0);
        textField = new JTextField(20);
        textField.setSize(444,20);
        textField.setLocation(0,110);
        notThisPLZ = textField.getText();
        send = new JButton("Отправить");
        send.setSize(99,19);
        send.setLocation(445,110);
        textField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){//Если нажата кнопка ENTER
                    keyPress();
                }
            }
        });
        ActionListener listenerSend = event -> {
            keyPress();
        };
        send.addActionListener(listenerSend);
        chat.add(textField);
        chat.add(pane);
        chat.add(send);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }
    public void keyPress() {
        time = java.util.Calendar.getInstance().getTime();
        String message = (textField.getText());
        try {
            if (!Objects.equals(message, notThisPLZ)) {
                message = (new Time(time.getTime())).toString() + " " + Main.login + ": " + textField.getText();
                Inet.sendMessage(message);
                message = (new Time(time.getTime())).toString() + " Вы: " + textField.getText();
                incomingText.append(message + "\n");
                textField.setText("");
            }
        } catch (NullPointerException noConnect){
            message = (new Time(time.getTime())).toString() + " Вы: " + textField.getText() + " (В пустоту, т.к. нет других людей в чате.)";
            incomingText.append(message + "\n");
            textField.setText("");
        }
    }
    public void addRadioButton(String name, boolean selected, byte numCells, byte count) {
        MJRadioButton rButton = new MJRadioButton(name, selected, count);

        bGroup[countButton] = rButton;
        radio.add(rButton);
        ActionListener listener = event -> {
            for(byte i=0; i < 4; i++){
                bGroup[i].setSelected(false);
            }
            rButton.setSelected(true);
            MyCells.typeShip = numCells;
        };
        rButton.addActionListener(listener);
        rButton.id = countButton;
        countButton++;
    }
    private void paintButtons(){
        rotB = new JButton();
        rotB.setIcon(createIcon("/pic/PicRot.png"));
        rotB.addActionListener(e -> MyCells.rotate = !MyCells.rotate);
        rotB.setSize(100,50);
        rotB.setLocation(220, 450);
        add(rotB);
        resB = new JButton();
        resB.addActionListener(e -> resetCells(false));
        resB.setSize(100,50);
        resB.setIcon(createIcon("/pic/PicRes.png"));
        resB.setLocation(330, 450);
        add(resB);
        goB = new JButton();
        goB.addActionListener(e -> {
            if (Main.connected) {
                fillWater();
                Inet.imReady();
                resB.setEnabled(false);
                rotB.setEnabled(false);
                if (RivalCells.youTurn & Main.countReadyPlayers >= 2) {
                    RivalCells.canTurn = true;
                }
                goB.setEnabled(false);
            } else {
                Gui.incomingTextPub.append("Некому сообщать о готовности. Ждём соперника." + "\n");
            }
        });
        goB.setSize(210,69);
        goB.setIcon(createIcon("/pic/PicStart.png"));
        goB.setLocation(220, 510);
        add(goB);
        goB.setEnabled(false);
    }
    private void paintLine(String stringChar, JPanel visual, int sizeX, boolean vertical){
        for(int j = 0; j < 10; j++){
            String aChar = String.valueOf(stringChar.charAt(j));
            JLabel labelChar = new JLabel(aChar);
            int charlocX = sizeX/11 * j;
            Font forChar = new Font("TimesRoman", Font.BOLD, 15);
            labelChar.setFont(forChar);
            labelChar.setVisible(true);
            if (vertical) {
                labelChar.setLocation(charlocX + 35, 0);
            } else {
                labelChar.setLocation(7, charlocX + 30);
            }
            labelChar.setSize(20, 20);
            visual.add(labelChar);
        }
    }
    private void paintCells(int xLoc, int yLoc, int xSiz, int ySiz, boolean rival){            //Очень мудрённый код для отрисовки главных сеток игры. Писал под кофе, не пытаться разобраться))
        int xLocM = xLoc;
        int defY = yLoc;
        byte i = 0;
        while (i < 100){
            int x = i / 10;
            int y = i - (Math.round(i/10)*10);
            if (!rival) {
                MyCells cell = new MyCells();
                myCellsList[x][y] = cell;
                cell.x = (byte)  x;
                cell.y = (byte)  y;
                cell.index = i;
                cell.setLocation(xLocM, yLoc);
                cell.setSize(xSiz, ySiz);
                cell.setBorder(null);
                add(cell);
            }
            if (rival){
                RivalCells cell = new RivalCells();
                rivalCellsList[x][y] = cell;
                cell.x = (byte)  x;
                cell.y = (byte)  y;
                cell.index = i;
                cell.setLocation(xLocM, yLoc);
                cell.setSize(xSiz, ySiz);
                cell.setBorder(null);
                add(cell);
            }
            i++;
            if (xGrid < 9){
                xLocM = xLoc + (xSiz * (xGrid + 1));
                xGrid++;
            } else{
                yLoc = yLoc + ySiz;
                xGrid = 0;
                xLocM = xLoc;
            }
        }
        JPanel visual = new JPanel();                                                    //Создаём панель для RadioButton
        visual.setSize(xSiz * 10 + 30, ySiz * 10 + 30);
        visual.setLocation(xLoc - 20, defY - 20);
        visual.setBorder(BorderFactory.createEtchedBorder());
        visual.setVisible(true);
        visual.setLayout(null);
        paintLine("АБВГДЕЁЖЗИК" , visual, xSiz * 10 + 40 , true);
        paintLine("0123456789" , visual, xSiz * 10 + 40 , false);
        add(visual);
    }
    private void fillWater(){
        MyCells cell;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                cell = myCellsList[i][j];
                if (!cell.thenShip) {
                    cell.setIMG("pic/water1.png");
                    cell.img = "pic/water1.png";
                }
            }
        }
    }
    public static void resetCells(boolean rival){
        MJRadioButton rButton2;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if (!rival){
                    MyCells cell = myCellsList[i][j];
                    cell.setIMG("pic/water1.png");
                    cell.img = "pic/water1.png";
                    cell.canBePut = true;
                    cell.thenShip = false;
                } else {
                    RivalCells cell = rivalCellsList[i][j];
                    cell.setIMG("pic/water1.png");
                    cell.img = "pic/water1.png";
                    cell.alreadyHit = false;
                    RivalCells.youTurn = false;
                }
            }
        }
        for(int t = 0; t != 4; t++){
            rButton2 = bGroup[t];
            rButton2.setDefaultStat();
            MJRadioButton.countDisable = 0;
        }
        bGroup[0].setSelected(true);
        MyCells.typeShip = 1;
        MJRadioButton.ifEnd();
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
