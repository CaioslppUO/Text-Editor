package gui.maingui.secondarypanels.editorpanel;

import gui.extragui.TextLineNumber;
import javax.swing.JEditorPane;
import gui.maingui.Constants;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import javax.swing.text.PlainDocument;

public class EditorPane {
    private Constants constants;
    private JEditorPane editorPane;
    private JScrollPane scrollPane ;
    private TextLineNumber tln ;
    private GridBagConstraints gbc_editorPane ;
    
    public EditorPane(){
        constants = new Constants();
        this.defineEditorPane();
    }
    
    private void defineEditorPane(){
        this.editorPane = new JEditorPane();
        this.editorPane.setForeground(this.constants.getEditorPaneFontColor());
        this.editorPane.setBackground(this.constants.getPaneEditorColor());

        this.gbc_editorPane = new GridBagConstraints();
        this.gbc_editorPane.gridx = 0;
        this.gbc_editorPane.gridy = 1;
        this.gbc_editorPane.fill = GridBagConstraints.BOTH;

        this.editorPane.setCaretColor(Color.WHITE);
        this.editorPane.setFont(new Font(this.constants.getFontType(), Font.PLAIN, this.constants.getFontSize()));

        //Mudando a quantidade de espaçõs da tecla TAB
        javax.swing.text.Document doc = this.editorPane.getDocument();
        doc.putProperty(PlainDocument.tabSizeAttribute, 2);

        //Incluindo o contador de linhas
        this.scrollPane = new JScrollPane(this.editorPane);
        this.tln = new TextLineNumber(this.editorPane);
        this.scrollPane.setRowHeaderView(this.tln);
    }
    
    public JScrollPane getScrollPane(){
        return this.scrollPane;
    }
    
    public JEditorPane getEditorPane(){
        return this.editorPane;
    }
    
    public GridBagConstraints getGbc(){
        return this.gbc_editorPane;
    }
    
}
