package gui.maingui.secondarypanels.filesystemview;

import java.io.File;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class FileTree extends JTree{

    private static final long serialVersionUID = 1L;

    // Construtor
    public FileTree(String rootPath){
        super(scan(new File(rootPath)));
    }

    //Função que procura e adiciona à estrutura de árvore todas as pastas internas baseando-se em um diretório de início
    //Entrada: nova pasta para iniciar-se a pesquisa
    //Retorno: Nova árvore gerada
    //Pré-condição: Nenhuma
    //Pós-condição: A árvore é gerada e retornada
    private static MutableTreeNode scan(File node){
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(node.getName());
        if(node.isDirectory()){
            for(File internalFile: node.listFiles()){
                ret.add(scan(internalFile));
            }
        }
        return ret;
    }
}