package gui.maingui.secondarypanels.filesview;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;

import gui.maingui.utilities.Constants;

public class FilesView {
    private Map<String, fFile> fFiles;
    private static FilesView instance;

    // Construtor
    private FilesView() {
        this.fFiles = new LinkedHashMap<>();
    }

    // Função que adicona um novo arquivo à lista de fFiles
    public void addfFile(String filePath, fFile fFile) {
        this.unselectAllFiles(null);
        fFile.setIsSelected(true);
        this.fFiles.put(filePath, fFile);
        this.paintSelectedFile();
    }

    // Função que remove um arquivo da lista de fFiles
    public void removefFile(String filePath){
        this.fFiles.remove(filePath);
        this.unselectAllFiles(null);
        if(!this.fFiles.isEmpty()){
            ((fFile) this.fFiles.values().toArray()[0]).setIsSelected(true);
            this.paintSelectedFile();
        }
    }

    // Função que atualiza todos os estados de isSelect de todos os fFiles do Map
    private void unselectAllFiles(String pathToKeepSelected){
        int i;
        String checkPath = "";
        if(pathToKeepSelected != null)
            checkPath = pathToKeepSelected;
        for(i = 0; i < this.fFiles.size(); i++){
            fFile aux = (fFile) this.fFiles.values().toArray()[i];
            if(aux.getFilePath() == checkPath)
                aux.setIsSelected(true);
            else
                aux.setIsSelected(false);
        }
    }

    // Função que seleciona um arquivo específico e des-seleciona os outros
    public void selectFile(String path){
        this.unselectAllFiles(path);
        this.paintSelectedFile();
    }

    // Função que retorna um fFile dado o caminho para o arquivo
    public fFile getfFile(String filePath) {
        return this.fFiles.get(filePath);
    }

    // Função que retorna um fFile dado a sua posição
    public fFile getFile(int pos) {
        try {
            return (fFile) this.fFiles.values().toArray()[pos];
        } catch (Exception e) {
            try {
                return (fFile) this.fFiles.values().toArray()[0];
            } catch (Exception e2) {
                return null;
            }
        }
    }

    // Função que verifica se um arquivo já está contido na lista de arquivos
    public Boolean isInside(String path){
        if(this.fFiles.get(path) == null)
            return false;
        return true;
    }

    // Função que pinta de branco o arquivo selecionado e de cor padrão os outros arquivos
    public void paintSelectedFile() {
        int i;
        for (i = 0; i < this.fFiles.size(); i++) { // Percorre todos os fFiles
            fFile file = this.getFile(i);
            if (file.isSelected()) {
                for (Component c : file.getPanel().getComponents()) { // Percorre todos os componentes do painel
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(Color.WHITE);
                    }
                }
            }else{
                for (Component c : file.getPanel().getComponents()) { // Percorre todos os componentes do painel
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(Constants.getInstance().getMenuForeGroundColor());
                    }
                }
            }
        }
    }

    // Getter dos arquivos
    public Map<String, fFile> getfFiles() {
        return this.fFiles;
    }

    // Getter da instância
    public static FilesView getInstance(){
        if(instance == null)
            instance = new FilesView();
        return instance;
    }
}