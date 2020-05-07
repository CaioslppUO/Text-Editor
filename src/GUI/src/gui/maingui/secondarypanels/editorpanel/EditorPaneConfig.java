package gui.maingui.secondarypanels.editorpanel;

import javax.swing.JDialog;
import gui.maingui.utilities.Constants;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class EditorPaneConfig {
    private JDialog editorConfigFrame;
    private JSpinner fontSizeSpinner;
    private JComboBox<String> fontTypeList;
    private JButton buttonOk;
    private JLabel labelFontSize;
    private JLabel labelFontType;
    
    //Construtor
    //Entrada: Tipo da fonte e tamanho da fonte
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    //Pós-condição: A janela de configuração do editor é instanciada e configurada
    public EditorPaneConfig(String fontType, Integer fontSize,ListenerEditorPanelConfig listener){
        this.defineEditorPaneConfig(fontType,fontSize,listener);
    }
    
    //Função que instancia e configura a janela de configuração do editor
    //Entrada: Tipo da fonte e tamanho da fonte
    //Retorno: Nenhum
    //Pré-condição: A variável fontType deve possuir um dos possíveis tipos corretos de fonte
    /*Pós-condição: A janela de configuração do editor pane é instanciada e configurada, e suas informações são guardadas
     * em variáveis locais na classe, para depois serem colhidas por quem desejar incluir a janela de configuração
    */
    private void defineEditorPaneConfig(String fontType, Integer fontSize, ListenerEditorPanelConfig listener){
        this.editorConfigFrame = new JDialog();
        this.editorConfigFrame.getContentPane().setBackground(Constants.getInstance().getSideAreasColor());
        this.editorConfigFrame.setSize(300, 300);
        this.editorConfigFrame.setLocationRelativeTo(null);
        this.editorConfigFrame.setTitle("Editor Settings");
        this.editorConfigFrame.getContentPane().setLayout(null);
        this.editorConfigFrame.addKeyListener(listener);
        
        //Botão para salvar as configurações escolhidas
        this.buttonOk = new JButton("OK");
        this.buttonOk.setActionCommand("FontSizeChanged");
        //buttonOk.addActionListener(this);
        this.buttonOk.setSize(55, 30);
        this.buttonOk.setLocation(125, 230);
        this.buttonOk.addActionListener(listener);
        this.editorConfigFrame.getContentPane().add(this.buttonOk);

        this.labelFontSize = new JLabel();
        this.labelFontSize.setText("Font Size");
        this.labelFontSize.setSize(75, 20);
        this.labelFontSize.setLocation(10, 5);
        this.labelFontSize.setForeground(Constants.getInstance().getMenuForeGroundColor());
        this.editorConfigFrame.getContentPane().add(this.labelFontSize);

        //Spinner para escolher o tamanho da fonte
        this.fontSizeSpinner = new JSpinner();
        this.fontSizeSpinner.setValue(fontSize);
        this.fontSizeSpinner.setSize(35, 20);
        this.fontSizeSpinner.setLocation(100, 7);
        this.editorConfigFrame.getContentPane().add(this.fontSizeSpinner);

        this.labelFontType = new JLabel();
        this.labelFontType.setText("Font Type");
        this.labelFontType.setSize(75, 20);
        this.labelFontType.setLocation(10, 40);
        this.labelFontType.setForeground(Constants.getInstance().getMenuForeGroundColor());
        this.editorConfigFrame.getContentPane().add(this.labelFontType);

        //ComboBox para escolher o tipo da fonte
        @SuppressWarnings("deprecation")
        String fontTypes[] = Toolkit.getDefaultToolkit().getFontList();

        this.fontTypeList = new JComboBox(fontTypes);
        this.fontTypeList.setSelectedItem(fontType);
        this.fontTypeList.setSize(120, 20);
        this.fontTypeList.setLocation(100, 42);
        this.editorConfigFrame.getContentPane().add(this.fontTypeList);

        this.editorConfigFrame.setVisible(true);
        this.editorConfigFrame.setFocusable(true);
    }
    
    //Getter do EditorConfigFrame. Inclua esse JDialog onde quiser que o menu apareça
    public JDialog getEditorConfigFrame(){
        return this.editorConfigFrame;
    }
    
    //Getter do botão OK. Utilizado para incluir listeners
    public JButton getOkButton(){
        return this.buttonOk;
    }
    
    //Getter do FontSizeSpinner. Utilizado para ler o valor setado no snipper do tamanho da fonte
    public JSpinner getFontSizeSpinner(){
        return this.fontSizeSpinner;
    }
    
    //Getter do FontTypeList. Utilizado para ler a fonte selecionada na lista de fontes
    public JComboBox<String> getFontTypeList(){
        return this.fontTypeList;
    }
}
