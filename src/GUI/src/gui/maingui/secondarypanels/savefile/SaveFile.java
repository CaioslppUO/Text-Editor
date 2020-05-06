package gui.maingui.secondarypanels.savefile;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import gui.maingui.Constants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

import gui.maingui.entities.gFile;

public class SaveFile {

    private JDialog saveFileFrame;
    private JFileChooser chooseSaveDirectory;
    private Constants constants;

    //Construtor
    public SaveFile() {
        this.constants = new Constants();
    }

    //Função que salva o arquivo que está aberto na variável this.currentFile
    //Entrada: Mostrar ou não a mensagem de arquivo salvo, arquivo aberto e texto para salvar
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo aberto na variável this.currentFile é salvo
    public void saveFile(Boolean showSaveMessage) {
        if (gFile.getInstance().isOpen()) {
            try {
                PrintWriter print_line = new PrintWriter(new FileWriter(gFile.getInstance().getFile().toString()));
                print_line.printf("%s", gFile.getInstance().getCurrentContent());
                print_line.close();
                if (showSaveMessage) {
                    JOptionPane.showMessageDialog(null, "File Saved");
                }
                gFile.getInstance().setIsSaved(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while trying to save the file");
            }
        } else {
            if(showSaveMessage)
                JOptionPane.showMessageDialog(null, "No Files Open");
        }
    }

    //Função que define o frame de salvar um arquivo
    //Entrada: Nenhuma
    //Retorno: FileChooser.APPROVE_OPTION se o diretório for escolhido ou o contrário caso não seja
    //Pré-condição: Nenhuma
    //Pós-condição: Nenhuma
    private int defineSaveFileFrame(String currentFolder) {
        this.saveFileFrame = new JDialog();
        this.saveFileFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.saveFileFrame.setSize(600, 600);
        this.saveFileFrame.setLocationRelativeTo(null);
        this.saveFileFrame.setTitle("Save File As");

        this.chooseSaveDirectory = new JFileChooser();
        this.chooseSaveDirectory.setCurrentDirectory(new File(currentFolder));
        this.chooseSaveDirectory.setDialogTitle("Save As");
        this.chooseSaveDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.saveFileFrame.getContentPane().add(chooseSaveDirectory);
        this.saveFileFrame.setVisible(true);
        this.saveFileFrame.dispose();
        return chooseSaveDirectory.showOpenDialog(null);
    }

    //Função que salva o arquivo que está aberto na variável this.currentFile
    //Entrada: Arquivo atual, pasta atual e texto para salvar
    //Retorno: Nenhum
    //Pŕe-condição: Nenhum
    //Pós-condição: É aberto um menu para escolher como e onde salvar o arquivo aberto na variável this.currentFile
    public File saveFileAs(String currentFolder) {
        if (gFile.getInstance().isOpen()){
            String fileSeparator = System.getProperty("file.separator");

            if (this.defineSaveFileFrame(currentFolder) == JFileChooser.APPROVE_OPTION) { //Salvar o arquivo
                File directory = this.chooseSaveDirectory.getSelectedFile();
                if (directory != null) {
                    String fileName;
                    fileName = JOptionPane.showInputDialog("File Name");
                    if (fileName != null) {
                        File newFile = new File(directory.toString() + fileSeparator + fileName);
                        try {
                            if (newFile.createNewFile()) {
                                gFile.getInstance().setFile(newFile);
                                this.saveFile(true);
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
        } else {
            JOptionPane.showMessageDialog(null, "No Files Open");
        }
        return gFile.getInstance().getFile();
    }
}
