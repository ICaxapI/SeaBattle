import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Created by Alex on 24.08.2016.
 */
public class ServerThread extends Thread {
    String ip;
    public int servSocket;
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream;
    public ServerThread(int socket) {
        this.servSocket = socket;
        this.start();
    }
    public void run(){
        try{
            boolean canScan = true;
            DatagramSocket ds = new DatagramSocket(7813);
            System.out.println("Ответ не получен. Создали сервер у себя");
            if (Main.gui == null){
                Main.gui = new Gui("Морской бой");
            }
            Gui.incomingTextPub.append("Ждём соперника..." + "\n");
            while (canScan){
                DatagramPacket pack = new DatagramPacket(new byte[1024], 1024);
                ds.receive(pack);
                System.out.println("Получили какой-то пакет от "+ new String(pack.getData()));
                Socket clientrec = new Socket(new String(pack.getData()), 7814);
                PrintWriter writer = new PrintWriter(clientrec.getOutputStream());
                writer.println(Inet.getCurrentIP(true));
                writer.close();
                System.out.println("Отправили " + Inet.getCurrentIP(true) + " на " + new String(pack.getData()) + ":7814");
                canScan = false;
                ds.close();
                clientrec.close();
                Main.imServer = true;
                RivalCells.youTurn = true;
            }
        }catch(Exception e){
            System.out.println("Порт уже занят!");
            e.printStackTrace();
            System.exit(0);
        }
        try {
            ServerSocket socketListener = new ServerSocket(servSocket);
            while (true) {
                Socket client = null;
                while (client == null) {
                    client = socketListener.accept();
                }
                outputStream = new ObjectOutputStream(client.getOutputStream());
                inputStream = new ObjectInputStream(client.getInputStream());
                Main.connected = true;
                socketListener.close();
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Inet.sendHello();
                //Принимаем сообщение
                while (true) {
                    MessageServer tempMessage = (MessageServer) inputStream.readObject();
                    if (tempMessage.getToClient()) {
                        Inet.takeMessage(tempMessage);
                    }
                }
            }
        } catch (IOException ex) {
            Gui.incomingTextPub.append("Ваш противник отключился от игры!" + "\n");
            Gui.resetCells(true);
            Gui.resetCells(false);
            Main.connected = false;
            new ServerThread(7812);
            JOptionPane.showMessageDialog(null,"Ваш противник отключился от игры!");
            this.stop();
        } catch (ClassNotFoundException ex) {
            System.out.println("Порт уже занят!");
            ex.printStackTrace();
            System.exit(0);
        }
    }

}

