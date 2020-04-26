package gui.maingui.secondarypanels.openfolder;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import gui.maingui.Constants;
import java.io.File;

public class OpenFolder {

    private JDialog openFolderFrame;
    private JFileChooser chooseOpenDirectory;
    private Constants constants;

    //Construtor
    public OpenFolder() {
        this.constants = new Constants();
    }

    //Função que define o frame da janela de abrir pastas
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    //Pós-condição: A janela de escolha de pasta é criada e exposta
    private int defineOpenFolderFrame(String currentFolder) {
        this.openFolderFrame = new JDialog();
        this.openFolderFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.openFolderFrame.setSize(600, 600);
        this.openFolderFrame.setLocationRelativeTo(null);
        this.openFolderFrame.setTitle("Open Folder");

        this.chooseOpenDirectory = new JFileChooser();
        this.chooseOpenDirectory.setCurrentDirectory(new File(currentFolder));
        this.chooseOpenDirectory.setDialogTitle("Open Folder");
        this.chooseOpenDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.openFolderFrame.getContentPane().add(chooseOpenDirectory);
        this.openFolderFrame.setVisible(true);
        this.openFolderFrame.dispose();
        return chooseOpenDirectory.showOpenDialog(null);
    }

    //Função que abre uma pasta, a qual será o diretório padrão
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    //Pós-condição: A pasta é aberta e vira o diretório padrão
    public String openFolder(String currentFolder) {
        if (this.defineOpenFolderFrame(currentFolder) == JFileChooser.APPROVE_OPTION) {
            currentFolder = this.chooseOpenDirectory.getSelectedFile().getAbsolutePath();
        }
        return currentFolder;
    }
}
