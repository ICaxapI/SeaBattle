import NoCanPlace.NoCanPlace;

/**
 * Класс создан Alex on 11.08.2016.
 */
public class SetShips {
    private static MyCells tempCell;
    static byte cellsX = -1;
    static byte cellsY = -1;
    private static int picNum = 0;
    private static Ship linkToNewShip;

    private static int indexComp(byte x, byte cells) {
        int compIndex = x + cells;
        if (compIndex < 10 & compIndex >= 0) {
            return compIndex;
        } else {
            return 11;
        }
    }
    public static void areaSet(byte x, byte y, byte type, boolean rotate, String noCanPlace, byte cellsX, byte cellsY, boolean place, boolean clear) throws NoCanPlace {
        while (cellsY < type + 1000) {
            if (rotate){
                if (indexComp(x, cellsY) != 11 & indexComp(y, cellsX) != 11){
                    tempCell = Gui.myCellsList[indexComp(x, cellsY)][indexComp(y, cellsX)];
                    whatPic(place, type, noCanPlace, cellsY, cellsX, clear, rotate);
                }
            }
            else {
                if (indexComp(x, cellsX) != 11 & indexComp(y, cellsY) != 11){
                    tempCell = Gui.myCellsList[indexComp(x, cellsX )][indexComp(y, cellsY)];
                    whatPic(place, type, noCanPlace, cellsY, cellsX, clear, rotate);
                }
            }
            if (cellsX != 1) {
                cellsX++;
            } else if (cellsY != type) {
                cellsX = -1;
                cellsY++;
            } else {
                break;
            }
        }
    }
    public static void whatPic (boolean place, byte type, String noCanPlace, byte cellsY, byte cellsX, boolean clear, boolean rotate) throws NoCanPlace {
        byte tempx = 0;
        byte tempy = 0;
        if (!clear) {
            if (0 <= cellsY & cellsY <= (type - 1) & cellsX == 0) {
                if (tempCell.canBePut & !tempCell.thenShip & !MyCells.thisCellNotFit) {
                    if (place) {
                        tempCell.thenShip = true;
                    }
                    if (rotate) {
                        if (type == 1) {
                            tempx = tempCell.x;
                            tempy = tempCell.y;
                            tempCell.img = ("pic/vertical/ship1.png");
                            if (place){
                                tempCell.myShip = new Ship(MyCells.rotate, (byte) 1, tempx, tempy);
                                MJRadioButton rB = Gui.bGroup[0];
                                rB.used();
                            }
                        } else if (type == 2) {
                            if (picNum == 0) {
                                tempx = tempCell.x;
                                tempy = tempCell.y;
                                tempCell.img = ("pic/vertical/ship2_1.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = new Ship(MyCells.rotate, (byte) 2, tempx, tempy);
                                    MJRadioButton rB = Gui.bGroup[1];
                                    rB.used();
                                    linkToNewShip = tempCell.myShip;
                                }
                            } else if (picNum == 1) {
                                tempCell.img = ("pic/vertical/ship2_2.png");
                                picNum = 0;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                    linkToNewShip = null;
                                }
                            }
                        } else if (type == 3) {
                            if (picNum == 0) {
                                tempx = tempCell.x;
                                tempy = tempCell.y;
                                tempCell.img = ("pic/vertical/ship3_1.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = new Ship(MyCells.rotate, (byte) 3, tempx, tempy);
                                    MJRadioButton rB = Gui.bGroup[2];
                                    rB.used();
                                    linkToNewShip = tempCell.myShip;
                                }
                            } else if (picNum == 1) {
                                tempCell.img = ("pic/vertical/ship3_2.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                }
                            } else if (picNum == 2) {
                                tempCell.img = ("pic/vertical/ship3_3.png");
                                picNum = 0;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                    linkToNewShip = null;
                                }
                            }
                        } else if (type == 4) {
                            if (picNum == 0) {
                                tempx = tempCell.x;
                                tempy = tempCell.y;
                                tempCell.img = ("pic/vertical/ship4_1.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = new Ship(MyCells.rotate, (byte) 4, tempx, tempy);
                                    MJRadioButton rB = Gui.bGroup[3];
                                    rB.used();
                                    linkToNewShip = tempCell.myShip;
                                }
                            } else if (picNum == 1) {
                                tempCell.img = ("pic/vertical/ship4_2.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                }
                            } else if (picNum == 2) {
                                tempCell.img = ("pic/vertical/ship4_3.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                }
                            } else if (picNum == 3) {
                                tempCell.img = ("pic/vertical/ship4_4.png");
                                picNum = 0;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                    linkToNewShip = null;
                                }
                            }
                        }
                    } else {
                        if (type == 1) {
                            tempx = tempCell.x;
                            tempy = tempCell.y;
                            tempCell.img = ("pic/horizontal/ship1.png");
                            if (place){
                                tempCell.myShip = new Ship(MyCells.rotate, (byte) 1, tempx, tempy);
                                MJRadioButton rB = Gui.bGroup[0];
                                rB.used();
                            }
                        } else if (type == 2) {
                            if (picNum == 0) {
                                tempx = tempCell.x;
                                tempy = tempCell.y;
                                tempCell.img = ("pic/horizontal/ship2_1.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = new Ship(MyCells.rotate, (byte) 2, tempx, tempy);
                                    MJRadioButton rB = Gui.bGroup[1];
                                    rB.used();
                                    linkToNewShip = tempCell.myShip;
                                }
                            } else if (picNum == 1) {
                                tempCell.img = ("pic/horizontal/ship2_2.png");
                                picNum = 0;
                                tempCell.myShip = linkToNewShip;
                                linkToNewShip = null;
                            }
                        } else if (type == 3) {
                            if (picNum == 0) {
                                tempx = tempCell.x;
                                tempy = tempCell.y;
                                tempCell.img = ("pic/horizontal/ship3_1.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = new Ship(MyCells.rotate, (byte) 3, tempx, tempy);
                                    MJRadioButton rB = Gui.bGroup[2];
                                    rB.used();
                                    linkToNewShip = tempCell.myShip;
                                }
                            } else if (picNum == 1) {
                                tempCell.img = ("pic/horizontal/ship3_2.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                }
                            } else if (picNum == 2) {
                                tempCell.img = ("pic/horizontal/ship3_3.png");
                                picNum = 0;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                    linkToNewShip = null;
                                }
                            }
                        } else if (type == 4) {
                            if (picNum == 0) {
                                tempx = tempCell.x;
                                tempy = tempCell.y;
                                tempCell.img = ("pic/horizontal/ship4_1.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = new Ship(MyCells.rotate, (byte) 4, tempx, tempy);
                                    MJRadioButton rB = Gui.bGroup[3];
                                    rB.used();
                                    linkToNewShip = tempCell.myShip;
                                }
                            } else if (picNum == 1) {
                                tempCell.img = ("pic/horizontal/ship4_2.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                }
                            } else if (picNum == 2) {
                                tempCell.img = ("pic/horizontal/ship4_3.png");
                                picNum++;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                }
                            } else if (picNum == 3) {
                                tempCell.img = ("pic/horizontal/ship4_4.png");
                                picNum = 0;
                                if (place){
                                    tempCell.myShip = linkToNewShip;
                                    linkToNewShip = null;
                                }
                            }
                        }
                    }
                } else {
                    picNum = 0;
                    throw new NoCanPlace();
                }
            } else if (tempCell.canBePut){
                tempCell.img = noCanPlace;
            }
            tempCell.setIMG(tempCell.img);
        } else if (tempCell.canBePut){
            tempCell.setIMG(noCanPlace);
        }
        if (place) {
            tempCell.canBePut = false;

        }
    }
}


