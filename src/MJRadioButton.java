import javax.swing.*;

/**
 * Класс создан Alex on 13.08.2016.
 */
public class MJRadioButton extends JRadioButton {
    static int countDisable = 0;
    byte id;
    byte count;
    byte countUsed;
    boolean state = true;
    public MJRadioButton(String name, boolean selected, byte temp) {
        super(name, selected);
        count = temp;
    }
    public void used(){
        countUsed ++;
        int temp = Integer.parseInt(Gui.lGroup[id].getText()) - 1;
        Gui.lGroup[id].setText("" + temp);
        if (countUsed == count){
            countDisable ++;
            setEnabled(false);
            setSelected(false);
            state = false;
            for(int i=0; i < 4; i++){
                Gui.bGroup[i].setSelected(false);
            }
            for(int i=0; i < 4; i++){

                if(Gui.bGroup[i].state){
                    Gui.bGroup[i].setSelected(true);
                    MyCells.typeShip = Gui.bGroup[i].id +  1;
                    break;
                }
            }
        }
        ifEnd();
    }
    public static void ifEnd(){
        if (countDisable == 4){
            MyCells.typeShip = 0;
            Gui.goB.setEnabled(true);
        } else {
            Gui.goB.setEnabled(false);
        }
    }
    public void setDefaultStat(){
        Gui.lGroup[id].setText("" + count);
        countUsed = 0;
        state = true;
        setEnabled(true);
        setSelected(false);
    }
}
