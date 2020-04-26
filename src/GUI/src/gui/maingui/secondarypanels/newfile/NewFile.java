package gui.maingui.secondarypanels.newfile;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import gui.maingui.Constants;
import java.io.File;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

public class NewFile {

    private JDialog createNewFileFrame;
    private JFileChooser chooseNewFileDirectory;
    private Constants constants;
    private JEditorPane editorPane;
    private File currentFile;
    private String currentFolder;

    public NewFile(File currentFile, JEditorPane editorPane, String currentFolder) {
        this.constants = new Constants();
        this.currentFile = currentFile;
        this.editorPane = editorPane;
        this.currentFolder = currentFolder;
    }

    //Função que define o frame de criar um novo arquivo
    //Entrada: Nenhuma
    //Retorno: FileChooser.APPROVE_OPTION se o diretório for escolhido ou o contrário caso não seja
    //Pré-condição: Nenhuma
    //Pós-condição: Nenhuma
    private int defineCreateFileFrame() {
        this.createNewFileFrame = new JDialog();
        this.createNewFileFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.createNewFileFrame.setSize(600, 600);
        this.createNewFileFrame.setLocationRelativeTo(null);
        this.createNewFileFrame.setTitle("Save File As");

        this.chooseNewFileDirectory = new JFileChooser();
        this.chooseNewFileDirectory.setCurrentDirectory(new File(this.currentFolder));
        this.chooseNewFileDirectory.setDialogTitle("Save to");
        this.chooseNewFileDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return chooseNewFileDirectory.showOpenDialog(null);
    }

    //Função que cria um novo arquivo caso ele já não exista
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo é criado e salvo no disco
    public void createNewFile() {
        if (this.currentFile == null) {
            String fileSeparator = System.getProperty("file.separator");
            if (defineCreateFileFrame() == JFileChooser.APPROVE_OPTION) { //Salvar o arquivo
                File directory = this.chooseNewFileDirectory.getSelectedFile();
                String fileName;
                fileName = JOptionPane.showInputDialog("File Name");
                if (fileName != null) {
                    File newFile = new File(directory.toString() + fileSeparator + fileName);
                    try {
                        if (newFile.createNewFile()) {
                            JOptionPane.showMessageDialog(null, "File Created");
                            this.currentFile = newFile;
                            this.editorPane.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "File Already Exists");
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error while trying to create the file");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid File Name");
                }
            }
            this.createNewFileFrame.getContentPane().add(chooseNewFileDirectory);

            this.createNewFileFrame.setVisible(true);
            this.createNewFileFrame.dispose();
        } else {
            this.currentFile = null;
            this.createNewFile();
        }
    }

    public JEditorPane getEditorPane() {
        return editorPane;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public String getCurrentFolder() {
        return currentFolder;
    }
    
    
}
