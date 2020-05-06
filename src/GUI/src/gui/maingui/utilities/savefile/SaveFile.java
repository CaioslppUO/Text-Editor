package gui.maingui.utilities.savefile;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import gui.Gui;
import gui.maingui.Constants;

import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

import gui.maingui.entities.gFile;
import gui.maingui.interfacegenerator.JDialogGenerator;
import gui.maingui.interfacegenerator.JFileChooserGenerator;

public class SaveFile {

    private JDialog saveFileFrame;
    private JFileChooser chooseSaveDirectory;
    private Constants constants;

    private static SaveFile instance;

    // Construtor
    private SaveFile() {}

    // Função que salva o arquivo que está aberto na variável this.currentFile
    // Entrada: Mostrar ou não a mensagem de arquivo salvo, arquivo aberto e texto
    // para salvar
    // Retorno: Nenhum
    // Pŕe-condição: Nenhuma
    // Pós-condição: O arquivo aberto na variável this.currentFile é salvo
    public void saveFile(Boolean showSaveMessage) {
        if (gFile.getInstance().isOpen()) {
            try {
                PrintWriter print_line = new PrintWriter(new FileWriter(gFile.getInstance().getFile().toString()));
                print_line.printf("%s", gFile.getInstance().getCurrentContent());
                print_line.close();
                if (showSaveMessage) {
                    JOptionPane.showMessageDialog(null, "File Saved");
                }
                Gui.getInstance().runUpdateFileSystemView();
                gFile.getInstance().setIsSaved(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while trying to save the file");
            }
        } else {
            if (showSaveMessage)
                JOptionPane.showMessageDialog(null, "No Files Open");
        }
    }

    // Função que configura o JFileChooser
    // Entrada: Pasta atual
    // Retorno: Nenhum
    // Pré-condição: As variáveis currentFile e currentFolder devem estar
    // devidamente instanciadas e configuradas
    // Pós-condição: O JFileChooser é configurado
    private void configureJFileChooser(String currentFolder) {
        this.saveFileFrame = JDialogGenerator.createJDialog(new Dimension(600, 600), null, "Save As",
                this.constants.getSideAreasColor());
        this.chooseSaveDirectory = JFileChooserGenerator.createJFileChooser(currentFolder, "Save As",
                JFileChooser.DIRECTORIES_ONLY);
        this.saveFileFrame.getContentPane().add(this.chooseSaveDirectory);
        this.saveFileFrame.setVisible(false);
    }

    // Função que salva o arquivo que está aberto na variável this.currentFile
    // Entrada: Arquivo atual, pasta atual e texto para salvar
    // Retorno: Nenhum
    // Pŕe-condição: Nenhum
    // Pós-condição: É aberto um menu para escolher como e onde salvar o arquivo
    // aberto na variável this.currentFile
    public void saveFileAs(String currentFolder) {
        this.constants = new Constants();
        if (gFile.getInstance().isOpen()) {
            this.configureJFileChooser(currentFolder);
            String fileSeparator = System.getProperty("file.separator");
            if (this.chooseSaveDirectory.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // Salvar o arquivo
                File directory = this.chooseSaveDirectory.getSelectedFile();
                if (directory != null) {
                    String fileName;
                    fileName = JOptionPane.showInputDialog("File Name");
                    if (fileName != null) {
                        File newFile = new File(directory.toString() + fileSeparator + fileName);
                        try {
                            if (newFile.createNewFile()) {
                                gFile.getInstance().setFile(newFile);
                                this.saveFile(false);
                            } else {
                                JOptionPane.showMessageDialog(null, "File Already Exists");
                            }
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "Error while trying to save the file");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid File Name");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Directory");
                }
            }
            this.saveFileFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "No Files Open");
        }
    }

    // Getter da instância
    public static SaveFile getInstance(){
        if(instance == null)
            instance = new SaveFile();
        return instance;
    }
}
