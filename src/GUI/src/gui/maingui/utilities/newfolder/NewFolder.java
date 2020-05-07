package gui.maingui.utilities.newfolder;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import gui.maingui.utilities.Constants;

import gui.maingui.interfacegenerator.JDialogGenerator;
import gui.maingui.interfacegenerator.JFileChooserGenerator;

public class NewFolder {

    private JDialog createNewFolderFrame;
    private JFileChooser chooseNewFolderDirectory;

    private static NewFolder instance;

    // Construtor
    private NewFolder() {}

    // Função que configura o JFileChooser
    // Entrada: Pasta atual
    // Retorno: Nenhum
    // Pré-condição: As variáveis currentFile e currentFolder devem estar
    // devidamente instanciadas e configuradas
    // Pós-condição: O JFileChooser é configurado
    private void configureJFileChooser(String currentFolder){
        this.createNewFolderFrame = JDialogGenerator.createJDialog(new Dimension(600, 600), null, "Create New Folder", Constants.getInstance().getSideAreasColor());
        this.chooseNewFolderDirectory = JFileChooserGenerator.createJFileChooser(currentFolder, "Create New folder", JFileChooser.DIRECTORIES_ONLY);
        this.createNewFolderFrame.getContentPane().add(this.chooseNewFolderDirectory);
        this.createNewFolderFrame.setVisible(false);
    }

    // Função que cria um diretório padrão caso já não exista
    // Entrada: Pasta aberta atualmente
    // Retorno: Diretório criado
    // Pŕe-condição: Nenhuma
    // Pós-condição: O diretório é criado
    public String createNewFolder(String currentFolder) {
        this.configureJFileChooser(currentFolder);
        if (this.chooseNewFolderDirectory.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File newFolder;
            String folderName = JOptionPane.showInputDialog("Folder Name");
            newFolder = new File(this.chooseNewFolderDirectory.getSelectedFile() + "/" + folderName);
            if (newFolder.mkdir()) {
                currentFolder = newFolder.getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(null, "A file or folder with this name already exist");
            }
        }
        this.createNewFolderFrame.dispose();
        return currentFolder;
    }

    // Getter da instância
    public static NewFolder getInstance(){
        if(instance == null)
            instance = new NewFolder();
        return instance;
    }

}