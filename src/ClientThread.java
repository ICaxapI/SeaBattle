import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Created by Alex on 23.08.2016.
 */
public class ClientThread extends Thread{
    private int port;
    private boolean saidHello = false;
    private String ip;
    String ipserv;
    static ObjectInputStream inputStream;
    static ObjectOutputStream outputStream;
    Socket serv;
    public ClientThread(String serv) {
        this.start();
        this.ipserv = serv;
    }
    public void run(){
       clientToServer(ipserv);
    }
    public void clientToServer(String ipserv){
        try {
            serv = new Socket(ipserv, 7812);
            Main.gui = new Gui("Морской бой");
            inputStream = new ObjectInputStream(serv.getInputStream());
            outputStream = new ObjectOutputStream(serv.getOutputStream());
            while (true) {
                //Принимаем сообщение
                MessageServer tempMessage = (MessageServer) inputStream.readObject();
                if (Main.connected & !saidHello) {
                    Inet.sendHello();
                    this.saidHello = true;
                }
                //Если это ping
                if (!tempMessage.getToClient()) {
                    if (tempMessage.getItPing()) {
                        outputStream.writeObject(new Ping());
                    }
                    if (tempMessage.getDisconectMe()) {
                        this.stop();
                    }
                } else if (tempMessage.getToClient()) {
                    Inet.takeMessage(tempMessage);
                }
            }
        } catch (IOException ex){
        } catch (ClassNotFoundException ex){
            System.out.println("Порт уже занят!");
            ex.printStackTrace();
            System.exit(0);
        } finally {
            try {
                serv.close();
            }catch (Exception ex) {
            }
            JOptionPane.showMessageDialog(null,"Ваш противник отключился от игры!");
            Gui.incomingTextPub.append("Ваш противник отключился от игры!" + "\n");
            Gui.resetCells(true);
            Gui.resetCells(false);
            Main.connected = false;
            new ServerThread(7812);
            this.stop();
        }
    }
}
