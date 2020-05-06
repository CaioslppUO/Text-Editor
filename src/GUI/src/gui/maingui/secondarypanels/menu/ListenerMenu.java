package gui.maingui.secondarypanels.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.Gui;

public class ListenerMenu implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Gui aux;
        if (null != e.getActionCommand()) {
            switch (e.getActionCommand()) {
                case "buttonEditorPressed":
                    Gui.getInstance().includeEditorPaneConfig();
                    break;
                case "buttonNewFilePressed":
                    Gui.getInstance().runCreateNewFile();
                    break;
                case "buttonSaveAsPressed":
                    aux = Gui.getInstance();
                    Gui.getInstance().getSaveFile().saveFileAs(aux.getCurrentFolder());
                    break;
                case "buttonOpenFilePressed":
                    Gui.getInstance().runOpenFile();
                    break;
                case "buttonSavePressed":
                    aux = Gui.getInstance();
                    Gui.getInstance().getSaveFile().saveFile(true);
                    break;
                case "buttonRunPressed":
                    Gui.getInstance().runSelectedFile();
                    break;
                case "buttonOpenFolderPressed":
                    Gui.getInstance().runOpenFolder();
                    break;
                case "buttonNewFolderPressed":
                    Gui.getInstance().runCreateNewFolder();
                    break;
                default:
                    break;
            }
        }
    }
}
