package gui.maingui.secondarypanels.newfolder;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import gui.maingui.Constants;

public class NewFolder {

    private JDialog createNewFolderFrame;
    private Constants constants;
    private JFileChooser chooseNewFolderDirectory;

    // Construtor
    public NewFolder() {
        this.constants = new Constants();
    }

    //Função que define o frame de criar um novo diretório
    //Entrada: Pasta atualmente aberto
    //Retorno: FileChooser.APPROVE_OPTION se o diretório for escolhido ou o contrário caso não seja
    //Pré-condição: Nenhuma
    //Pós-condição: Nenhuma
    public int defineCreateNewFolderFrame(String currentFolder) {
        this.createNewFolderFrame = new JDialog();
        this.createNewFolderFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.createNewFolderFrame.setSize(600, 600);
        this.createNewFolderFrame.setLocationRelativeTo(null);
        this.createNewFolderFrame.setTitle("Select Location");

        this.chooseNewFolderDirectory = new JFileChooser();
        this.chooseNewFolderDirectory.setCurrentDirectory(new File(currentFolder));
        this.chooseNewFolderDirectory.setDialogTitle("Save to");
        this.chooseNewFolderDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return chooseNewFolderDirectory.showOpenDialog(null);
    }

    // Função que cria um diretório padrão caso já não exista
    // Entrada: Pasta aberta atualmente
    // Retorno: Diretório criado
    // Pŕe-condição: Nenhuma
    // Pós-condição: O diretório é criado
    public String createNewFolder(String currentFolder) {
       if(this.defineCreateNewFolderFrame(currentFolder) == JFileChooser.APPROVE_OPTION){
            File newFolder;
            String folderName = JOptionPane.showInputDialog("Folder Name");
            newFolder = new File(this.chooseNewFolderDirectory.getSelectedFile() + "/" + folderName);
            if(newFolder.mkdir()){
                JOptionPane.showMessageDialog(null, "folder successfully created");
                currentFolder = newFolder.getAbsolutePath();
            }else{
                JOptionPane.showMessageDialog(null, "folder already exist");
            }

            this.createNewFolderFrame.getContentPane().add(this.chooseNewFolderDirectory);
            this.createNewFolderFrame.setVisible(true);
       }
       this.createNewFolderFrame.dispose();  
       return currentFolder;
    }

}