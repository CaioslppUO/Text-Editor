package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.io.InputStreamReader;

import gui.maingui.utilities.Constants;
import gui.maingui.utilities.openfile.OpenFile;
import gui.maingui.utilities.savefile.SaveFile;
import gui.maingui.utilities.newfile.NewFile;
import gui.maingui.utilities.openfolder.OpenFolder;
import gui.maingui.utilities.newfolder.NewFolder;

import gui.extragui.RoundedPanel;

import gui.maingui.secondarypanels.menu.MenuBar;
import gui.maingui.secondarypanels.menu.ListenerMenu;
import gui.maingui.secondarypanels.editorpanel.ListenerEditorPanel;
import gui.maingui.secondarypanels.editorpanel.ListenerEditorPanelConfig;
import gui.maingui.secondarypanels.filesview.FilesPanelListener;
import gui.maingui.secondarypanels.filesview.fFile;
import gui.maingui.secondarypanels.filesystemview.SystemFilePanel;
import gui.maingui.secondarypanels.filesystemview.SystemFilePanelListener;
import gui.maingui.secondarypanels.editorpanel.EditorPane;
import gui.maingui.secondarypanels.editorpanel.EditorPaneConfig;
import gui.maingui.secondarypanels.filesview.FilesPanel;
import gui.maingui.interfacegenerator.fFilePanelGenerator;
import gui.maingui.secondarypanels.filesview.FilesView;
import gui.maingui.entities.gFile;

public class Gui {
    // Frame e Painéis principais
    private JFrame frame;
    private JPanel panelCentral;
    private JPanel panelLeft;
    private JPanel panelTop;
    private JPanel panelDown;
    private JPanel panelRight;

    // Variáveis "globais"
    private String currentFolder;
    private JSplitPane centralSplitPane;

    // Fonte
    private Integer fontSize;
    private String fontType;

    // Gerenciadores de interface
    private EditorPane editorPane;
    private EditorPaneConfig editorPaneConfig;
    private FilesPanel filesPanel;
    private SystemFilePanel systemView;

    // Listeners
    private ListenerGui listenerGui;
    private ListenerMenu listenerMenu;
    private ListenerEditorPanel listenerEditorPanel;
    private ListenerEditorPanelConfig listenerEditorPanelConfig;
    private FilesPanelListener listenerFilesPanel;
    private SystemFilePanelListener systemFilePanelListener;

    // Variável utilizada para guardar a única instância da classe
    private static Gui instance;

    // Construtor da classe
    private Gui() {
        // Variáveis globais
        this.currentFolder = System.getProperty("user.dir");

        // Fonte padrão
        this.fontSize = 12;
        this.fontType = "Monospaced";

        // Gerenciadores
        this.systemView = new SystemFilePanel();

        // Listeners
        this.listenerGui = new ListenerGui();
        this.listenerMenu = new ListenerMenu();
        this.listenerEditorPanel = new ListenerEditorPanel();
        this.listenerEditorPanelConfig = new ListenerEditorPanelConfig();
        this.listenerFilesPanel = new FilesPanelListener();
        this.systemFilePanelListener = new SystemFilePanelListener();

        // Iniciando os componentes visuais
        initialize();
    }

    // Função que inicializa todos os componentes visuais da interface
    private void initialize() {
        // Definições dos elementos principais da tela
        this.defineMainFrame();
        this.definePanelTop();
        this.definePanelLeft();
        this.definePanelCentral();
        this.definePanelRight();
        this.definePanelDown();

        // Definições dos elementos secundários de cada espaço na tela
        this.includeFilesPanel();
        this.includeEditorPane();
        this.includeMenuBar();
        this.includeSystemFileView();

        // Definições finais do Main Frame
        this.frame.setBackground(Color.LIGHT_GRAY);
        this.frame.setBounds(100, 100, 450, 300);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Getter do frame principal
    public JFrame getMainFrame() {
        return this.frame;
    }

    // ***********************
    // * Funções de inclusão *
    // ***********************
    // Função que inclui a barra de menu na interface
    private void includeMenuBar() {
        new MenuBar(this.listenerMenu, this.frame);
    }

    // Função que inclui o painel de arquivos abertos à interface
    private void includeFilesPanel() {
        this.filesPanel = new FilesPanel(this.panelCentral);
    }

    // Função que inclui o editroPane na interface
    private void includeEditorPane() {
        this.editorPane = new EditorPane(this.fontType, this.fontSize, this.panelCentral, this.listenerEditorPanel);
        this.decideEditorEnabled(false);
    }

    // Função que inclui a tela de configuração do editorPane na interface
    public void includeEditorPaneConfig() {
        this.editorPaneConfig = new EditorPaneConfig(this.fontType, this.fontSize, this.listenerEditorPanelConfig);
    }

    // Função que inclui a tela de diretórios abertos
    private void includeSystemFileView() {
        this.runUpdateFileSystemView();
    }

    // ************************
    // * Funções de definição *
    // ************************
    // Função que configura e inicializa o frame principal
    private void defineMainFrame() {
        this.frame = new JFrame("Text Editor");
        this.frame.getContentPane().setBackground(Constants.getInstance().getSideAreasColor());
        this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
        this.frame.addKeyListener(this.listenerGui);
        this.frame.setFocusable(true);

        this.centralSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.centralSplitPane.setDividerSize(3);
        this.centralSplitPane.resetToPreferredSizes();

        this.frame.getContentPane().add(this.centralSplitPane, BorderLayout.CENTER);
    }

    // Função que configura e inicializa o painel central
    private void definePanelCentral() {
        this.panelCentral = new JPanel();
        this.panelCentral.setBackground(Constants.getInstance().getPaneEditorColor());
        this.centralSplitPane.add(this.panelCentral);

        GridBagLayout gbl_panelCentral = new GridBagLayout();
        gbl_panelCentral.columnWidths = new int[] { 0, 0 };
        gbl_panelCentral.rowHeights = new int[] { 18, 0, 0 };
        gbl_panelCentral.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_panelCentral.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        this.panelCentral.setLayout(gbl_panelCentral);
    }

    // Função que configura e inicializa o painel da esquerda
    private void definePanelLeft() {
        this.panelLeft = new JPanel();
        this.panelLeft.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.panelLeft.setBackground(Constants.getInstance().getSideAreasColor());
        this.panelLeft.setLayout(new BorderLayout());
        this.centralSplitPane.add(this.panelLeft);
    }

    // Função que configura e inicializa o painel do topo
    private void definePanelTop() {
        this.panelTop = new JPanel();
        this.panelTop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.panelTop.setBackground(Constants.getInstance().getSideAreasColor());
        this.frame.getContentPane().add(this.panelTop, BorderLayout.NORTH);

        Component verticalStrut = Box.createVerticalStrut(50);

        this.panelTop.add(verticalStrut);
    }

    // Função que configura e inicializa o painel de baixo
    private void definePanelDown() {
        this.panelDown = new JPanel();
        this.panelDown.setBackground(Constants.getInstance().getSideAreasColor());
        this.frame.getContentPane().add(this.panelDown, BorderLayout.SOUTH);
    }

    // Função que configura e inicializa o painel da direita
    private void definePanelRight() {
        this.panelRight = new JPanel();
        this.panelRight.setBackground(Constants.getInstance().getSideAreasColor());
        this.frame.getContentPane().add(this.panelRight, BorderLayout.EAST);
    }

    // *************************
    // * Funções de auxiliares *
    // *************************
    // Função que fecha o arquivo que está aberto no visualisador de arquivos
    public void closeFile(String fileToClose) {
        if (fileToClose.equals(gFile.getInstance().getFullPath())) {
            if (gFile.getInstance().isOpen()) {
                int result;
                if (gFile.getInstance().isSaved())
                    result = JOptionPane.NO_OPTION;
                else
                    result = JOptionPane.showConfirmDialog(null, "Save File?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                if (result != JOptionPane.CANCEL_OPTION) {
                    FilesView.getInstance().removefFile(gFile.getInstance().getFullPath());
                    this.filesPanel.updateFiles();
                    SwingUtilities.updateComponentTreeUI(this.panelCentral);

                    // Tenta abrir o próximo arquivo, aberto anteriormente, se existir
                    try { // Existe arquivo para abrir
                        this.runOpenFile(((fFile) FilesView.getInstance().getfFiles().values().toArray()[0]).getFilePath());
                    } catch (Exception e) { // Não existe arquivo para abrir
                        if (result == JOptionPane.YES_OPTION) { // Salva o arquivo atual antes de fechar
                            SaveFile.getInstance().saveFile(false, false);
                        }
                        gFile.getInstance().closeFile();
                        this.decideEditorEnabled(false);
                    }
                }
            }
        } else {
            FilesView.getInstance().removefFile(fileToClose);
            this.filesPanel.updateFiles();
            SwingUtilities.updateComponentTreeUI(this.panelCentral);

            // Tenta abrir o próximo arquivo, aberto anteriormente, se existir
            try { // Existe arquivo para abrir
                this.runOpenFile(((fFile) FilesView.getInstance().getfFiles().values().toArray()[0]).getFilePath());
            } catch (Exception e) { // Não existe arquivo para abrir
                this.decideEditorEnabled(true);
            }
        }
    }

    // Função que atualiza a fonte utilizada no editor
    public void updateFont() {
        this.fontSize = Integer.parseInt(this.editorPaneConfig.getFontSizeSpinner().getValue().toString());
        this.fontType = this.editorPaneConfig.getFontTypeList().getSelectedItem().toString();
        this.editorPane.getEditorPane().setFont(new Font(this.fontType, Font.PLAIN, this.fontSize));
    }

    // Função que decide se o editor de texto vai estar habilitado ou não
    private void decideEditorEnabled(Boolean forceDisabled) {
        if (forceDisabled) {
            this.editorPane.getEditorPane().setEnabled(false);
            this.editorPane.getEditorPane().setText("");
        } else {
            if (gFile.getInstance().isOpen()) {
                this.editorPane.getEditorPane().setEnabled(true);
                if (!FilesView.getInstance().isInside(gFile.getInstance().getFullPath())) {
                    this.createNewFileOpenPanel();
                }else{
                    FilesView.getInstance().selectFile(gFile.getInstance().getFullPath());
                    this.filesPanel.updateFiles();
                }
            } else {
                this.editorPane.getEditorPane().setEnabled(false);
                this.editorPane.getEditorPane().setText("");
            }
        }
    }

    // Função que adiciona um novo painel ao visualisador de arquivos abertos
    private void createNewFileOpenPanel() {
        RoundedPanel rPanel = fFilePanelGenerator.createNewFileOpenPanel(gFile.getInstance().getName(),
                gFile.getInstance().getFullPath(), this.listenerFilesPanel, this.listenerFilesPanel);
        fFile file = new fFile(gFile.getInstance().getName(),gFile.getInstance().getFullPath(),rPanel);
        FilesView.getInstance().addfFile(gFile.getInstance().getFullPath(), file);
        this.filesPanel.updateFiles();
        SwingUtilities.updateComponentTreeUI(this.filesPanel.getFilesPanel());
    }

    // Função que aplica as rotinas para abrir um arquivo
    public void runOpenFile() {
        OpenFile.getInstance().openFile(currentFolder);
        this.decideEditorEnabled(false);
    }

    // Função que aplica as rotinas para abrir um arquivo
    public void runOpenFile(String filePath) {
        OpenFile.getInstance().openFileUsingPath(filePath, this.currentFolder);
        this.decideEditorEnabled(false);
    }

    // Função que executa as rotinas para criar um novo arquivo
    public void runCreateNewFile() {
        NewFile.getInstance().createNewFile(currentFolder);
        this.runUpdateFileSystemView();
        this.decideEditorEnabled(false);
    }

    // Função que abre uma pasta como diretório padrão
    public void runOpenFolder() {
        this.currentFolder = OpenFolder.getInstance().openFolder(this.currentFolder);
        this.runUpdateFileSystemView();
    }

    // Função que roda o arquivo atualmente aberto. Somente em Python3 ou em C
    public void runSelectedFile() {
        Process process;
        if (this.editorPane.getEditorPane().isEnabled()) { // Verifica se o arquivo está aberto
            if (gFile.getInstance().getName().split("[.]")[1].equals("py")) {
                try {
                    process = Runtime.getRuntime().exec("python3 " + gFile.getInstance().getFullPath());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    String inputText = "", aux;
                    while ((aux = reader.readLine()) != null) {
                        inputText += aux;
                    }
                    if (!inputText.equals("")) {
                        JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
                    }
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    inputText = "";
                    while ((aux = reader.readLine()) != null) {
                        inputText += aux;
                    }
                    JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                }
            } else if (gFile.getInstance().getName().split("[.]")[1].equals("c")) {
                try {
                    process = Runtime.getRuntime()
                            .exec("gcc " + gFile.getInstance().getFullPath() + " -o "
                                    + gFile.getInstance().getFile().getParent() + "/"
                                    + gFile.getInstance().getName().split("[.]")[0]);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    String inputText = "", aux;
                    while ((aux = reader.readLine()) != null) {
                        inputText += aux;
                    }
                    if (!inputText.equals("")) {
                        JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (inputText.equals("")) {
                        process = Runtime.getRuntime().exec(gFile.getInstance().getFile().getParent() + "/./"
                                + gFile.getInstance().getName().split("[.]")[0]);
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
    }

    // Função que cria uma nova pasta como diretório padrão
    public void runCreateNewFolder() {
        this.currentFolder = NewFolder.getInstance().createNewFolder(this.currentFolder);
        this.runUpdateFileSystemView();
    }

    // Função que atualiza as pastas e arquivos atualmente abertos
    public void runUpdateFileSystemView() {
        this.systemView.updateFolder(this.currentFolder, this.panelLeft, this.systemView.getSystemFilesPanel(),
                this.systemFilePanelListener);
        SwingUtilities.updateComponentTreeUI(this.panelLeft);
    }

    // Getter da instância do singleton
    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    // Getter da pasta aberta atualmente
    public String getCurrentFolder() {
        return this.currentFolder;
    }

    // Getter do EditorPane
    public EditorPane getEditorPane() {
        return this.editorPane;
    }

    // Getter do panelEditorConfig
    public EditorPaneConfig getEditorPaneConfig() {
        return this.editorPaneConfig;
    }

    // Getter do openFiles
    public FilesPanel getOpenFilesPanel() {
        return this.filesPanel;
    }
}
