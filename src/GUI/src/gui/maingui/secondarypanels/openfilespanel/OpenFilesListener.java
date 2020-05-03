package gui.maingui.secondarypanels.openfilespanel;

import gui.Gui;
import gui.extragui.RoundedPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import gui.maingui.Constants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class OpenFilesListener implements ActionListener, MouseListener {

    private Constants constants;

    public OpenFilesListener() {
        this.constants = new Constants();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Gui aux = Gui.getInstance();
        if (null != e.getActionCommand()) {
            switch (e.getActionCommand()) {
                case "buttonCloseFilePressed":
                    aux.closeFile();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Gui aux = Gui.getInstance();
        if (!"closeButton".equals(me.getComponent().getName())) {
            aux.runOpenFile(me.getComponent().getName());
            if (aux.getLastClickedFilePath() == null) {

                for (Component c : ((RoundedPanel) me.getComponent()).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(Color.WHITE);
                    }
                }
                SwingUtilities.updateComponentTreeUI(aux.getOpenFilesPanel().getOpenFilesPanel());
                aux.setLastClickedFilePath(me.getComponent().getName());
            } else {
                for (Component c : ((RoundedPanel) aux.getAddedFilesPanel().get(aux.getLastClickedFilePath())).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(this.constants.getMenuForeGroundColor());
                    }
                }
                SwingUtilities.updateComponentTreeUI(aux.getOpenFilesPanel().getOpenFilesPanel());
                for (Component c : ((RoundedPanel) me.getComponent()).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(Color.WHITE);
                    }
                }
                SwingUtilities.updateComponentTreeUI(aux.getOpenFilesPanel().getOpenFilesPanel());
                aux.setLastClickedFilePath(me.getComponent().getName());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        Gui aux = Gui.getInstance();
        if ("closeButton".equals(me.getComponent().getName())) {
            ((JButton) me.getComponent()).setBackground(this.constants.getButtonCloseEnteredColor());
            ((JButton) me.getComponent()).setForeground(this.constants.getEditorPaneFontColor());
            SwingUtilities.updateComponentTreeUI(aux.getOpenFilesPanel().getOpenFilesPanel());
        }
        if (!"closeButton".equals(me.getComponent().getName())) {
            ((RoundedPanel) me.getComponent()).setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            SwingUtilities.updateComponentTreeUI(aux.getOpenFilesPanel().getOpenFilesPanel());
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        Gui aux = Gui.getInstance();
        if ("closeButton".equals(me.getComponent().getName())) {
            ((JButton) me.getComponent()).setBackground(null);
            ((JButton) me.getComponent()).setForeground(null);
            SwingUtilities.updateComponentTreeUI(aux.getOpenFilesPanel().getOpenFilesPanel());
        }
        if (!"closeButton".equals(me.getComponent().getName())) {
            ((RoundedPanel) me.getComponent()).setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
            SwingUtilities.updateComponentTreeUI(aux.getOpenFilesPanel().getOpenFilesPanel());
        }
    }

}
