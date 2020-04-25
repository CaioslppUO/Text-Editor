package gui.maingui;

import gui.extragui.RoundedPanel;
import java.awt.Color;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {
    //Cores
    private Color PaneEditorColor;
    private Color MenuBarColor;
    private Color sideAreasColor;
    private Color MenuForeGroundColor;
    private Color editorPaneFontColor;
    private Color buttonCloseEnteredColor;
    
    //Fonte
    private Integer fontSize;
    private String fontType;
    
    //Variáveis "globais"
    private File currentFile;
    private String currentFolder;
    private Map<String, RoundedPanel> addedFilesPanel;
    private String lastClickedFilePath;
    
    public Constants(){
        //Cores
        this.PaneEditorColor = new Color(51, 51, 51);
        this.sideAreasColor = new Color(82, 82, 82);
        this.MenuBarColor = new Color(28, 28, 28);
        this.MenuForeGroundColor = new Color(137, 163, 201);
        this.editorPaneFontColor = new Color(191, 191, 191);
        this.buttonCloseEnteredColor = new Color(173, 36, 36);
        
        //Variáveis globais
        this.currentFile = null;
        this.currentFolder = ".";
        this.addedFilesPanel = new LinkedHashMap<>();
        lastClickedFilePath = null;
        
        //Fonte padrão
        this.fontSize = 12;
        this.fontType = "Monospaced";
    }

    public Color getPaneEditorColor() {
        return PaneEditorColor;
    }

    public void setPaneEditorColor(Color PaneEditorColor) {
        this.PaneEditorColor = PaneEditorColor;
    }

    public Color getMenuBarColor() {
        return MenuBarColor;
    }

    public void setMenuBarColor(Color MenuBarColor) {
        this.MenuBarColor = MenuBarColor;
    }

    public Color getSideAreasColor() {
        return sideAreasColor;
    }

    public void setSideAreasColor(Color sideAreasColor) {
        this.sideAreasColor = sideAreasColor;
    }

    public Color getMenuForeGroundColor() {
        return MenuForeGroundColor;
    }

    public void setMenuForeGroundColor(Color MenuForeGroundColor) {
        this.MenuForeGroundColor = MenuForeGroundColor;
    }

    public Color getEditorPaneFontColor() {
        return editorPaneFontColor;
    }

    public void setEditorPaneFontColor(Color editorPaneFontColor) {
        this.editorPaneFontColor = editorPaneFontColor;
    }

    public Color getButtonCloseEnteredColor() {
        return buttonCloseEnteredColor;
    }

    public void setButtonCloseEnteredColor(Color buttonCloseEnteredColor) {
        this.buttonCloseEnteredColor = buttonCloseEnteredColor;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    public String getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(String currentFolder) {
        this.currentFolder = currentFolder;
    }

    public Map<String, RoundedPanel> getAddedFilesPanel() {
        return addedFilesPanel;
    }

    public void setAddedFilesPanel(Map<String, RoundedPanel> addedFilesPanel) {
        this.addedFilesPanel = addedFilesPanel;
    }

    public String getLastClickedFilePath() {
        return lastClickedFilePath;
    }

    public void setLastClickedFilePath(String lastClickedFilePath) {
        this.lastClickedFilePath = lastClickedFilePath;
    }
    
    
}
