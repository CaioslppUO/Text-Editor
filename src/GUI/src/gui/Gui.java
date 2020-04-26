package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import gui.extragui.RoundedPanel;
import gui.maingui.Constants;
import gui.maingui.secondarypanels.editorpanel.EditorPane;
import gui.maingui.secondarypanels.editorpanel.EditorPaneConfig;
import gui.maingui.secondarypanels.openfile.OpenFile;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStreamReader;
import gui.maingui.secondarypanels.openfiles.OpenFiles;
import java.util.LinkedHashMap;
import java.util.Map;
import gui.maingui.secondarypanels.openfiles.CreateNewFileOpenPanel;
import gui.maingui.secondarypanels.savefile.SaveFile;
import gui.maingui.secondarypanels.newfile.NewFile;
import gui.maingui.secondarypanels.openfolder.OpenFolder;

public class Gui implements ActionListener, KeyListener, MouseListener {

    //Frame e Painéis principais
    private JFrame frame;
    private JPanel panelCentral;
    private JPanel panelLeft;
    private JPanel panelTop;
    private JPanel panelDown;
    private JPanel panelRight;

    //Constants
    private Constants constants;

    //Variáveis "globais"
    private File currentFile;
    private String currentFolder;
    private Map<String, RoundedPanel> addedFilesPanel;
    private String lastClickedFilePath;

    //Fonte
    private Integer fontSize;
    private String fontType;

    //Painéis secundários        

    private EditorPane editorPane;
    private EditorPaneConfig editorPaneConfig;

    private OpenFiles openFiles;

    private CreateNewFileOpenPanel createNewFileOpenPanel;

    private SaveFile saveFile;

    private OpenFile openFile;
    
    private NewFile newFile;
    
    private OpenFolder openFolder;

    //Construtor da classe
    public Gui() {
        //Variáveis globais
        this.currentFile = null;
        this.currentFolder = ".";
        this.addedFilesPanel = new LinkedHashMap<>();
        lastClickedFilePath = null;

        //Fonte padrão
        this.fontSize = 12;
        this.fontType = "Monospaced";

        //Constantes
        this.constants = new Constants();

        //Utilitários
        this.saveFile = new SaveFile();

        //Iniciando os componentes visuais
        initialize();
    }

    //Getter do frame principal
    public JFrame getMainFrame() {
        return this.frame;
    }

    //Definições do frame principal
    public void defineMainFrame() {
        this.frame = new JFrame("Text Editor");
        this.frame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
        this.frame.setFocusable(true);
        this.frame.addKeyListener(this);
    }

    //Definições do painel central
    public void definePanelCentral() {
        this.panelCentral = new JPanel();
        this.panelCentral.setBackground(this.constants.getPaneEditorColor());
        this.frame.getContentPane().add(this.panelCentral, BorderLayout.CENTER);

        GridBagLayout gbl_panelCentral = new GridBagLayout();
        gbl_panelCentral.columnWidths = new int[]{0, 0};
        gbl_panelCentral.rowHeights = new int[]{18, 0, 0};
        gbl_panelCentral.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panelCentral.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        this.panelCentral.setLayout(gbl_panelCentral);
    }

    //Definições do painel da esquerda
    private void definePanelLeft() {
        this.panelLeft = new JPanel();
        this.panelLeft.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.panelLeft.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelLeft, BorderLayout.WEST);

        Component horizontalStrut = Box.createHorizontalStrut(110);

        this.panelLeft.add(horizontalStrut);
    }

    //Definições do painel do topo
    private void definePanelTop() {
        this.panelTop = new JPanel();
        this.panelTop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.panelTop.setBackground(this.constants.getSideAreasColor());
        frame.getContentPane().add(this.panelTop, BorderLayout.NORTH);

        Component verticalStrut = Box.createVerticalStrut(50);

        this.panelTop.add(verticalStrut);
    }

    //Definições do painel de baixo
    private void definePanelDown() {
        this.panelDown = new JPanel();
        this.panelDown.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelDown, BorderLayout.SOUTH);
    }

    //Definições do painel da direita
    private void definePanelRight() {
        this.panelRight = new JPanel();
        this.panelRight.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelRight, BorderLayout.EAST);
    }

    //Função que decide se o editor de texto vai estar habilitado ou não baseado se existe ou não um arquivo aberto
    public void decideEditorEnabled(Boolean isFileAlreadyOpen) {
        if (this.currentFile != null) {
            this.editorPane.getEditorPane().setEnabled(true);
            if (!isFileAlreadyOpen) {
                createNewFileOpenPanel(this.currentFile.getName(), this.currentFile.getAbsolutePath());
            }
        } else {
            this.editorPane.getEditorPane().setEnabled(false);
            this.editorPane.getEditorPane().setText("");
        }
    }

    private void createNewFileOpenPanel(String fileName, String filePath) {
        this.createNewFileOpenPanel = new CreateNewFileOpenPanel(this.lastClickedFilePath, this.addedFilesPanel);
        this.createNewFileOpenPanel.createNewFileOpenPanel(fileName, filePath);
        this.createNewFileOpenPanel.getNewFilePanel().addMouseListener(this);
        this.createNewFileOpenPanel.getCloseButton().addActionListener(this);
        this.createNewFileOpenPanel.getCloseButton().addMouseListener(this);

        this.openFiles.getOpenFilesPanel().add(this.createNewFileOpenPanel.getNewFilePanel());
        this.openFiles.getOpenFilesPanel().add(this.createNewFileOpenPanel.getSeparator());

        this.addedFilesPanel = this.createNewFileOpenPanel.getAddedFilesPanel();
        this.lastClickedFilePath = this.createNewFileOpenPanel.getLastClickedFilePath();

        this.createNewFileOpenPanel = null;

        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void includeOpenFilesPanel() {
        this.openFiles = new OpenFiles();
        this.panelCentral.add(this.openFiles.getOpenFilesPanel(), this.openFiles.getGbc());
    }

    private void includeEditorPane() {
        this.editorPane = new EditorPane(this.fontType, this.fontSize);
        this.editorPane.getEditorPane().addKeyListener(this);
        this.decideEditorEnabled(false);
        this.panelCentral.add(this.editorPane.getScrollPane(), this.editorPane.getGbc());
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
        menuItem.addActionListener(this);
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
    private void defineMenuBar() {
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
        this.frame.setJMenuBar(menuBar);
    }

    private void runOpenFile() {
        this.openFile = new OpenFile(this.currentFile, this.editorPane.getEditorPane(), this.addedFilesPanel, this.currentFolder);
        this.openFile.openFile();
        this.currentFile = this.openFile.getCurrentFile();
        this.currentFolder = this.openFile.getCurrentFolder();
        this.addedFilesPanel = this.openFile.getAddedFilesPanel();
        this.editorPane.setEditorPane(this.openFile.getEditorPane());
        this.openFile = null;
        if (this.addedFilesPanel.get(this.currentFile.getAbsolutePath()) != null) {
            this.decideEditorEnabled(true);
        } else {
            this.decideEditorEnabled(false);
        }
    }

    private void runOpenFile(String filePath) {
        this.openFile = new OpenFile(this.currentFile, this.editorPane.getEditorPane(), this.addedFilesPanel, this.currentFolder);
        this.openFile.openFile(filePath);
        this.currentFile = this.openFile.getCurrentFile();
        this.currentFolder = this.openFile.getCurrentFolder();
        this.addedFilesPanel = this.openFile.getAddedFilesPanel();
        this.editorPane.setEditorPane(this.openFile.getEditorPane());
        this.openFile = null;
        if (this.addedFilesPanel.get(this.currentFile.getAbsolutePath()) != null) {
            this.decideEditorEnabled(true);
        } else {
            this.decideEditorEnabled(false);
        }
    }

    private void includeEditorPaneConfig() {
        this.editorPaneConfig = new EditorPaneConfig(this.fontType, this.fontSize);
        this.editorPaneConfig.getOkButton().addActionListener(this);
        this.editorPaneConfig.getEditorConfigFrame().addKeyListener(this);
    }

    //Função que atualiza a fonte utilizada no editor
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: A fonte é atualizada para as opções definidas no menu
    private void updateFont() {
        this.editorPaneConfig.getEditorConfigFrame().dispose();
        this.fontSize = Integer.parseInt(this.editorPaneConfig.getFontSizeSpinner().getValue().toString());
        this.fontType = this.editorPaneConfig.getFontTypeList().getSelectedItem().toString();
        this.editorPane.getEditorPane().setFont(new Font(this.fontType, Font.PLAIN, this.fontSize));
    }
    
    public void runCreateNewFile(){
        this.newFile = new NewFile(this.currentFile,this.editorPane.getEditorPane(),this.currentFolder);
        this.newFile.createNewFile();
        this.currentFile = this.newFile.getCurrentFile();
        this.currentFolder = this.newFile.getCurrentFolder();
        this.editorPane.setEditorPane(this.newFile.getEditorPane());
        this.newFile = null;
        this.decideEditorEnabled(false);
    }
    
    public void runOpenFolder(){
        this.openFolder = new OpenFolder();
        this.currentFolder = this.openFolder.openFolder(this.currentFolder);
        this.openFolder = null;
    }

    //Função que roda o arquivo atualmente aberto. Somente em Python3 ou em C
    //Entrada: Nenhuma
    //Retorno: Nenhum
    /*Pré-condição: O arquivo deve ter a extensão .py ou .c. O arquivo deve ser um código fonte escrito em c ou em python3.
         * O sistema Operacional deve ser Linux.
     */
    //Pós-condição: O programa é rodado no bash
    private void runSelectedFile() {
        Process process;
        if (this.currentFile.getName().split("[.]")[1].equals("py")) {
            try {
                process = Runtime.getRuntime().exec("python3 " + this.currentFile.getAbsolutePath());
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String inputText = "", aux;
                while ((aux = reader.readLine()) != null) {
                    inputText += aux;
                }
                JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                inputText = "";
                while ((aux = reader.readLine()) != null) {
                    inputText += aux;
                }
                JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
            }
        } else if (this.currentFile.getName().split("[.]")[1].equals("c")) {
            try {
                process = Runtime.getRuntime().exec("gcc " + this.currentFile.getAbsolutePath() + " -o " + this.currentFile.getParent() + "/" + this.currentFile.getName().split("[.]")[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String inputText = "", aux;
                while ((aux = reader.readLine()) != null) {
                    inputText += aux;
                }
                if (!inputText.equals("")) {
                    JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
                }
                if (inputText.equals("")) {
                    process = Runtime.getRuntime().exec(this.currentFile.getParent() + "/./" + this.currentFile.getName().split("[.]")[0]);
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    inputText = "";
                    while ((aux = reader.readLine()) != null) {
                        inputText += aux;
                    }
                    JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e) {
            }
        }
    }

    //Função que fecha o arquivo que está aberto
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo aberto atualmente é fechado
    private void closeFile() {
        if (this.currentFile != null) {
            int result = JOptionPane.showConfirmDialog(null, "Save File?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            RoundedPanel aux = this.addedFilesPanel.remove(this.currentFile.getAbsolutePath());
            this.openFiles.getOpenFilesPanel().remove(aux);
            SwingUtilities.updateComponentTreeUI(frame);

            try {
                this.runOpenFile(((RoundedPanel) this.addedFilesPanel.values().toArray()[0]).getName());
            } catch (Exception e) {
                if (result == 0) {
                    this.saveFile.saveFile(false, this.currentFile, this.editorPane.getEditorPane().getText());
                }
                this.currentFile = null;
                this.lastClickedFilePath = null;
                this.decideEditorEnabled(false);
            }
        }
    }

    //Responde aos botões pressionados na interface
    @Override
    public void actionPerformed(ActionEvent e) {
        if (null != e.getActionCommand()) {
            switch (e.getActionCommand()) {
                case "buttonThemesPressed":
                    JOptionPane.showMessageDialog(null, "Button Themes Pressed");
                    break;
                case "buttonEditorPressed":
                    this.includeEditorPaneConfig();
                    break;
                case "buttonNewFilePressed":
                    this.runCreateNewFile();
                    break;
                case "FontSizeChanged":
                    this.updateFont();
                    break;
                case "buttonSaveAsPressed":
                    this.saveFile.saveFileAs(this.currentFile, this.currentFolder, this.editorPane.getEditorPane().getText());
                    break;
                case "buttonOpenFilePressed":
                    this.runOpenFile();
                    break;
                case "buttonSavePressed":
                    this.saveFile.saveFile(true, this.currentFile, this.editorPane.getEditorPane().getText());
                    break;
                case "buttonCloseFilePressed":
                    this.closeFile();
                    break;
                case "buttonRunPressed":
                    this.runSelectedFile();
                    break;
                case "buttonOpenFolderPressed":
                    this.runOpenFolder();
                    break;
                default:
                    break;
            }
        }
    }

    //Função que inicializa todos os componentes visuais da interface
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: Todas as interfaces são inicializadas
    private void initialize() {
        //Definições dos elementos principais da tela
        this.defineMainFrame();
        this.definePanelTop();
        this.definePanelCentral();
        this.definePanelLeft();
        this.definePanelRight();
        this.definePanelDown();

        //Definições dos elementos secundários de cada espaço na tela
        this.includeOpenFilesPanel();
        this.includeEditorPane();
        this.defineMenuBar();

        //Definições finais do Main Frame
        this.frame.setBackground(Color.LIGHT_GRAY);
        this.frame.setBounds(100, 100, 450, 300);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    //Verifica as teclas apertadas na interface
    @Override
    public void keyReleased(KeyEvent e) {
        //Salva o arquivo aberto ao apertar ctrl+s
        if (this.editorPane.getEditorPane() != null && e.isShiftDown() == false && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
            this.saveFile.saveFile(true, this.currentFile, this.editorPane.getEditorPane().getText());
        }

        //Salva como o arquivo ao apertar ctrl+shift+s
        if (this.editorPane.getEditorPane() != null && e.isShiftDown() && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
            this.saveFile.saveFileAs(this.currentFile, this.currentFolder, this.editorPane.getEditorPane().getText());
        }

        //Resposta do botão OK da tela de configuração do editor
        if (this.editorPaneConfig != null && this.editorPaneConfig.getEditorConfigFrame().isActive() && e.getKeyCode() == 27) {
            this.updateFont();
        }

        //Abre um arquivo ao apertar ctrl+o
        if (e.isControlDown() && e.getKeyChar() != 'o' && e.getKeyCode() == 79) {
            this.runOpenFile();
        }

        //Cria um novo arquivo ao apertar ctr+n
        if (e.isControlDown() && e.getKeyChar() != 'n' && e.getKeyCode() == 78) {
            this.runCreateNewFile();
        }

        //Executa um programa em python ou em c
        if (this.editorPane.getEditorPane() != null && e.isControlDown() && e.getKeyChar() != 'r' && e.getKeyCode() == 82) {
            this.runSelectedFile();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (!"closeButton".equals(me.getComponent().getName())) {
            this.runOpenFile(me.getComponent().getName());
            if (this.lastClickedFilePath == null) {
                for (Component c : ((RoundedPanel) me.getComponent()).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(Color.WHITE);
                    }
                }
                SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
                this.lastClickedFilePath = me.getComponent().getName();
            } else {
                for (Component c : ((RoundedPanel) this.addedFilesPanel.get(this.lastClickedFilePath)).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(this.constants.getMenuForeGroundColor());
                    }
                }
                SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
                for (Component c : ((RoundedPanel) me.getComponent()).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(Color.WHITE);
                    }
                }
                SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
                this.lastClickedFilePath = me.getComponent().getName();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if ("closeButton".equals(me.getComponent().getName())) {
            ((JButton) me.getComponent()).setBackground(this.constants.getButtonCloseEnteredColor());
            ((JButton) me.getComponent()).setForeground(this.constants.getEditorPaneFontColor());
            SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
        }
        if (!"closeButton".equals(me.getComponent().getName())) {
            ((RoundedPanel) me.getComponent()).setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if ("closeButton".equals(me.getComponent().getName())) {
            ((JButton) me.getComponent()).setBackground(null);
            ((JButton) me.getComponent()).setForeground(null);
            SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
        }
        if (!"closeButton".equals(me.getComponent().getName())) {
            ((RoundedPanel) me.getComponent()).setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
            SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
        }
    }
}
