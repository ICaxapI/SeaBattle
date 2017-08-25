import javax.swing.*;
import java.io.*;
import java.net.*;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Alex on 19.08.2016.
 */
public class Inet {
    private static Date time;
    public static void imReady(){
        time = java.util.Calendar.getInstance().getTime();
        ObjectOutputStream outputStream;
        String login = Main.login;
        if (Main.imServer) {
            outputStream = ServerThread.outputStream;
            Main.countReadyPlayers ++;
            try {
                outputStream.writeObject(new MessageServer());
            } catch (IOException ex){

            }
            if (Main.countReadyPlayers >= 2){
                String message = (new Time(time.getTime()).toString() + " Оба игрока готовы!");
                RivalCells.canTurn = true;
                try {
                    RivalCells.canTurn = true;
                    Gui.incomingTextPub.append(message);
                    outputStream.writeObject(new MessageServer(message));
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            } else {
                try {
                    outputStream.writeObject(new MessageServer(new Time(time.getTime()).toString() + " " + login + " расставил свои корабли и ждёт вас!"));
                    Gui.incomingTextPub.append(new Time(time.getTime()).toString() + " Мы сообщили о своей готовности." + "\n");
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        } else {
            Main.countReadyPlayers ++;
            if (Main.countReadyPlayers >= 2){
                RivalCells.canTurn = true;
            }
            outputStream = ClientThread.outputStream;
            try {
                outputStream.writeObject(new MessageServer(new Time(time.getTime()).toString() + " " + login + " расставил свои корабли и ждёт вас!"));
                outputStream.writeObject(new MessageServer());
                Gui.incomingTextPub.append(new Time(time.getTime()).toString() + " Мы сообщили о своей готовности." + "\n");
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static void sendTurn(byte x, byte y){
        ObjectOutputStream outputStream;
        if (Main.imServer) {
            outputStream = ServerThread.outputStream;
        } else {
            outputStream = ClientThread.outputStream;
        }
        try {
            outputStream.writeObject(new MessageServer(x, y));
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
    public static void sendMessage(String messageIn){
        ObjectOutputStream outputStream;
        if(Main.imServer) {
            outputStream = ServerThread.outputStream;
        } else {
            outputStream = ClientThread.outputStream;
        }
        try {
            outputStream.writeObject(new MessageServer(messageIn));
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public static void sendHello(){
        ObjectOutputStream outputStream;
        String message = "";
        Date time;
        time = java.util.Calendar.getInstance().getTime();
        if(Main.imServer) {
            outputStream = ServerThread.outputStream;
            message = ((new Time(time.getTime())).toString() + " Вы подключились к игре с " + Main.login);
        } else {
            outputStream = ClientThread.outputStream;
            message = ((new Time(time.getTime())).toString() + " " + Main.login + " подключился  к игре.");
        }
        try {
            outputStream.writeObject(new MessageServer(message));
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public static void takeMessage(MessageServer tempMessage){
        time = java.util.Calendar.getInstance().getTime();
        ObjectOutputStream outputStream;
        if(Main.imServer) {
            outputStream = ServerThread.outputStream;
        } else {
            outputStream = ClientThread.outputStream;
        }
        if (tempMessage.getToClient()){
            if (tempMessage.getTypeShip() > 0){
                Ship.compFillCels(tempMessage.getxFirst(), tempMessage.getyFirst(), tempMessage.getRotate(), tempMessage.getTypeShip(), true);
            }
            if (tempMessage.getReady()) {
                Date time = Calendar.getInstance().getTime();
                Main.countReadyPlayers ++;
                if(Main.countReadyPlayers >= 2) {
                    if(Main.imServer) {
                        String message = (new Time(time.getTime()).toString() + " Оба игрока готовы!" + "\n");
                        try {
                            outputStream.writeObject(new MessageServer(message));
                        } catch (IOException ex){
                            ex.printStackTrace();
                        }
                        Gui.incomingTextPub.append(message);
                        RivalCells.canTurn = true;
                    } else {
                        RivalCells.canTurn = true;
                    }
                }
            }
            if (tempMessage.getTurnx() <= 10 & tempMessage.getTurny() <= 10 & !tempMessage.getResponse()) {
                byte x = tempMessage.getTurnx();
                byte y = tempMessage.getTurny();
                boolean hit = Gui.myCellsList[x][y].thenShip;
                if (!hit) {
                    Icon ic = Gui.myCellsList[x][y].createIcon("/pic/miss.png");
                    Gui.myCellsList[x][y].setIcon(ic);
                    RivalCells.youTurn = true;
                    RivalCells.canTurn = true;
                    try {
                        outputStream.writeObject(new MessageServer(false, x, y));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }


                } else {
                    Icon ic = Gui.myCellsList[x][y].createIcon("/pic/hit.png");
                    Gui.myCellsList[x][y].setIcon(ic);
                    Gui.myCellsList[x][y].img = "/pic/hit.png";
                    Gui.myCellsList[x][y].myShip.sunk();
                    RivalCells.youTurn = false;
                    Main.countRivalHit ++;
                    checkWin();
                    try {
                        outputStream.writeObject(new MessageServer(true, x, y));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }else if (tempMessage.getTurnx() <= 10 & tempMessage.getTurny() <= 10 & tempMessage.getResponse()) {
                byte x = tempMessage.getTurnx();
                byte y = tempMessage.getTurny();
                if (!tempMessage.getYouHit()) {
                    Icon ic = Gui.rivalCellsList[x][y].createIcon("/pic/miss.png");
                    Gui.rivalCellsList[x][y].setIcon(ic);
                    RivalCells.youTurn = false;
                } else {
                    Icon ic = Gui.rivalCellsList[x][y].createIcon("/pic/hit.png");
                    Gui.rivalCellsList[x][y].setIcon(ic);
                    Gui.rivalCellsList[x][y].img = "/pic/hit.png";
                    RivalCells.youTurn = true;
                    Main.countMyHit ++;
                    checkWin();
                }
            }else if (!Objects.equals(tempMessage.getMessage(), "null") & !Objects.equals(tempMessage.getMessage(), null)){
                String message = tempMessage.getMessage();
                Gui.incomingTextPub.append(message + "\n");
            }
        }
    }
    public static void checkWin(){
        if (Main.countMyHit >= 20){
            RivalCells.canTurn = false;
            Gui.resB.setEnabled(true);
            Gui.rotB.setEnabled(true);
            Gui.goB.setEnabled(false);
            Gui.resetCells(false);
            Gui.resetCells(true);
            Main.countReadyPlayers = 0;
            Main.countMyHit = 0;
            Main.countRivalHit = 0;
            JOptionPane.showMessageDialog(null, "Вы выйграли! Наши поздравления!");
        }
        if (Main.countRivalHit >= 20){
            RivalCells.canTurn = false;
            Gui.resB.setEnabled(true);
            Gui.rotB.setEnabled(true);
            Gui.goB.setEnabled(false);
            Gui.resetCells(false);
            Gui.resetCells(true);
            RivalCells.youTurn = true;
            Main.countReadyPlayers = 0;
            Main.countMyHit = 0;
            Main.countRivalHit = 0;
            JOptionPane.showMessageDialog(null, "Вы проиграли. Не переживайте, у Вас обязательно всё получится в следующий раз!");
            String message1 = ("Партия начата заново, на этот раз первым ходить будет проигравший.");
            String message2 = ("Расставляйте свои корабли снова!");
            try {
                ObjectOutputStream outputStream;
                if (Main.imServer){
                    outputStream = ServerThread.outputStream;
                } else {
                    outputStream = ClientThread.outputStream;
                }
                Gui.incomingTextPub.append("\n" + message1 + "\n");
                Gui.incomingTextPub.append(message2 + "\n");
                outputStream.writeObject(new MessageServer(message1));
                outputStream.writeObject(new MessageServer(message2));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static int getNotUsePort(int startport){
        for (int i = startport ; i < 60000; i++){
            try {
                ServerSocket s = new ServerSocket(i);
                s.close();
                return i;
            }
            catch (IOException e) {
                System.out.println("Порт " + i + " уже использован");
            }
        }
        return 900000;
    }
    public static String getCurrentIP(boolean local) {
        String result = null;
        if (local){
            try {
                InetAddress addr = InetAddress.getLocalHost();
                result = addr.getHostAddress();
            } catch (UnknownHostException ex){

            }
        } else {
            try {
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://myip.by/");
                    InputStream inputStream = null;
                    inputStream = url.openStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder allText = new StringBuilder();
                    char[] buff = new char[1024];

                    int count = 0;
                    while ((count = reader.read(buff)) != -1) {
                        allText.append(buff, 0, count);
                    }
                    Integer indStart = allText.indexOf("\">whois ");
                    Integer indEnd = allText.indexOf("</a>", indStart);

                    String ipAddress = allText.substring(indStart + 8, indEnd);
                    if (ipAddress.split("\\.").length == 4) {
                        result = ipAddress;
                    }
                } catch (MalformedURLException ex) {
                   ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (Exception ex) {
                          ex.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static boolean checkInternetConnection() {
        Boolean result = false;
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL("https://www.google.ru/").openConnection();
            con.setRequestMethod("HEAD");
            result = (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }
}
