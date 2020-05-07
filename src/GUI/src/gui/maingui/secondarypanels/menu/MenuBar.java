package gui.maingui.secondarypanels.menu;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EtchedBorder;
import gui.maingui.utilities.Constants;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class MenuBar {
    
    //Construtor da barra de menu
    //Entrada: Listener que irá tratar as ações executadas no menu
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    //Pós-condição: A classe é instanciada
    public MenuBar(ActionListener listener, JFrame menuWrap){
        this.defineMenuBar(listener,menuWrap);
    }
    
    //Cria e retorna um sub item de menu
    //Entrada: Nome do sub menu e comando ativado ao clicar no sub menu gerado
    //Retorno: Menu Item gerado
    //Pŕe-condição: Nenhuma
    //Pós-condição: O Menu Item é gerado e retornado
    private JMenuItem createMenuItem(String name, String actionCommand, String toolTip, ActionListener listener) {
        JMenuItem menuItem;
        menuItem = new JMenuItem(name);
        menuItem.setForeground(Constants.getInstance().getMenuForeGroundColor());
        menuItem.setBackground(Constants.getInstance().getSideAreasColor());
        menuItem.setToolTipText(toolTip);
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(listener);
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
        menu.setBackground(Constants.getInstance().getSideAreasColor());
        menu.setForeground(Constants.getInstance().getMenuForeGroundColor());
        for (JMenuItem j : itens) {
            menu.add(j);
        }
        return menu;
    }

    //Função que define a barra do menu
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    //Pós-condição: A barra de menu é gerada, configurada e adcionada ao menuWrap
    public void defineMenuBar(ActionListener listener, JFrame menuWrap) {
        JMenuBar menuBar;
        //Inicializando a barra do menu
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        menuBar.setBackground(Constants.getInstance().getMenuBarColor());

        //Botão File
        menuBar.add(
                this.createMenu(
                        "File", //Menu File
                        new JMenuItem[]{
                            this.createMenuItem("New File", "buttonNewFilePressed", "ctrl+n",listener), //Sub botão new file
                            this.createMenuItem("Open File", "buttonOpenFilePressed", "ctrl+o",listener), //Sub botão open file
                            this.createMenuItem("New Folder", "buttonNewFolderPressed", "Create a new folder",listener),
                            this.createMenuItem("Open Folder", "buttonOpenFolderPressed", "Open a folder",listener),
                            this.createMenuItem("Run Current File", "buttonRunPressed", "ctlr+r. C or Python only. Output Only",listener)
                        }
                )
        );

        //Botão save
        menuBar.add(
                this.createMenu(
                        "Save", //Menu Save
                        new JMenuItem[]{
                            this.createMenuItem("Save as", "buttonSaveAsPressed", "ctrl+shift+s",listener), //Sub botão Save as
                            this.createMenuItem("Save", "buttonSavePressed", "ctrl+s",listener) //Sub botão Save
                        }
                )
        );

        //Botão settings
        menuBar.add(
                this.createMenu(
                        "Settings", //Menu Settings
                        new JMenuItem[]{
                            this.createMenuItem("Editor", "buttonEditorPressed", "",listener), //Sub botão Editor
                            this.createMenuItem("Themes", "buttonThemesPressed", "",listener) //Sub botão Themes
                        }
                )
        );

        //Adicionando a menuBar à interface
        
        menuWrap.setJMenuBar(menuBar);
    }
}
