package gui.maingui.secondarypanels.menu;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EtchedBorder;
import gui.maingui.Constants;
import java.awt.event.ActionListener;

public class MenuBar {
    private Constants constants;
    private ActionListener listener;
    
    public MenuBar(ActionListener listener){
        this.constants = new Constants();
        this.listener = listener;
    }
    
    //Cria e retorna um sub item de menu
    //Entrada: Nome do sub menu e comando ativado ao clicar no sub menu gerado
    //Retorno: Menu Item gerado
    //Pŕe-condição: Nenhuma
    //Pós-condição: O Menu Item é gerado e retornado
    private JMenuItem createMenuItem(String name, String actionCommand, String toolTip) {
        JMenuItem menuItem;
        menuItem = new JMenuItem(name);
        menuItem.setForeground(this.constants.getMenuForeGroundColor());
        menuItem.setBackground(this.constants.getSideAreasColor());
        menuItem.setToolTipText(toolTip);
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(this.listener);
        return menuItem;
    }

    //Cria e retorna um menu
    //Entrada: Nome do menu e array contendo todos os JMenuItems do menu
    //Retorno: Menu gerado
    //Pŕe-condição: Nenhuma
    //Pós-condição: O Menu é gerado e retornado
    private JMenu createMenu(String name, JMenuItem[] itens) {
        JMenu menu;
        menu = new JMenu(name);
        menu.setBackground(this.constants.getSideAreasColor());
        menu.setForeground(this.constants.getMenuForeGroundColor());
        for (JMenuItem j : itens) {
            menu.add(j);
        }
        return menu;
    }

    //Definições da barra de menu
    public JMenuBar defineMenuBar() {
        JMenuBar menuBar;
        //Inicializando a barra do menu
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        menuBar.setBackground(this.constants.getMenuBarColor());

        //Botão File
        menuBar.add(
                this.createMenu(
                        "File", //Menu File
                        new JMenuItem[]{
                            this.createMenuItem("New File", "buttonNewFilePressed", "ctrl+n"), //Sub botão new file
                            this.createMenuItem("Open File", "buttonOpenFilePressed", "ctrl+o"), //Sub botão open file
                            this.createMenuItem("Open Folder", "buttonOpenFolderPressed", "Open a folder"),
                            this.createMenuItem("Run Current File", "buttonRunPressed", "ctlr+r. C or Python only. Output Only")
                        }
                )
        );

        //Botão save
        menuBar.add(
                this.createMenu(
                        "Save", //Menu Save
                        new JMenuItem[]{
                            this.createMenuItem("Save as", "buttonSaveAsPressed", "ctrl+shift+s"), //Sub botão Save as
                            this.createMenuItem("Save", "buttonSavePressed", "ctrl+s") //Sub botão Save
                        }
                )
        );

        //Botão settings
        menuBar.add(
                this.createMenu(
                        "Settings", //Menu Settings
                        new JMenuItem[]{
                            this.createMenuItem("Editor", "buttonEditorPressed", ""), //Sub botão Editor
                            this.createMenuItem("Themes", "buttonThemesPressed", "") //Sub botão Themes
                        }
                )
        );

        //Adicionando a menuBar à interface
        
        return menuBar;
    }
}
