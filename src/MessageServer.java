import java.io.Serializable;

/**
 * Created by Alex on 22.08.2016.
 */
public class MessageServer implements Serializable {

    private String ip;
    private int port;
    private boolean youHit;
    private boolean youServer;
    private boolean itPing;
    private boolean itInfoServ;
    private boolean disconectMe;
    private String message = "null";
    private byte turnx = 11;
    private byte turny = 11;
    private boolean toClient;
    private boolean response;
    private boolean imReady;
    private boolean rotate;
    private byte xFirst;
    private byte yFirst;
    private byte typeShip;

    public MessageServer(String ipIn, int portIn){
        this.ip = ipIn;                                                             //Конструктор, передача сведений о сервере
        this.port = portIn;
        this.itInfoServ = true;
    }
    public MessageServer(boolean hit, byte x, byte y){
        this.toClient = true;                                                           //Конструктор, передача РЕЗУЛЬТАТА ХОДА
        this.response = true;
        this.turnx = x;
        this.turny = y;
        this.youHit = hit;
    }
    public MessageServer(){                                                              //Сообщение о готовности к бою
        this.toClient = true;
        this.imReady = true;
    }
    public MessageServer(boolean rotate, byte typeShip, byte xFirst, byte yFirst){
        this.toClient = true;
        this.rotate = rotate;
        this.typeShip = typeShip;
        this.xFirst = xFirst;
        this.yFirst = yFirst;
    }
    public MessageServer(boolean youServerIn, boolean itPingIn){
        if (!itPingIn) {                                                            //Конструктор, Пинга и сервер-ли
            this.youServer = youServerIn;
        } else {
            this.itPing = itPingIn;
        }
    }
    public MessageServer(String messageIN){
        this.message = messageIN;
        this.toClient = true;                                                           //Конструктор, передача СООБЩЕНИЯ
    }
    public MessageServer(byte turnInx, byte turnIny){
        this.turnx = turnInx;
        this.turny = turnIny;                                               //Конструктор, передача ХОДА
        this.toClient = true;
    }
    public MessageServer(boolean disconectMe) {
        this.disconectMe = disconectMe;
    }
    public boolean getRotate() {return this.rotate;}
    public byte getTypeShip() {return this.typeShip;}
    public byte getxFirst() {return this.xFirst;}
    public byte getyFirst() {return this.yFirst;}
    public boolean getToClient(){
        return this.toClient;
    }
    public boolean getReady(){
        return this.imReady;
    }
    public boolean getResponse(){
        return this.response;
    }
    public byte getTurnx(){
        return this.turnx;
    }
    public boolean getYouHit(){
        return this.youHit;
    }
    public byte getTurny(){
        return this.turny;
    }
    public String getMessage(){
        return this.message;
    }
    public boolean getDisconectMe(){
        return this.disconectMe;
    }
    public boolean getInfoServ(){
        return itInfoServ;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public boolean getYouServer() {
        return this.youServer;
    }

    public boolean getItPing(){
        return this.itPing;
    }

}