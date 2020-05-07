package gui.maingui.interfacegenerator;

import gui.extragui.RoundedBorder;
import gui.extragui.RoundedPanel;
import gui.maingui.utilities.Constants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class fFilePanelGenerator {
    // Construtor
    private fFilePanelGenerator() {
    }

    // Função que cria um novo painel de arquivo aberto
    public static RoundedPanel createNewFileOpenPanel(String fileName, String filePath, MouseListener mouseListener,
            ActionListener actionListener) {
        RoundedPanel newFilePanel = new RoundedPanel();
        newFilePanel.setBackground(Constants.getInstance().getSideAreasColor());
        newFilePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        newFilePanel.setLayout(new BoxLayout(newFilePanel, BoxLayout.LINE_AXIS));
        newFilePanel.setName(filePath);
        newFilePanel.setToolTipText(filePath);
        newFilePanel.addMouseListener(mouseListener);

        JLabel fName = new JLabel(fileName + "  ");
        fName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        fName.setForeground(Color.WHITE);

        JButton closeButton = new JButton("x");
        closeButton.setName("closeButton:" + filePath);
        closeButton.setBorder(new RoundedBorder(5));
        closeButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        closeButton.setActionCommand("buttonCloseFilePressed");
        closeButton.setBackground(null);
        closeButton.addMouseListener(mouseListener);
        closeButton.addActionListener(actionListener);

        newFilePanel.add(fName);
        newFilePanel.add(closeButton);

        return newFilePanel;
    }
}
