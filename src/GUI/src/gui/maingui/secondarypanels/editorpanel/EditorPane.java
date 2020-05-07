package gui.maingui.secondarypanels.editorpanel;

import gui.extragui.TextLineNumber;
import javax.swing.JEditorPane;
import gui.maingui.utilities.Constants;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.PlainDocument;

public class EditorPane {
    private JEditorPane editorPane;
    private JScrollPane scrollPane ;
    private TextLineNumber tln ;
    private GridBagConstraints gbc_editorPane ;
    
    //Construtor
    //Entrada: Tipo da fonte e tamanho da fonte utilizada dentro do painel de edição
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    //Pós-condição: A Classe é instanciada e o painel de edição é definido
    public EditorPane(String fontType, Integer fontSize, JPanel wrapPanel, KeyListener keyListener){
        this.defineEditorPane(fontType,fontSize,wrapPanel,keyListener);
    }
    
    //Função que define o painel de edição
    //Entrada: Tipo da fonte e tamanho da fonte
    //Retorno: Nenhum
    //Pré-condição: O tipo da fonte deve ser um tipo válido
    /*Pós-condição: O painel é instanciado e configurado, sendo que sua configuração fica guardada em variáveis locais da classe
     * para posteriormente serem recolhidas por quem desejar utilizar o painel que foi gerado
    */
    private void defineEditorPane(String fontType, Integer fontSize, JPanel wrapPanel, KeyListener keyListener){
        this.editorPane = new JEditorPane();
        this.editorPane.setForeground(Constants.getInstance().getEditorPaneFontColor());
        this.editorPane.setBackground(Constants.getInstance().getPaneEditorColor());
        this.editorPane.addKeyListener(keyListener);

        this.gbc_editorPane = new GridBagConstraints();
        this.gbc_editorPane.gridx = 0;
        this.gbc_editorPane.gridy = 1;
        this.gbc_editorPane.fill = GridBagConstraints.BOTH;

        this.editorPane.setCaretColor(Color.WHITE);
        this.editorPane.setFont(new Font(fontType, Font.PLAIN, fontSize));

        //Mudando a quantidade de espaçõs da tecla TAB
        javax.swing.text.Document doc = this.editorPane.getDocument();
        doc.putProperty(PlainDocument.tabSizeAttribute, 2);

        //Incluindo o contador de linhas
        this.scrollPane = new JScrollPane(this.editorPane);
        this.tln = new TextLineNumber(this.editorPane);
        this.scrollPane.setRowHeaderView(this.tln);
        
        wrapPanel.add(this.scrollPane, this.gbc_editorPane);
    }
    
    //Getter do ScrollPane. Adicione esse componente ao JPanel para ter o editor de texto funcional
    public JScrollPane getScrollPane(){
        return this.scrollPane;
    }
    
    //Getter do EditorPane. Adicione novos componentes à essa variável para obter os efeitos desejados
    public JEditorPane getEditorPane(){
        return this.editorPane;
    }
    
    //Getter go gestor de layout para GridBagLayout
    public GridBagConstraints getGbc(){
        return this.gbc_editorPane;
    }
    
    //Setter do editorPane. Utilize esse método para atualizar/trocar o editorPane ou o seu texto
    public void setEditorPane(JEditorPane editorPane){
        this.editorPane = editorPane;
    }
    
}
