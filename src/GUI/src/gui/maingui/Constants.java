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
        //Definição das cores
        this.PaneEditorColor = new Color(51, 51, 51);
        this.sideAreasColor = new Color(82, 82, 82);
        this.MenuBarColor = new Color(28, 28, 28);
        this.MenuForeGroundColor = new Color(137, 163, 201);
        this.editorPaneFontColor = new Color(191, 191, 191);
        this.buttonCloseEnteredColor = new Color(173, 36, 36);
    }

    //Getter da cor do painel de edição
    public Color getPaneEditorColor() {
        return PaneEditorColor;
    }

    //Setter da cor do painel de edição
    public void setPaneEditorColor(Color PaneEditorColor) {
        this.PaneEditorColor = PaneEditorColor;
    }

    //Getter da cor da menu bar
    public Color getMenuBarColor() {
        return MenuBarColor;
    }

    //Setter da cor da menu bar
    public void setMenuBarColor(Color MenuBarColor) {
        this.MenuBarColor = MenuBarColor;
    }

    //Getter da cor das áreas laterais
    public Color getSideAreasColor() {
        return sideAreasColor;
    }

    //Setter da cor das áreas laterais
    public void setSideAreasColor(Color sideAreasColor) {
        this.sideAreasColor = sideAreasColor;
    }

    //Getter da cor do foreground dos textos do menu
    public Color getMenuForeGroundColor() {
        return MenuForeGroundColor;
    }
    
    //Setter da cor do foreground dos textos do menu
    public void setMenuForeGroundColor(Color MenuForeGroundColor) {
        this.MenuForeGroundColor = MenuForeGroundColor;
    }

    //Getter da cor da fonte do texto do painel de edição
    public Color getEditorPaneFontColor() {
        return editorPaneFontColor;
    }

    //Setter da cor da fonte do texto do painel de edição
    public void setEditorPaneFontColor(Color editorPaneFontColor) {
        this.editorPaneFontColor = editorPaneFontColor;
    }

    //Getter da dor do botão de fechar quando o mouse passa por cima
    public Color getButtonCloseEnteredColor() {
        return buttonCloseEnteredColor;
    }
    
    //Setter da dor do botão de fechar quando o mouse passa por cima
    public void setButtonCloseEnteredColor(Color buttonCloseEnteredColor) {
        this.buttonCloseEnteredColor = buttonCloseEnteredColor;
    }
}
