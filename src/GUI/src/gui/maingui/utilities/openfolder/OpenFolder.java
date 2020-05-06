package gui.maingui.utilities.openfolder;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import gui.maingui.Constants;
import gui.maingui.interfacegenerator.JDialogGenerator;
import gui.maingui.interfacegenerator.JFileChooserGenerator;

import java.awt.Dimension;
import java.io.File;

public class OpenFolder {

    private JDialog openFolderFrame;
    private JFileChooser chooseOpenDirectory;
    private Constants constants;

    // Construtor
    public OpenFolder() {}

    // Função que configura o JFileChooser
    // Entrada: Pasta atual
    // Retorno: Nenhum
    // Pré-condição: As variáveis currentFile e currentFolder devem estar
    // devidamente instanciadas e configuradas
    // Pós-condição: O JFileChooser é configurado
    private void configureJFileChooser(String currentFolder) {
        this.openFolderFrame = JDialogGenerator.createJDialog(new Dimension(600, 600), null, "Open Folder",
                this.constants.getSideAreasColor());
        this.chooseOpenDirectory = JFileChooserGenerator.createJFileChooser(currentFolder, "Open Folder",
                JFileChooser.DIRECTORIES_ONLY);
        this.openFolderFrame.getContentPane().add(this.chooseOpenDirectory);
        this.openFolderFrame.setVisible(false);
    }
    
    // Função que abre uma pasta, a qual será o diretório padrão
    // Entrada: Pasta atualmente aberta
    // Retorno: Nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: A pasta é aberta e vira o diretório padrão
    public String openFolder(String currentFolder) {
        this.constants = new Constants();
        this.configureJFileChooser(currentFolder);
        if (this.chooseOpenDirectory.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File aux = new File(this.chooseOpenDirectory.getSelectedFile().getAbsolutePath());
            if (aux.isDirectory()) {
                currentFolder = aux.getAbsolutePath();
            }
        }
        this.openFolderFrame.dispose();
        return currentFolder;
    }
}
