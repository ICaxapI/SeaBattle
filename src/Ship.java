
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 * Created by Alex on 26.08.2016.
 */
public class Ship {
    byte type;
    boolean rotate;
    byte countSunk;
    byte xFirstCell;
    byte yFirstCell;
    public Ship(boolean rotate, byte type, byte xFirstCell, byte yFirstCell){
        this.rotate = rotate;
        this.type = type;
        this.xFirstCell = xFirstCell;
        this.yFirstCell = yFirstCell;
        this.countSunk = 0;
    }
    private static int indexComp(byte x, byte cells) {
        int compIndex = x + cells;
        if (compIndex < 10 & compIndex >= 0) {
            return compIndex;
        } else {
            return 11;
        }
    }
    void sunk(){
        countSunk ++;
        if (countSunk == type) {
            compFillCels(xFirstCell, yFirstCell, rotate, type, false);
            ObjectOutputStream outputStream;
            if(Main.imServer) {
                outputStream = ServerThread.outputStream;
            } else {
                outputStream = ClientThread.outputStream;
            }
            try {
                outputStream.writeObject(new MessageServer(rotate, type, xFirstCell, yFirstCell));
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
    public static void fillMissIfEmpty(MyCells tempCell){
        if (tempCell.img.equals ("pic/water1.png")) {
            Icon ic = tempCell.createIcon("/pic/miss.png");
            tempCell.setIcon(ic);
        }
    }
    public static void fillMissIfEmpty(RivalCells tempCell){
        if (tempCell.img.equals ("pic/water1.png")) {
            Icon ic = tempCell.createIcon("/pic/miss.png");
            tempCell.setIcon(ic);
            tempCell.alreadyHit = true;
        }
    }
    public static void compFillCels(byte xFirstCell, byte yFirstCell, boolean rotate, byte type, boolean rival){
        byte cellsXfill = -1;
        byte cellsYfill = -1;
        while (cellsYfill < type + 1000) {
            if (rotate){
                if (indexComp(xFirstCell, cellsYfill) != 11 & indexComp(yFirstCell, cellsXfill) != 11){
                    if (!rival){
                        MyCells tempCell = Gui.myCellsList[indexComp(xFirstCell, cellsYfill)][indexComp(yFirstCell, cellsXfill)];
                        fillMissIfEmpty(tempCell);
                    } else {
                        RivalCells tempCell = Gui.rivalCellsList[indexComp(xFirstCell, cellsYfill)][indexComp(yFirstCell, cellsXfill)];
                        fillMissIfEmpty(tempCell);
                    }
                }
            }
            else {
                if (indexComp(xFirstCell, cellsXfill) != 11 & indexComp(yFirstCell, cellsYfill) != 11){
                    if (rival){
                        RivalCells tempCell = Gui.rivalCellsList[indexComp(xFirstCell, cellsXfill )][indexComp(yFirstCell, cellsYfill)];
                        fillMissIfEmpty(tempCell);
                    } else {
                        MyCells tempCell = Gui.myCellsList[indexComp(xFirstCell, cellsXfill )][indexComp(yFirstCell, cellsYfill)];
                        fillMissIfEmpty(tempCell);
                    }
                }
            }
            if (cellsXfill != 1) {
                cellsXfill++;
            } else if (cellsYfill != type) {
                cellsXfill = -1;
                cellsYfill++;
            } else {
                cellsXfill = -1;
                cellsYfill = -1;
                break;
            }
        }
    }
}
