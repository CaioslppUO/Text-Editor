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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import gui.extragui.RoundedPanel;
import gui.maingui.utilities.Constants;

import gui.maingui.utilities.openfile.OpenFile;
import gui.maingui.utilities.savefile.SaveFile;
import gui.maingui.utilities.newfile.NewFile;
import gui.maingui.utilities.openfolder.OpenFolder;
import gui.maingui.utilities.newfolder.NewFolder;

import gui.maingui.secondarypanels.menu.MenuBar;
import gui.maingui.secondarypanels.menu.ListenerMenu;
import gui.maingui.secondarypanels.editorpanel.ListenerEditorPanel;
import gui.maingui.secondarypanels.editorpanel.ListenerEditorPanelConfig;
import gui.maingui.secondarypanels.openfilespanel.OpenFilesListener;
import gui.maingui.secondarypanels.filesystemview.SystemFilePanel;
import gui.maingui.secondarypanels.filesystemview.SystemFilePanelListener;
import gui.maingui.secondarypanels.editorpanel.EditorPane;
import gui.maingui.secondarypanels.editorpanel.EditorPaneConfig;
import gui.maingui.secondarypanels.openfilespanel.OpenFilesPanel;
import gui.maingui.secondarypanels.openfilespanel.CreateNewFileOpenPanel;

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
    private Map<String, RoundedPanel> addedFilesPanel;
    private String lastClickedFilePath;

    // Fonte
    private Integer fontSize;
    private String fontType;

    // Gerenciador do editorPane
    private EditorPane editorPane;

    // Gerenciador da tela de configuração do editorPane
    private EditorPaneConfig editorPaneConfig;

    // Gerenciador do painel do visualisador de arquivos abertos
    private OpenFilesPanel openFiles;

    // Gerenciador do visualisador de arquivos abertos
    private CreateNewFileOpenPanel createNewFileOpenPanel;

    // Listener da interface principal
    private ListenerGui listenerGui;

    // Listener do menu
    private ListenerMenu listenerMenu;

    // Listener do editorPanel
    private ListenerEditorPanel listenerEditorPanel;

    // Listener do editorPanelConfig
    private ListenerEditorPanelConfig listenerEditorPanelConfig;

    // Listener do openFilesPanel
    private OpenFilesListener listenerOpenFilesPanel;

    // Listener do systemFilePanel
    private SystemFilePanelListener systemFilePanelListener;

    // Variável utilizada para guardar a única instância da classe
    private static Gui instance;

    // Gerenciador do visualisador de diretórios
    private SystemFilePanel systemView;

    // Pane utilizado para redimensionar as janelas
    private JSplitPane splitPane;

    // Construtor da classe
    private Gui() {
        // Variáveis globais
        this.currentFolder = System.getProperty("user.dir");

        this.addedFilesPanel = new LinkedHashMap<>();
        lastClickedFilePath = null;

        // Fonte padrão
        this.fontSize = 12;
        this.fontType = "Monospaced";

        // Utilitários
        this.systemView = new SystemFilePanel();

        // Listeners
        this.listenerGui = new ListenerGui();
        this.listenerMenu = new ListenerMenu();
        this.listenerEditorPanel = new ListenerEditorPanel();
        this.listenerEditorPanelConfig = new ListenerEditorPanelConfig();
        this.listenerOpenFilesPanel = new OpenFilesListener();
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
        this.includeOpenFilesPanel();
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
    private void includeOpenFilesPanel() {
        this.openFiles = new OpenFilesPanel(this.panelCentral);
    }

    // Função que inclui o editroPane na interface
    private void includeEditorPane() {
        this.editorPane = new EditorPane(this.fontType, this.fontSize, this.panelCentral, this.listenerEditorPanel);
        this.decideEditorEnabled(false);
    }

    // Função que inclui a tela de configuração do editorPane na interface
    public void includeEditorPaneConfig() {
        this.editorPaneConfig = new EditorPaneConfig(this.fontType, this.fontSize);
        this.editorPaneConfig.getOkButton().addActionListener(this.listenerEditorPanelConfig);
        this.editorPaneConfig.getEditorConfigFrame().addKeyListener(this.listenerEditorPanelConfig);
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

        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerSize(3);
        this.splitPane.resetToPreferredSizes();

        this.frame.getContentPane().add(this.splitPane, BorderLayout.CENTER);
    }

    // Função que configura e inicializa o painel central
    private void definePanelCentral() {
        this.panelCentral = new JPanel();
        this.panelCentral.setBackground(Constants.getInstance().getPaneEditorColor());
        this.splitPane.add(this.panelCentral);

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

        this.splitPane.add(this.panelLeft);
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
    // Função que fecha o arquivo que está aberto no visualisador de arquivos abertos
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
                    RoundedPanel aux = this.addedFilesPanel.remove(gFile.getInstance().getFullPath());
                    this.openFiles.getOpenFilesPanel().remove(aux);
                    SwingUtilities.updateComponentTreeUI(this.panelCentral);

                    // Tenta abrir o próximo arquivo, aberto anteriormente, se existir
                    try { // Existe arquivo para abrir
                        this.lastClickedFilePath = ((RoundedPanel) this.addedFilesPanel.values().toArray()[0])
                                .getName();
                        this.runOpenFile(((RoundedPanel) this.addedFilesPanel.values().toArray()[0]).getName());
                    } catch (Exception e) { // Não existe arquivo para abrir
                        if (result == JOptionPane.YES_OPTION) { // Salva o arquivo atual antes de fechar
                            SaveFile.getInstance().saveFile(false,false);
                        }
                        gFile.getInstance().closeFile();
                        this.lastClickedFilePath = null;
                        this.decideEditorEnabled(false);
                    }
                }
            }
        } else {
            RoundedPanel aux = this.addedFilesPanel.remove(fileToClose);
            this.openFiles.getOpenFilesPanel().remove(aux);
            SwingUtilities.updateComponentTreeUI(this.panelCentral);

            // Tenta abrir o próximo arquivo, aberto anteriormente, se existir
            try { // Existe arquivo para abrir
                this.runOpenFile(((RoundedPanel) this.addedFilesPanel.values().toArray()[0]).getName());
            } catch (Exception e) { // Não existe arquivo para abrir
                this.lastClickedFilePath = null;
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

    // Função que decide se o editor de texto vai estar habilitado ou não baseado se
    private void decideEditorEnabled(Boolean forceDisabled) {
        if (gFile.getInstance().isOpen()) {
            this.editorPane.getEditorPane().setEnabled(true);
            if (this.addedFilesPanel.get(gFile.getInstance().getFullPath()) == null) {
                this.createNewFileOpenPanel();
            } else {
                if (lastClickedFilePath != null) {
                    for (Component c : addedFilesPanel.get(this.lastClickedFilePath).getComponents()) {
                        if (c instanceof JLabel) {
                            ((JLabel) c).setForeground(Color.WHITE);
                        }
                    }
                }
            }
        } else {
            this.editorPane.getEditorPane().setEnabled(false);
            this.editorPane.getEditorPane().setText("");
        }
        if(forceDisabled){
            this.editorPane.getEditorPane().setEnabled(false);
            this.editorPane.getEditorPane().setText("");
        }
    }

    // Função que adiciona um novo painel ao visualisador de arquivos abertos
    private void createNewFileOpenPanel() {
        this.createNewFileOpenPanel = new CreateNewFileOpenPanel(this.lastClickedFilePath, this.addedFilesPanel,
                gFile.getInstance().getName(), gFile.getInstance().getFullPath(), this.listenerOpenFilesPanel,
                this.listenerOpenFilesPanel, this.openFiles);
        this.lastClickedFilePath = this.createNewFileOpenPanel.getLastClickedFilePath();

        this.createNewFileOpenPanel = null;

        SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
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

    // Atualiza as pastas e arquivos atualmente abertos
    public void runUpdateFileSystemView() {
        this.systemView.updateFolder(this.currentFolder, this.panelLeft, this.systemView.getSystemFilesPanel(),
                this.systemFilePanelListener);
        SwingUtilities.updateComponentTreeUI(frame);
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

    // Getter do lastClickedFilePath
    public String getLastClickedFilePath() {
        return this.lastClickedFilePath;
    }

    // Setter do lastClickedFilePath
    public void setLastClickedFilePath(String lastClickedFilePath) {
        this.lastClickedFilePath = lastClickedFilePath;
    }

    // Getter do openFiles
    public OpenFilesPanel getOpenFilesPanel() {
        return this.openFiles;
    }

    // Getter do addedFilesPanel
    public Map<String, RoundedPanel> getAddedFilesPanel() {
        return this.addedFilesPanel;
    }
}
