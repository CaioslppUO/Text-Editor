package gui.maingui.secondarypanels.editorpanel;

import gui.Gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ListenerEditorPanelConfig implements ActionListener, KeyListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Gui aux = Gui.getInstance();
        if (null != e.getActionCommand()) {
            switch (e.getActionCommand()) {
                case "FontSizeChanged":
                    aux.getEditorPaneConfig().getEditorConfigFrame().dispose();
                    aux.updateFont();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Gui aux = Gui.getInstance();
        //Resposta do botão OK da tela de configuração do editor
        if (aux.getEditorPaneConfig() != null && e.getKeyCode() == 27) {
            aux.getEditorPaneConfig().getEditorConfigFrame().dispose();
            aux.updateFont();
        }
    }

}
