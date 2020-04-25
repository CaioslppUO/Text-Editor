package gui.maingui.secondarypanels.openfiles;

import gui.extragui.RoundedBorder;
import gui.extragui.RoundedPanel;
import gui.maingui.Constants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class CreateNewFileOpenPanel {
    private RoundedPanel newFilePanel;;
    private Constants constants;
    private Component separator;
    private JButton closeButton;
    private JLabel fName;
    private String lastClickedFilePath;
    private Map<String, RoundedPanel> addedFilesPanel;
    
    public CreateNewFileOpenPanel(String lastClickedFilePath,Map<String, RoundedPanel> addedFilesPanel){
        this.constants = new Constants();
        this.lastClickedFilePath = lastClickedFilePath;
        this.addedFilesPanel = addedFilesPanel;
    }
        
    //Função que cria um novo painel de arquivo aberto
    //Entrada: Nome do arquivo aberto
    //Retorno: Nenhum
    /* Pré-condição: O painel openFilesPanel, deve existir, pois é ele quem contém o novo painel que será criado. A variável
         * addedFilesPanel deve existir, pois é ela quem guarda quais e quantos painéis de arquivo estão abertos
     */
    //Pós-condição: Um novo painel de arquivo aberto é inserido na interface
    public void createNewFileOpenPanel(String fileName, String filePath) {
        this.newFilePanel = new RoundedPanel();
        this.newFilePanel.setBackground(this.constants.getSideAreasColor());
        this.newFilePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        this.newFilePanel.setLayout(new BoxLayout(newFilePanel, BoxLayout.LINE_AXIS));
        this.newFilePanel.setName(filePath);

        this.fName = new JLabel(fileName + "  ");
        this.fName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        this.fName.setForeground(Color.WHITE);
        if (this.lastClickedFilePath == null) {
            this.lastClickedFilePath = filePath;
        } else {
            for (Component c : this.addedFilesPanel.get(this.lastClickedFilePath).getComponents()) {
                if (c instanceof JLabel) {
                    ((JLabel) c).setForeground(this.constants.getMenuForeGroundColor());
                }
            }
            this.lastClickedFilePath = filePath;
        }
        this.newFilePanel.add(fName);

        this.closeButton = new JButton("x");
        this.closeButton.setName("closeButton");
        this.closeButton.setBorder(new RoundedBorder(5));
        this.closeButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        this.closeButton.setActionCommand("buttonCloseFilePressed");
        this.closeButton.setBackground(null);

        this.newFilePanel.add(fName);
        this.newFilePanel.add(closeButton);

        this.separator = Box.createHorizontalStrut(2);

        this.addedFilesPanel.put(filePath, newFilePanel);
    }
    
    public Map<String, RoundedPanel> getAddedFilesPanel(){
        return this.addedFilesPanel;
    }
    
    public String getLastClickedFilePath(){
        return this.lastClickedFilePath;
    }
    
    public JButton getCloseButton(){
        return this.closeButton;
    }
    
    public RoundedPanel getNewFilePanel(){
        return this.newFilePanel;
    }
    
    public Component getSeparator(){
        return this.separator;
    }
}
