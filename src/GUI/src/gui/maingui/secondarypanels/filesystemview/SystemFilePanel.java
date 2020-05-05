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
    public SystemFilePanel(String rootPath, JPanel wrapPanel){
        this.constants = new Constants();
        this.defineFilePanel(rootPath,wrapPanel);
    }

    //Função que define o painel de diretórios abertos
    //Entrada: Pasta de inicio da procura e painel em que serão visualisados os diretórios abertos
    //Retorno: Nenhum
    //Pré-condição: O rootPath e o wrapPanel devem estar devidamente instanciados e serem válidos
    //Pós-condição: O diretório passado é processado, carregado e exposto na tela
    public void defineFilePanel(String rootPath, JPanel wrapPanel){
        this.systemFilesPanel = new JPanel();
        this.systemFilesPanel.setBackground(this.constants.getSideAreasColor());
        this.systemFilesPanel.setLayout(new BorderLayout());

        this.fileTree = new FileTree(rootPath);
        this.fileTree.setBackground(this.constants.getPaneEditorColor());
        this.fileTree.setCellRenderer(new DefaultTreeCellRenderer(){
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

        this.scrollPane = new JScrollPane(this.fileTree);

        this.systemFilesPanel.add(this.scrollPane, BorderLayout.CENTER);

        this.systemFilesPanel.setVisible(true);

        wrapPanel.add(this.systemFilesPanel, BorderLayout.CENTER);
    }
}