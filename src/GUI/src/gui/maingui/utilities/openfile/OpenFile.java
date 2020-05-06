package gui.maingui.utilities.openfile;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

import gui.maingui.utilities.savefile.SaveFile;
import gui.maingui.entities.gFile;
import gui.maingui.interfacegenerator.JDialogGenerator;
import gui.maingui.interfacegenerator.JFileChooserGenerator;
import gui.Gui;
import gui.maingui.Constants;

public class OpenFile {

    private JDialog openFileFrame;
    private JFileChooser chooseOpenFile;
    private Constants constants;
    private File tempFile;

    private static OpenFile instance;

    // Construtor
    // Entrada: Pasta atual, painel de edição, map de arquivos já adicionados ao
    // visualisador e pasta atual
    // Retorno: O arquivo atual
    // Pré-condição: As variáveis recebidas devem estar devidamente instanciadas e
    // configuradas
    // Pós-condição: A classe é instanciada
    private OpenFile() {}

    // Função que configura o JFileChooser
    // Entrada: Pasta atual
    // Retorno: Nenhum
    // Pré-condição: As variáveis currentFile e currentFolder devem estar
    // devidamente instanciadas e configuradas
    // Pós-condição: O JFileChooser é configurado
    private void configureJFileChooser(String currentFolder){
        this.openFileFrame = JDialogGenerator.createJDialog(new Dimension(600, 600), null, "Open File", this.constants.getSideAreasColor());
        this.chooseOpenFile = JFileChooserGenerator.createJFileChooser(currentFolder, "Open File", JFileChooser.FILES_AND_DIRECTORIES);
        this.openFileFrame.getContentPane().add(this.chooseOpenFile);
        this.openFileFrame.setVisible(false);
    }

    // Função que abre um menu para escolher um arquivo para abrir
    // Entrada: A pasta atual e o editorPane
    // Retorno: O novo arquivo que foi aberto
    // Pŕe-condição: Nenhuma
    // Pós-condição: O arquivo é aberto no editor
    public void openFile(String currentFolder) {
        this.constants = new Constants();
        if (!gFile.getInstance().isOpen()) { // Não existe arquivo aberto previamente
            this.configureJFileChooser(currentFolder);
            if (this.chooseOpenFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // Abre o arquivo
                File file = new File(chooseOpenFile.getSelectedFile().toString());
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String contentToLoad = "", aux;
                    try {
                        while ((aux = br.readLine()) != null) {
                            contentToLoad += aux;
                            contentToLoad += "\n";
                        }
                        Gui.getInstance().getEditorPane().getEditorPane().setText(contentToLoad);
                        gFile.getInstance().setFile(file);
                    } catch (IOException e) {
                        // do Nothing
                    }
                } catch (FileNotFoundException e) {
                    if (file.isDirectory()) {
                        JOptionPane.showMessageDialog(null, "The selected file is not a file");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error while trying to load file");
                    }
                }
            }else{ // Cancelamento da abertura de arquivo
                if(this.tempFile != null)
                    Gui.getInstance().runOpenFile(this.tempFile.getAbsolutePath());
                this.tempFile = null;
            }
            this.openFileFrame.dispose();
        } else { // Existe arquivo aberto previamente
            SaveFile.getInstance().saveFile(false,false);
            this.tempFile = new File(gFile.getInstance().getFullPath());
            gFile.getInstance().closeFile();
            this.openFile(currentFolder);
        }
    }

    // Função que abre um arquivo em que seja passado o caminho para abrir
    // Entrada: Caminho do arquivo a ser aberto
    // Retorno: O novo arquivo que foi aberto
    // Pŕe-condição: Nenhuma
    // Pós-condição: O arquivo é aberto no editor
    public void openFileUsingPath(String filePath, String currentFolder) {
        this.constants = new Constants();
        this.configureJFileChooser(currentFolder);
        if (!gFile.getInstance().isOpen()) { // Não existe arquivo aberto previamente
            File file = new File(filePath);
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String contentToLoad = "", aux;
                try {
                    while ((aux = br.readLine()) != null) {
                        contentToLoad += aux;
                        contentToLoad += "\n";
                    }
                    Gui.getInstance().getEditorPane().getEditorPane().setText(contentToLoad);
                    gFile.getInstance().setFile(file);
                } catch (IOException e) {
                    // do Nothing
                }
            } catch (FileNotFoundException e) {
                if (file.isDirectory()) {
                    JOptionPane.showMessageDialog(null, "The selected file is not a file");
                } else {
                    JOptionPane.showMessageDialog(null, "Error while trying to load file");
                }
            }
        } else { // Existe arquivo aberto previamente
            SaveFile.getInstance().saveFile(false,false);
            gFile.getInstance().closeFile();
            this.openFileUsingPath(filePath,currentFolder);
        }
    }

    // Getter da instância
    public static OpenFile getInstance(){
        if(instance == null)
            instance = new OpenFile();
        return instance;
    }
}
