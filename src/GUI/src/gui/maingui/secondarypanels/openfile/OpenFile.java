package gui.maingui.secondarypanels.openfile;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import gui.maingui.Constants;
import gui.maingui.secondarypanels.editorpanel.EditorPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import gui.maingui.secondarypanels.savefile.SaveFile;

public class OpenFile {

    private JDialog openFileFrame;
    private JFileChooser chooseOpenFile;
    private Constants constants;

    private File currentFile;
    private SaveFile saveFile;

    //Construtor
    //Entrada: Pasta atual, painel de edição, map de arquivos já adicionados ao visualisador e pasta atual
    //Retorno: O arquivo atual
    //Pré-condição: As variáveis recebidas devem estar devidamente instanciadas e configuradas
    //Pós-condição: A classe é instanciada
    public OpenFile(File currentFile) {
        this.constants = new Constants();
        this.currentFile = currentFile;
        this.saveFile = new SaveFile();
    }

    //Função que define o frame de salvar um arquivo
    //Entrada: Pasta atual
    //Retorno: FileChooser.APPROVE_OPTION se o diretório for escolhido ou o contrário caso não seja
    //Pré-condição: Nenhuma
    //Pós-condição: Nenhuma
    private int defineCreateOpenFileFrame(String currentFolder) {
        this.openFileFrame = new JDialog();
        this.openFileFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.openFileFrame.setSize(600, 600);
        this.openFileFrame.setLocationRelativeTo(null);
        this.openFileFrame.setTitle("Save File");

        chooseOpenFile = new JFileChooser();
        chooseOpenFile.setCurrentDirectory(new File(currentFolder));
        chooseOpenFile.setDialogTitle("Save to");
        chooseOpenFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        return chooseOpenFile.showOpenDialog(null);
    }

    //Função que abre um menu para escolher um arquivo para abrir
    //Entrada: A pasta atual e o editorPane
    //Retorno: O novo arquivo que foi aberto
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo é aberto no editor
    public File openFile(String currentFolder, EditorPane editorPane) {
        if (defineCreateOpenFileFrame(currentFolder) == JFileChooser.APPROVE_OPTION) { //Abre o arquivo
            File file = new File(chooseOpenFile.getSelectedFile().toString());
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String contentToLoad = "", aux;
                try {
                    while ((aux = br.readLine()) != null) {
                        contentToLoad += aux;
                        contentToLoad += "\n";
                    }
                    editorPane.getEditorPane().setText(contentToLoad);
                    this.currentFile = file;
                } catch (IOException e) {
                    //do Nothing
                }
            } catch (FileNotFoundException e) {
                if (file.isDirectory()) {
                    JOptionPane.showMessageDialog(null, "The selected file is not a file");
                } else {
                    JOptionPane.showMessageDialog(null, "Error while trying to load file");
                }
            }
        }
        this.openFileFrame.getContentPane().add(chooseOpenFile);
        this.openFileFrame.setVisible(true);
        this.openFileFrame.dispose();

        return this.currentFile;
    }

    //Entrada: Caminho do arquivo a ser aberto
    //Retorno: O novo arquivo que foi aberto
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo é aberto no editor
    public File openFileUsingPath(String filePath, EditorPane editorPane) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String contentToLoad = "", aux;
            try {
                while ((aux = br.readLine()) != null) {
                    contentToLoad += aux;
                    contentToLoad += "\n";
                }
                this.saveFile.saveFile(false, this.currentFile, editorPane.getEditorPane().getText());
                editorPane.getEditorPane().setText(contentToLoad);
                this.currentFile = file;
            } catch (IOException e) {
                //do Nothing

            }
        } catch (FileNotFoundException e) {
            if (file.isDirectory()) {
                JOptionPane.showMessageDialog(null, "The selected file is not a file");
            } else {
                JOptionPane.showMessageDialog(null, "Error while trying to load file");
            }
        }
        return this.currentFile;
    }
}
