package gui.maingui.secondarypanels.openfile;

import gui.extragui.RoundedPanel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import gui.maingui.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import gui.maingui.secondarypanels.savefile.SaveFile;

public class OpenFile {
    private JDialog openFileFrame;
    private JFileChooser chooseOpenFile;
    private Constants constants;
    private Map<String, RoundedPanel> addedFilesPanel;
    private File currentFile;
    private JEditorPane editorPane;
    private SaveFile saveFile;
    private String currentFolder;
    
    public OpenFile(File currentFile, JEditorPane editorPane, Map<String, RoundedPanel> addedFilesPanel, String currentFolder){
        this.constants = new Constants();
        this.currentFile = currentFile;
        this.editorPane = editorPane;
        this.addedFilesPanel = addedFilesPanel;
        this.currentFolder = currentFolder;
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
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo é aberto no editor
    public void openFile() {
        if (defineCreateOpenFileFrame(this.currentFolder) == JFileChooser.APPROVE_OPTION) { //Abre o arquivo
            File file = new File(chooseOpenFile.getSelectedFile().toString());
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String contentToLoad = "", aux;
                try {
                    while ((aux = br.readLine()) != null) {
                        contentToLoad += aux;
                        contentToLoad += "\n";
                    }
                    this.editorPane.setText(contentToLoad);
                    this.currentFile = file;
                } catch (IOException e) {
                    //do Nothing
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error while trying to load file");
            }
        }
        this.openFileFrame.getContentPane().add(chooseOpenFile);
        this.openFileFrame.setVisible(true);
        this.openFileFrame.dispose();
    }
    
    //Entrada: Caminho do arquivo a ser aberto
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo é aberto no editor
    public void openFile(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String contentToLoad = "", aux;
            try {
                while ((aux = br.readLine()) != null) {
                    contentToLoad += aux;
                    contentToLoad += "\n";
                }
                this.saveFile.saveFile(false,this.currentFile,this.editorPane.getText());
                this.editorPane.setText(contentToLoad);
                this.currentFile = file;
            } catch (IOException e) {
                //do Nothing

            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error while trying to load file");
        }
    }

    public Map<String, RoundedPanel> getAddedFilesPanel() {
        return addedFilesPanel;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public JEditorPane getEditorPane() {
        return editorPane;
    }

    public String getCurrentFolder() {
        return currentFolder;
    }
    
    
    
}
