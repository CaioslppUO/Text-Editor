package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import gui.Gui;

public class ListenerGui implements KeyListener {
    
    public ListenerGui() {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Abre um arquivo ao apertar ctrl+o
        if (e.isControlDown() && e.getKeyChar() != 'o' && e.getKeyCode() == 79) {
            Gui.getInstance().runOpenFile();
        }
        
        //Cria um novo arquivo ao apertar ctr+n
        if (e.isControlDown() && e.getKeyChar() != 'n' && e.getKeyCode() == 78) {
            Gui.getInstance().runCreateNewFile();
        }
    }
}
