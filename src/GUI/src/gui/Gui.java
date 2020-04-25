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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStreamReader;
import gui.maingui.secondarypanels.openfiles.OpenFiles;
import java.util.LinkedHashMap;
import java.util.Map;
import gui.maingui.secondarypanels.openfiles.CreateNewFileOpenPanel;
import gui.maingui.secondarypanels.savefiles.SaveFile;

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
    private JDialog saveFileFrame;
    private JFileChooser chooseSaveDirectory;
    
    private JDialog openFileFrame;
    private JFileChooser chooseOpenFile;
    
    private JDialog createNewFileFrame;
    private JFileChooser chooseNewFileDirectory;
    
    private JDialog openFolderFrame;
    private JFileChooser chooseOpenDirectory;
    
    //
    
    private EditorPane editorPane;
    private EditorPaneConfig editorPaneConfig;
    
    private OpenFiles openFiles;
    
    private CreateNewFileOpenPanel createNewFileOpenPanel;
    
    private SaveFile saveFile;
    
    //Construtor da classe
    public Gui() {
        //Constantes
        this.constants = new Constants();
        
        //Utilitários
        this.saveFile = new SaveFile();
        
        //Variáveis globais
        this.currentFile = null;
        this.currentFolder = ".";
        this.addedFilesPanel = new LinkedHashMap<>();
        lastClickedFilePath = null;
        
        //Fonte padrão
        this.fontSize = 12;
        this.fontType = "Monospaced";
        
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
                createNewFileOpenPanel(this.currentFile .getName(), this.currentFile .getAbsolutePath());
            }
        } else {
            this.editorPane.getEditorPane().setEnabled(false);
            this.editorPane.getEditorPane().setText("");
        }
    }
    
    private void createNewFileOpenPanel(String fileName, String filePath){
        this.createNewFileOpenPanel = new CreateNewFileOpenPanel(this.lastClickedFilePath, this.addedFilesPanel);
        this.createNewFileOpenPanel.createNewFileOpenPanel(fileName, filePath);
        this.createNewFileOpenPanel.getNewFilePanel().addMouseListener(this);
        this.createNewFileOpenPanel.getCloseButton().addActionListener(this);
        this.createNewFileOpenPanel.getCloseButton().addMouseListener(this);
        
        this.openFiles.getOpenFilesPanel().add(this.createNewFileOpenPanel.getNewFilePanel());
        this.openFiles.getOpenFilesPanel().add(this.createNewFileOpenPanel.getSeparator());
        
        this.addedFilesPanel = this.createNewFileOpenPanel.getAddedFilesPanel();
        this.lastClickedFilePath = this.createNewFileOpenPanel.getLastClickedFilePath();
        
        SwingUtilities.updateComponentTreeUI(frame);
    }
    
    private void includeOpenFilesPanel(){
        this.openFiles = new OpenFiles();
        this.panelCentral.add(this.openFiles.getOpenFilesPanel(), this.openFiles.getGbc());
    }
    
    private void includeEditorPane(){
        this.editorPane = new EditorPane(this.fontType,this.fontSize);
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

    private void includeEditorPaneConfig(){
        this.editorPaneConfig = new EditorPaneConfig(this.fontType,this.fontSize);
        this.editorPaneConfig.getOkButton().addActionListener(this);
        this.editorPaneConfig.getEditorConfigFrame().addKeyListener(this);
    }

    //Função que define o frame de salvar um arquivo
    //Entrada: Nenhuma
    //Retorno: FileChooser.APPROVE_OPTION se o diretório for escolhido ou o contrário caso não seja
    //Pré-condição: Nenhuma
    //Pós-condição: Nenhuma
    private int defineCreateOpenFileFrame() {
        this.openFileFrame = new JDialog();
        this.openFileFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.openFileFrame.setSize(600, 600);
        this.openFileFrame.setLocationRelativeTo(null);
        this.openFileFrame.setTitle("Save File");

        chooseOpenFile = new JFileChooser();
        chooseOpenFile.setCurrentDirectory(new File(this.currentFolder));
        chooseOpenFile.setDialogTitle("Save to");
        chooseOpenFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        return chooseOpenFile.showOpenDialog(null);
    }

    //Função que abre um menu para escolher um arquivo para abrir
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo é aberto no editor
    private void openFile() {
        if (defineCreateOpenFileFrame() == JFileChooser.APPROVE_OPTION) { //Abre o arquivo
            File file = new File(chooseOpenFile.getSelectedFile().toString());
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String contentToLoad = "", aux;
                try {
                    while ((aux = br.readLine()) != null) {
                        contentToLoad += aux;
                        contentToLoad += "\n";
                    }
                    this.editorPane.getEditorPane().setText(contentToLoad);
                    this.currentFile = file;
                    if (this.addedFilesPanel.get(this.currentFile.getAbsolutePath()) != null) {
                        this.decideEditorEnabled(true);
                    } else {
                        this.decideEditorEnabled(false);
                    }
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

    //Função que abre um arquivo dado um caminho
    //Entrada: Caminho do arquivo a ser aberto
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo é aberto no editor
    private void openFile(String filePath) {
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String contentToLoad = "", aux;
            try {
                while ((aux = br.readLine()) != null) {
                    contentToLoad += aux;
                    contentToLoad += "\n";
                }
                this.saveFile.saveFile(false,this.currentFile,this.editorPane.getEditorPane().getText());
                this.editorPane.getEditorPane().setText(contentToLoad);
                this.currentFile = file;
                if (this.addedFilesPanel.get(this.currentFile.getAbsolutePath()) != null) {
                    this.decideEditorEnabled(true);
                } else {
                    this.decideEditorEnabled(false);
                }
            } catch (IOException e) {
                //do Nothing

            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error while trying to load file");
        }
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

    //Função que define o frame de criar um novo arquivo
    //Entrada: Nenhuma
    //Retorno: FileChooser.APPROVE_OPTION se o diretório for escolhido ou o contrário caso não seja
    //Pré-condição: Nenhuma
    //Pós-condição: Nenhuma
    private int defineCreateFileFrame() {
        this.createNewFileFrame = new JDialog();
        this.createNewFileFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.createNewFileFrame.setSize(600, 600);
        this.createNewFileFrame.setLocationRelativeTo(null);
        this.createNewFileFrame.setTitle("Save File As");

        this.chooseNewFileDirectory = new JFileChooser();
        this.chooseNewFileDirectory.setCurrentDirectory(new File(this.currentFolder));
        this.chooseNewFileDirectory.setDialogTitle("Save to");
        this.chooseNewFileDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return chooseNewFileDirectory.showOpenDialog(null);
    }

    //Função que cria um novo arquivo caso ele já não exista
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo é criado e salvo no disco
    private void createNewFile() {
        if (this.currentFile == null) {
            String fileSeparator = System.getProperty("file.separator");
            if (defineCreateFileFrame() == JFileChooser.APPROVE_OPTION) { //Salvar o arquivo
                File directory = this.chooseNewFileDirectory.getSelectedFile();
                String fileName;
                fileName = JOptionPane.showInputDialog("File Name");
                if (fileName != null) {
                    File newFile = new File(directory.toString() + fileSeparator + fileName);
                    try {
                        if (newFile.createNewFile()) {
                            JOptionPane.showMessageDialog(null, "File Created");
                            this.currentFile = newFile;
                            this.editorPane.getEditorPane().setText("");
                            this.decideEditorEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "File Already Exists");
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error while trying to create the file");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid File Name");
                }
            }
            this.createNewFileFrame.getContentPane().add(chooseNewFileDirectory);

            this.createNewFileFrame.setVisible(true);
            this.createNewFileFrame.dispose();
        } else {
            this.currentFile = null;
            this.createNewFile();
        }
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
                this.openFile(((RoundedPanel) this.addedFilesPanel.values().toArray()[0]).getName());
            } catch (Exception e) {
                if (result == 0) {
                    this.saveFile.saveFile(false,this.currentFile,this.editorPane.getEditorPane().getText());
                }
                this.currentFile = null;
                this.lastClickedFilePath = null;
                this.decideEditorEnabled(false);
            }
        }
    }

    //Função que define o frame da janela de abrir pastas
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    //Pós-condição: A janela de escolha de pasta é criada e exposta
    private int defineOpenFolderFrame() {
        this.openFolderFrame = new JDialog();
        this.openFolderFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.openFolderFrame.setSize(600, 600);
        this.openFolderFrame.setLocationRelativeTo(null);
        this.openFolderFrame.setTitle("Open Folder");

        this.chooseOpenDirectory = new JFileChooser();
        this.chooseOpenDirectory.setCurrentDirectory(new File(this.currentFolder));
        this.chooseOpenDirectory.setDialogTitle("Open Folder");
        this.chooseOpenDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.openFolderFrame.getContentPane().add(chooseOpenDirectory);
        this.openFolderFrame.setVisible(true);
        this.openFolderFrame.dispose();
        return chooseOpenDirectory.showOpenDialog(null);
    }

    //Função que abre uma pasta, a qual será o diretório padrão
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    //Pós-condição: A pasta é aberta e vira o diretório padrão
    private void openFolder() {
        if (this.defineOpenFolderFrame() == JFileChooser.APPROVE_OPTION) {
            this.currentFolder = this.chooseOpenDirectory.getSelectedFile().getAbsolutePath();
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
                    this.createNewFile();
                    break;
                case "FontSizeChanged":
                    this.updateFont();
                    break;
                case "buttonSaveAsPressed":
                    this.saveFile.saveFileAs(this.currentFile,this.currentFolder,this.editorPane.getEditorPane().getText());
                    break;
                case "buttonOpenFilePressed":
                    this.openFile();
                    break;
                case "buttonSavePressed":
                    this.saveFile.saveFile(true,this.currentFile,this.editorPane.getEditorPane().getText());
                    break;
                case "buttonCloseFilePressed":
                    this.closeFile();
                    break;
                case "buttonRunPressed":
                    this.runSelectedFile();
                    break;
                case "buttonOpenFolderPressed":
                    this.openFolder();
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
            this.saveFile.saveFile(true,this.currentFile,this.editorPane.getEditorPane().getText());
        }

        //Salva como o arquivo ao apertar ctrl+shift+s
        if (this.editorPane.getEditorPane() != null && e.isShiftDown() && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
            this.saveFile.saveFileAs(this.currentFile,this.currentFolder,this.editorPane.getEditorPane().getText());
        }

        //Resposta do botão OK da tela de configuração do editor
        if (this.editorPaneConfig != null && this.editorPaneConfig.getEditorConfigFrame().isActive() && e.getKeyCode() == 27) {
            this.updateFont();
        }

        //Abre um arquivo ao apertar ctrl+o
        if (e.isControlDown() && e.getKeyChar() != 'o' && e.getKeyCode() == 79) {
            this.openFile();
        }

        //Cria um novo arquivo ao apertar ctr+n
        if (e.isControlDown() && e.getKeyChar() != 'n' && e.getKeyCode() == 78) {
            this.createNewFile();
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
            this.openFile(me.getComponent().getName());
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
