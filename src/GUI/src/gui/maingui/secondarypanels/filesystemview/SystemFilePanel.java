package gui.maingui.secondarypanels.filesystemview;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree;

import gui.maingui.Constants;

public class SystemFilePanel {
    private JPanel systemFilesPanel;
    private FileTree fileTree;
    private JScrollPane scrollPane;
    private Constants constants;
    
    //Construtor
    public SystemFilePanel(){
        this.constants = new Constants();
    }

    //Função que define o painel de diretórios abertos
    //Entrada: Pasta de inicio da procura e painel em que serão visualisados os diretórios abertos
    //Retorno: Nenhum
    //Pré-condição: O rootPath e o wrapPanel devem estar devidamente instanciados e serem válidos
    //Pós-condição: O diretório passado é processado, carregado e exposto na tela
    public void defineFilePanel(String rootPath, JPanel wrapPanel, SystemFilePanelListener listener){
        this.systemFilesPanel = new JPanel();
        this.systemFilesPanel.setBackground(this.constants.getSideAreasColor());
        this.systemFilesPanel.setLayout(new BorderLayout());

        this.fileTree = new FileTree(rootPath);
        this.fileTree.setBackground(this.constants.getPaneEditorColor());
        this.fileTree.setCellRenderer(new DefaultTreeCellRenderer(){
            private static final long serialVersionUID = 1L;

            @Override
            public Color getBackgroundNonSelectionColor() {
                return (null);
            }
        
            @Override
            public Color getBackgroundSelectionColor() {
                return Color.WHITE;
            }
        
            @Override
            public Color getBackground() {
                return (null);
            }

            @Override
            public Color getForeground(){
                return constants.getMenuForeGroundColor();
            }
        
            @Override
            public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
                final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
                final DefaultMutableTreeNode node = ((DefaultMutableTreeNode) (value));
                this.setText(value.toString());
                return ret;
            }
        });
        this.fileTree.addMouseListener(listener);

        this.scrollPane = new JScrollPane(this.fileTree);

        this.systemFilesPanel.add(this.scrollPane, BorderLayout.CENTER);

        this.systemFilesPanel.setVisible(true);

        wrapPanel.add(this.systemFilesPanel, BorderLayout.CENTER);
    }

    //Função que faz o update em qual pasta está atualmente aberta
    //Entrada: Pasta de inicio da procura e painel em que serão visualisados os diretórios abertos
    //Retorno: Nenhum
    //Pré-condição: O rootPath e o wrapPanel devem estar devidamente instanciados e serem válidos
    //Pós-condição: O diretório passado é processado, carregado e exposto na tela
    public void updateFolder(String rootPath, JPanel wrapPanel, JPanel lastOpenDirectory, SystemFilePanelListener listener){
        if(lastOpenDirectory != null){
            for(Component c:wrapPanel.getComponents()){
                if(c instanceof JPanel){
                    wrapPanel.remove(c);
                }
            }
        }
        this.defineFilePanel(rootPath, wrapPanel, listener);
    }

    //Getter do painel de diretório aberto
    public JPanel getSystemFilesPanel(){
        return this.systemFilesPanel;
    }
}