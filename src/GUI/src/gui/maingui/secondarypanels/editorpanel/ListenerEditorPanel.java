package gui.maingui.secondarypanels.editorpanel;

import gui.Gui;
import gui.maingui.utilities.savefile.SaveFile;
import gui.maingui.entities.gFile;

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
        // Executa um programa em python ou em c
        if (aux.getEditorPane().getEditorPane().isEnabled() && e.isControlDown() && e.getKeyChar() != 'r'
                && e.getKeyCode() == 82) {
            aux.runSelectedFile();
        }

        // Salva o arquivo aberto ao apertar ctrl+s
        if (aux.getEditorPane().getEditorPane().isEnabled() && e.isShiftDown() == false && e.isControlDown()
                && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
            SaveFile.getInstance().saveFile(true);
        }

        // Salva como o arquivo ao apertar ctrl+shift+s
        if (aux.getEditorPane().getEditorPane().isEnabled() && e.isShiftDown() && e.isControlDown() && e.getKeyChar() != 's'
                && e.getKeyCode() == 83) {
            SaveFile.getInstance().saveFileAs(aux.getCurrentFolder());
        }

        // Abre um arquivo ao apertar ctrl+o
        if (aux.getEditorPane().getEditorPane().isEnabled() && e.isControlDown() && e.getKeyChar() != 'o' && e.getKeyCode() == 79) {
            Gui.getInstance().runOpenFile();
        }

        // Cria um novo arquivo ao apertar ctr+n
        if (aux.getEditorPane().getEditorPane().isEnabled() && e.isControlDown() && e.getKeyChar() != 'n' && e.getKeyCode() == 78) {
            Gui.getInstance().runCreateNewFile();
        }

        if(aux.getEditorPane().getEditorPane().isEnabled()){
            gFile.getInstance().checkModification();
        }
    }

}
