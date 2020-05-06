package gui.maingui.utilities.newfile;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

import gui.maingui.interfacegenerator.JDialogGenerator;
import gui.maingui.interfacegenerator.JFileChooserGenerator;
import gui.Gui;
import gui.maingui.Constants;
import gui.maingui.utilities.savefile.SaveFile;
import gui.maingui.entities.gFile;

public class NewFile {

    private JDialog createNewFileFrame;
    private JFileChooser chooseNewFileDirectory;
    private Constants constants;
    private String fileSeparator;

    private static NewFile instance;

    // Construtor
    // Entrada: Arquivo atual, o painel de edição e a pasta atual
    // Retorno: Nenhum
    // Pré-condição: As variáveis currentFile, editorPane e currentFolder devem
    // estar devidamente instanciadas e configuradas
    // Pós-condição: A classe é instanciada
    private NewFile() {
    }

    // Função que configura o JFileChooser
    // Entrada: Pasta atual
    // Retorno: Nenhum
    // Pré-condição: As variáveis currentFile e currentFolder devem estar
    // devidamente instanciadas e configuradas
    // Pós-condição: O JFileChooser é configurado
    private void configureFileChooser(String currentFolder) {
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
    public void createNewFile(String currentFolder) {
        this.constants = new Constants();
        this.fileSeparator = System.getProperty("file.separator");
        this.configureFileChooser(currentFolder);
        if (!gFile.getInstance().isOpen()) { // Não existe arquivo aberto previamente
            if (this.chooseNewFileDirectory.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File directory = this.chooseNewFileDirectory.getSelectedFile();
                if (directory != null && directory.isDirectory()) { // Pasta válida
                    String fileName = JOptionPane.showInputDialog("File Name");
                    if (fileName != null && !fileName.equals("") && directory != null && directory.isDirectory()) { // Nome
                                                                                                                    // válido
                        File newFile = new File(directory.toString() + this.fileSeparator + fileName);
                        try { // Tenta criar o novo arquivo
                            if (newFile.createNewFile()) {
                                JOptionPane.showMessageDialog(null, "File Created");
                                gFile.getInstance().setFile(newFile);
                                Gui.getInstance().getEditorPane().getEditorPane().setText("");
                            } else { // Arquivo já existente
                                JOptionPane.showMessageDialog(null, "File Already Exists");
                            }
                        } catch (IOException e) { // Erro ao tentar criar o arquivo
                            JOptionPane.showMessageDialog(null, "Error while trying to create the file");
                        }
                    } else { // Nome inválido
                        JOptionPane.showMessageDialog(null, "Invalid File Name");
                    }
                } else { // Pasta inválida
                    JOptionPane.showMessageDialog(null, "Invalid Folder");
                }
                this.createNewFileFrame.dispose();
            }
        } else { // Existe arquivo aberto previamente
            SaveFile.getInstance().saveFile(false);
            this.createNewFile(currentFolder);
        }
    }

    // Getter da instância
    public static NewFile getInstance() {
        if (instance == null)
            instance = new NewFile();
        return instance;
    }
}
