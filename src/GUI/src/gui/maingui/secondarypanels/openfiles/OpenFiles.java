package gui.maingui.secondarypanels.openfiles;

import gui.maingui.Constants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class OpenFiles {
    private JPanel openFilesPanel;
    private Constants constants;
    private JScrollPane scrollPane;
    private GridBagConstraints gbc_openFilesPanel;
    
    //Construtor
    public OpenFiles(){
        this.constants = new Constants();
        this.defineOpenFilesPanel();
    }
    
    //Função que define o visualisador de arquivos abertos
    //Entrada: Nenhuma
    //Pré-condição: Nenhuma
    //Pós-condição: O painel é configurado e guardado em variáveis locais
    private void defineOpenFilesPanel(){
        //Configurando o painel que retém os arquivos que estão abertos no momento
        this.openFilesPanel = new JPanel();
        this.openFilesPanel.setBackground(this.constants.getSideAreasColor());
        this.openFilesPanel.setLayout(new BoxLayout(this.openFilesPanel, BoxLayout.LINE_AXIS));
        
        this.scrollPane = new JScrollPane(this.openFilesPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.scrollPane.setPreferredSize(new Dimension(this.scrollPane.getPreferredSize().width, 35));
        this.scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        //Configura o layout manager do openFilesPanel para inserção no painel central
        this.gbc_openFilesPanel = new GridBagConstraints();
        this.gbc_openFilesPanel.fill = GridBagConstraints.BOTH;
        this.gbc_openFilesPanel.insets = new Insets(0, 0, 0, 0);
        this.gbc_openFilesPanel.gridx = 0;
        this.gbc_openFilesPanel.gridy = 0;
    }
    
    //Getter do visualisador
    public JPanel getOpenFilesPanel(){
        return this.openFilesPanel;
    }
    
    //Getter do gestor de layout para GridBagLayout
    public GridBagConstraints getGbc(){
        return this.gbc_openFilesPanel;
    }

}
