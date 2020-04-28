package gui.maingui.secondarypanels.editorpanel;

import gui.Gui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ListenerEditorPanel implements KeyListener {

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Gui aux = Gui.getInstance();
        //Executa um programa em python ou em c
        if (aux.getEditorPane().getEditorPane() != null && e.isControlDown() && e.getKeyChar() != 'r' && e.getKeyCode() == 82) {
            aux.runSelectedFile();
        }
        
        //Salva o arquivo aberto ao apertar ctrl+s
        if (aux.getEditorPane().getEditorPane() != null && e.isShiftDown() == false && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
            aux.getSaveFile().saveFile(true, aux.getCurrentFile(), aux.getEditorPane().getEditorPane().getText());
        }

        //Salva como o arquivo ao apertar ctrl+shift+s
        if (aux.getEditorPane().getEditorPane() != null && e.isShiftDown() && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
            aux.getSaveFile().saveFileAs(aux.getCurrentFile(), aux.getCurrentFolder(), aux.getEditorPane().getEditorPane().getText());
        }
    }

}
