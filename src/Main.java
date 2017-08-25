import javax.swing.*;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Main {
    public static byte countMyHit;
    public static byte countRivalHit;
    public static byte countReadyPlayers;
    public static boolean connected = false;
    public static JOptionPane getLogin = new JOptionPane("Введите никнейм");
    public static String login;
    public static Thread tempThread;
    public static Gui guiLink;
    public static boolean imServer = false;
    public static Gui gui;
    public static void main(String[] args) {
        System.out.println(Inet.getCurrentIP(false));
            String voidString = "";
            login = getLogin.showInputDialog(null, "Введите никнейм");
            if (!Objects.equals(login, voidString)) {
                try {
                    boolean s = false;
                    Brodcast sndr = new Brodcast("255.255.255.255", 7813);
                    sleep(1000);
                    if (Brodcast.ipServer == null) {
                        guiLink = gui;
                        sndr.stop();
                        new ServerThread(7812);
                    } else {
                        guiLink = gui;
                        new ClientThread(Brodcast.ipServer);
                        sndr.stop();
                    }
                } catch (InterruptedException ex){
                }
            }
    }
}
