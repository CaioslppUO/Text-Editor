package gui.maingui.secondarypanels.filesystemview;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import gui.Gui;

public class SystemFilePanelListener extends MouseAdapter {

    private Gui gui;

    public SystemFilePanelListener() {
        
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            this.gui = Gui.getInstance();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) ((JTree) e.getSource())
                    .getLastSelectedPathComponent();
            String aux = "";
            int i;
            for(i = 1; i < node.getPath().length; i++){
                aux += node.getPath()[i].toString();
                if(i != node.getPath().length -1 )
                    aux += "/";
            }
            File check = new File(this.gui.getCurrentFolder() + "/" + aux);
            if(!check.isDirectory()){
                this.gui.runOpenFile(check.getAbsolutePath());
            }
        }
    }

}