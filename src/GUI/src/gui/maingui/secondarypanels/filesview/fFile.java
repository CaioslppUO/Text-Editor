package gui.maingui.secondarypanels.filesview;

import java.awt.Component;

import javax.swing.Box;

import gui.extragui.RoundedPanel;

public class fFile {
    private Component separator;
    private RoundedPanel panel;
    private Boolean isSelected;
    private String fileName;
    private String filePath;

    // Construtor
    public fFile(String fileName, String filePath, RoundedPanel panel){
        this.fileName = fileName;
        this.filePath = filePath;
        this.panel = panel;
        this.separator = Box.createHorizontalStrut(2);
        this.isSelected = false;
    }

    // Getter da verificação de se o arquivo está ou não selecionado
    public Boolean isSelected(){
        return this.isSelected;
    }

    // Getter do painel do arquivo
    public RoundedPanel getPanel(){
        return this.panel;
    }

    // Getter do nome do arquivo
    public String getFileName(){
        return this.fileName;
    }

    // Getter do caminho completo do arquivo
    public String getFilePath(){
        return this.filePath;
    }

    // Setter da verificação de se o arquivo está ou não selecionado
    public void setIsSelected(Boolean isSelected){
        this.isSelected = isSelected;
    }

    // Getter do separador
    public Component getSeprator(){
        return this.separator;
    }
}