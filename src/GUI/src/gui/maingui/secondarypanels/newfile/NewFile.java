package gui.maingui.secondarypanels.newfile;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

import gui.maingui.interfacegenerator.JDialogGenerator;
import gui.maingui.interfacegenerator.JFileChooserGenerator;
import gui.maingui.Constants;
import gui.maingui.secondarypanels.editorpanel.EditorPane;
import gui.maingui.secondarypanels.savefile.SaveFile;

public class NewFile {

    private JDialog createNewFileFrame;
    private JFileChooser chooseNewFileDirectory;
    private Constants constants;
    private File currentFile;
    private String fileSeparator;
    private SaveFile saveFile;

    // Construtor
    // Entrada: Arquivo atual, o painel de edição e a pasta atual
    // Retorno: Nenhum
    // Pré-condição: As variáveis currentFile, editorPane e currentFolder devem
    // estar devidamente instanciadas e configuradas
    // Pós-condição: A classe é instanciada
    public NewFile(File currentFile, EditorPane editorPane, String currentFolder) {
        this.constants = new Constants();
        this.fileSeparator = System.getProperty("file.separator");
        this.saveFile = new SaveFile();
        this.configureFileChooser(currentFile, currentFolder);
        this.createNewFile(editorPane);
    }

    // Função que configura o JFileChooser
    // Entrada: Arquivo atual e pasta atual
    // Retorno: Nenhum
    // Pré-condição: As variáveis currentFile e currentFolder devem estar
    // devidamente instanciadas e configuradas
    // Pós-condição: O JFileChooser é configurado
    private void configureFileChooser(File currenfFile, String currentFolder) {
        this.currentFile = currentFile;
        this.createNewFileFrame = JDialogGenerator.createJDialog(new Dimension(600, 600), null, "Create New File",
                this.constants.getSideAreasColor());
        this.chooseNewFileDirectory = JFileChooserGenerator.createJFileChooser(currentFolder, "Create New File",
                JFileChooser.DIRECTORIES_ONLY);
        this.createNewFileFrame.getContentPane().add(chooseNewFileDirectory);
        this.createNewFileFrame.setVisible(false);
    }

    // Função que cria um novo arquivo caso ele já não exista
    // Entrada: Painel de edição
    // Retorno: Nenhum
    // Pŕe-condição: Nenhuma
    // Pós-condição: O arquivo é criado e salvo no disco
    public void createNewFile(EditorPane editorPane) {
        if (this.currentFile == null) { // Não existe arquivo aberto previamente
            if (this.chooseNewFileDirectory.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File directory = this.chooseNewFileDirectory.getSelectedFile(); 
                if(directory != null && directory.isDirectory()){ // Pasta válida
                    String fileName = JOptionPane.showInputDialog("File Name");
                    if (fileName != null && !fileName.equals("") && directory != null && directory.isDirectory()) { // Nome válido
                        File newFile = new File(directory.toString() + this.fileSeparator + fileName);
                        try { // Tenta criar o novo arquivo
                            if (newFile.createNewFile()) {
                                JOptionPane.showMessageDialog(null, "File Created");
                                this.currentFile = newFile;
                                editorPane.getEditorPane().setText("");
                            } else { // Arquivo já existente
                                JOptionPane.showMessageDialog(null, "File Already Exists");
                            }
                        } catch (IOException e) { // Erro ao tentar criar o arquivo
                            JOptionPane.showMessageDialog(null, "Error while trying to create the file");
                        }
                    } else { // Nome inválido
                        JOptionPane.showMessageDialog(null, "Invalid File Name");
                    }
                }else{ // Pasta inválida
                    JOptionPane.showMessageDialog(null, "Invalid Folder");
                }
                this.createNewFileFrame.dispose();
            }
        } else { // Existe arquivo aberto previamente
            this.saveFile.saveFile(false, this.currentFile, editorPane.getEditorPane().getText());
            this.currentFile = null;
            this.createNewFile(editorPane);
        }
    }

    // Getter do currentFile. O arquivo aberto ficará guardado nessa variável
    public File getCurrentFile() {
        return currentFile;
    }

}
