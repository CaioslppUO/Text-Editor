package gui.maingui.secondarypanels.openfilespanel;

import gui.extragui.RoundedBorder;
import gui.extragui.RoundedPanel;
import gui.maingui.Constants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class CreateNewFileOpenPanel {

    private RoundedPanel newFilePanel;
    ;
    private Constants constants;
    private Component separator;
    private JButton closeButton;
    private JLabel fName;
    private String lastClickedFilePath;

    //Construtor
    /*Entrada: Caminho para o último arquivo clicado, Map com os arquivos abertos no visualisador, nome do arquivo
     * caminho do arquivo, mouse listener, action listener, classe que gerencia o painel de visualisação
     */
    //Retorno: Nenhum
    //Pré-condição: As variáveis recebidas devem estar devidamente instanciadas e configuradas
    //Pós-condição: A classe é instanciada, o painle é criado e incorporado à interface de visualisar arquivos
    public CreateNewFileOpenPanel(
            String lastClickedFilePath,
            Map<String, RoundedPanel> addedFilesPanel,
            String fileName,
            String filePath,
            MouseListener mouseListener,
            ActionListener actionListener,
            OpenFilesPanel openFiles
    ) {
        this.constants = new Constants();

        this.createNewFileOpenPanel(
                lastClickedFilePath,
                addedFilesPanel,
                fileName,
                filePath,
                mouseListener,
                actionListener,
                openFiles
        );
    }

    //Função que cria um novo painel de arquivo aberto
    //Entrada: Nome do arquivo aberto
    //Retorno: Nenhum
    /* Pré-condição: O painel openFilesPanel, deve existir, pois é ele quem contém o novo painel que será criado. A variável
         * addedFilesPanel deve existir, pois é ela quem guarda quais e quantos painéis de arquivo estão abertos
     */
    //Pós-condição: Um novo painel de arquivo aberto é inserido na interface
    private void createNewFileOpenPanel(
            String lastClickedFilePath,
            Map<String, RoundedPanel> addedFilesPanel,
            String fileName,
            String filePath,
            MouseListener mouseListener,
            ActionListener actionListener,
            OpenFilesPanel openFiles
    ) {
        this.newFilePanel = new RoundedPanel();
        this.newFilePanel.setBackground(this.constants.getSideAreasColor());
        this.newFilePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        this.newFilePanel.setLayout(new BoxLayout(newFilePanel, BoxLayout.LINE_AXIS));
        this.newFilePanel.setName(filePath);
        this.newFilePanel.setToolTipText(filePath);
        this.newFilePanel.addMouseListener(mouseListener);

        this.fName = new JLabel(fileName + "  ");
        this.fName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        this.fName.setForeground(Color.WHITE);
        if (lastClickedFilePath != null) {
            for (Component c : addedFilesPanel.get(lastClickedFilePath).getComponents()) {
                if (c instanceof JLabel) {
                    ((JLabel) c).setForeground(this.constants.getMenuForeGroundColor());
                }
            }
        }
        this.lastClickedFilePath = filePath;
        
        this.closeButton = new JButton("x");
        this.closeButton.setName("closeButton");
        this.closeButton.setBorder(new RoundedBorder(5));
        this.closeButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        this.closeButton.setActionCommand("buttonCloseFilePressed");
        this.closeButton.setBackground(null);
        this.closeButton.addMouseListener(mouseListener);
        this.closeButton.addActionListener(actionListener);
        
        this.newFilePanel.add(fName);
        this.newFilePanel.add(closeButton);

        this.separator = Box.createHorizontalStrut(2);

        openFiles.getOpenFilesPanel().add(newFilePanel);
        openFiles.getOpenFilesPanel().add(separator);
        addedFilesPanel.put(filePath, newFilePanel);
    }

    //Getter do LastClickedFilePath. O novo arquivo é registrado como lastClickedFilePath
    public String getLastClickedFilePath() {
        return this.lastClickedFilePath;
    }
}
