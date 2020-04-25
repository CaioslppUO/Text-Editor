package gui.maingui.secondarypanels.editorpanel;

import javax.swing.JDialog;
import gui.maingui.Constants;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class EditorPaneConfig {
    private JDialog editorConfigFrame;
    private Constants constants;
    private JSpinner fontSizeSpinner;
    private JComboBox<String> fontTypeList;
    private JButton buttonOk;
    private JLabel labelFontSize;
    private JLabel labelFontType;
    
    public EditorPaneConfig(){
        this.constants = new Constants();
        this.defineEditorPaneConfig();
    }
    
    private void defineEditorPaneConfig(){
        this.editorConfigFrame = new JDialog();
        this.editorConfigFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.editorConfigFrame.setSize(300, 300);
        this.editorConfigFrame.setLocationRelativeTo(null);
        this.editorConfigFrame.setTitle("Editor Settings");
        this.editorConfigFrame.getContentPane().setLayout(null);
        //this.editorConfigFrame.addKeyListener(this);
        
        //Botão para salvar as configurações escolhidas
        this.buttonOk = new JButton("OK");
        this.buttonOk.setActionCommand("FontSizeChanged");
        //buttonOk.addActionListener(this);
        this.buttonOk.setSize(55, 30);
        this.buttonOk.setLocation(125, 230);
        this.editorConfigFrame.getContentPane().add(this.buttonOk);

        this.labelFontSize = new JLabel();
        this.labelFontSize.setText("Font Size");
        this.labelFontSize.setSize(75, 20);
        this.labelFontSize.setLocation(10, 5);
        this.labelFontSize.setForeground(this.constants.getMenuForeGroundColor());
        this.editorConfigFrame.getContentPane().add(this.labelFontSize);

        //Spinner para escolher o tamanho da fonte
        this.fontSizeSpinner = new JSpinner();
        this.fontSizeSpinner.setValue(this.constants.getFontSize());
        this.fontSizeSpinner.setSize(35, 20);
        this.fontSizeSpinner.setLocation(100, 7);
        this.editorConfigFrame.getContentPane().add(this.fontSizeSpinner);

        this.labelFontType = new JLabel();
        this.labelFontType.setText("Font Type");
        this.labelFontType.setSize(75, 20);
        this.labelFontType.setLocation(10, 40);
        this.labelFontType.setForeground(this.constants.getMenuForeGroundColor());
        this.editorConfigFrame.getContentPane().add(this.labelFontType);

        //ComboBox para escolher o tipo da fonte
        @SuppressWarnings("deprecation")
        String fontTypes[] = Toolkit.getDefaultToolkit().getFontList();

        this.fontTypeList = new JComboBox(fontTypes);
        this.fontTypeList.setSelectedItem(this.constants.getFontType());
        this.fontTypeList.setSize(120, 20);
        this.fontTypeList.setLocation(100, 42);
        this.editorConfigFrame.getContentPane().add(this.fontTypeList);

        this.editorConfigFrame.setVisible(true);
        this.editorConfigFrame.setFocusable(true);
    }
    
    public JDialog getEditorConfigFrame(){
        return this.editorConfigFrame;
    }
    
    public JButton getOkButton(){
        return this.buttonOk;
    }
    
    public JSpinner getFontSizeSpinner(){
        return this.fontSizeSpinner;
    }
    
    public JComboBox<String> getFontTypeList(){
        return this.fontTypeList;
    }
}
