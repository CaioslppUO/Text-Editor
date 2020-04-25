package gui.maingui;

import java.awt.Color;

public class Constants {
    //Cores
    private Color PaneEditorColor;
    private Color MenuBarColor;
    private Color sideAreasColor;
    private Color MenuForeGroundColor;
    private Color editorPaneFontColor;
    private Color buttonCloseEnteredColor;
    
    public Constants(){
        //Cores
        this.PaneEditorColor = new Color(51, 51, 51);
        this.sideAreasColor = new Color(82, 82, 82);
        this.MenuBarColor = new Color(28, 28, 28);
        this.MenuForeGroundColor = new Color(137, 163, 201);
        this.editorPaneFontColor = new Color(191, 191, 191);
        this.buttonCloseEnteredColor = new Color(173, 36, 36);
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
}
