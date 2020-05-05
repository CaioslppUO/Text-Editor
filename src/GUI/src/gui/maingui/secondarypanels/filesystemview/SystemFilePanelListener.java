package gui.maingui.secondarypanels.filesystemview;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
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
            for (TreeNode n : node.getPath()) {
                aux += n.toString() + "/";
            }
            File check = new File(aux);
            if(!check.isDirectory()){
                check = new File(this.gui.getCurrentFolder());
                this.gui.runOpenFile(check.getParentFile().getAbsolutePath() + "/" + aux);
            }
        }
    }

}