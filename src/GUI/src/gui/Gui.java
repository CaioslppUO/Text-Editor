package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
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
import gui.maingui.Constants;
import gui.maingui.secondarypanels.editorpanel.EditorPane;
import gui.maingui.secondarypanels.editorpanel.EditorPaneConfig;
import gui.maingui.secondarypanels.openfile.OpenFile;
import gui.maingui.secondarypanels.openfilespanel.OpenFilesPanel;
import gui.maingui.secondarypanels.openfilespanel.CreateNewFileOpenPanel;
import gui.maingui.secondarypanels.savefile.SaveFile;
import gui.maingui.secondarypanels.newfile.NewFile;
import gui.maingui.secondarypanels.openfolder.OpenFolder;
import gui.maingui.secondarypanels.menu.MenuBar;
import gui.maingui.secondarypanels.menu.ListenerMenu;
import gui.maingui.secondarypanels.editorpanel.ListenerEditorPanel;
import gui.maingui.secondarypanels.editorpanel.ListenerEditorPanelConfig;
import gui.maingui.secondarypanels.openfilespanel.OpenFilesListener;
import gui.maingui.secondarypanels.newfolder.NewFolder;
import gui.maingui.secondarypanels.filesystemview.SystemFilePanel;
import gui.maingui.secondarypanels.filesystemview.SystemFilePanelListener;

public class Gui {

    // Frame e Painéis principais
    private JFrame frame;
    private JPanel panelCentral;
    private JPanel panelLeft;
    private JPanel panelTop;
    private JPanel panelDown;
    private JPanel panelRight;

    // Constantes
    private Constants constants;

    // Variáveis "globais"
    private File currentFile;
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

    // Gerenciador do salvamento de arquivos
    private SaveFile saveFile;

    // Gerenciador do abridor de arquivos
    private OpenFile openFile;

    // Gerenciador do criador de arquivos
    private NewFile newFile;

    // Gerenciador do abridor de pastas
    private OpenFolder openFolder;

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

    // Gerenciado do criador de diretórios
    private NewFolder newFolder;

    // Gerenciador do visualisador de diretórios
    private SystemFilePanel systemView;

    // Pane utilizado para redimensionar as janelas
    private JSplitPane splitPane;

    // Construtor da classe
    private Gui() {
        // Variáveis globais
        this.currentFile = null;
        this.currentFolder = ".";
        this.addedFilesPanel = new LinkedHashMap<>();
        lastClickedFilePath = null;

        // Fonte padrão
        this.fontSize = 12;
        this.fontType = "Monospaced";

        // Constantes
        this.constants = new Constants();

        // Utilitários
        this.saveFile = new SaveFile();
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
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pŕe-condição: Nenhuma
    // Pós-condição: Todas as interfaces são inicializadas
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
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: O Frame principal deve estar instanciado e configurado
    // corretamente
    // Pós-condição: A barra de menu é adicionada à interface
    private void includeMenuBar() {
        new MenuBar(this.listenerMenu, this.frame);
    }

    // Função que inclui o painel de arquivos abertos à interface
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: O painel central deve estar instanciado e configurado.
    // Pós-condição: O painel que gerencia os arquivos abertos é adicionado à
    // interface
    private void includeOpenFilesPanel() {
        this.openFiles = new OpenFilesPanel(this.panelCentral);
    }

    // Função que inclui o editroPane na interface
    // Entrada: Nenhuma
    // Retorno: Nenhum
    /*
     * Pré-condição: O painel central deve estar instanciado e configurado. A classe
     * Gui deve implementar a classe KeyListener para tratar atalhos pressionados no
     * editor
     */
    // Pós-condição: O editor é adicionado à interface
    private void includeEditorPane() {
        this.editorPane = new EditorPane(this.fontType, this.fontSize, this.panelCentral, this.listenerEditorPanel);
        this.decideEditorEnabled(false);
    }

    // Função que inclui a tela de configuração do editorPane na interface
    // Entrada: Nenhuma
    // Retorno: Nenhum
    /*
     * Pré-condição: As variáveis this.fontTypee this.fontSize devem estar
     * instanciadas e configuradas. A classe Gui deve implementar as classes
     * ActionListener e KeyListener para tratar interações
     */
    // Pós-condição: A tela de configuração do editorPane é incluida à interface
    public void includeEditorPaneConfig() {
        this.editorPaneConfig = new EditorPaneConfig(this.fontType, this.fontSize);
        this.editorPaneConfig.getOkButton().addActionListener(this.listenerEditorPanelConfig);
        this.editorPaneConfig.getEditorConfigFrame().addKeyListener(this.listenerEditorPanelConfig);
    }

    // Função que inclui a tela de diretórios abertos
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: As variáveis this.currentFolde e this.panelLeft devem estar
    // instanciadas e configuradas.
    // Pós-condição: A tela de configuração do editorPane é incluida à interface
    private void includeSystemFileView() {
        this.systemView.updateFolder(this.currentFolder, this.panelLeft, null, this.systemFilePanelListener);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    // ************************
    // * Funções de definição *
    // ************************
    // Função que configura e inicializa o frame principal
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: Nenhuma
    /// Pós-condição: O frame principal é criado e configurado
    private void defineMainFrame() {
        this.frame = new JFrame("Text Editor");
        this.frame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
        this.frame.addKeyListener(this.listenerGui);
        this.frame.setFocusable(true);

        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerSize(3);
        this.splitPane.resetToPreferredSizes();

        this.frame.getContentPane().add(this.splitPane, BorderLayout.CENTER);
    }

    // Função que configura e inicializa o painel central
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelCentral() {
        this.panelCentral = new JPanel();
        this.panelCentral.setBackground(this.constants.getPaneEditorColor());
        this.splitPane.add(this.panelCentral);

        GridBagLayout gbl_panelCentral = new GridBagLayout();
        gbl_panelCentral.columnWidths = new int[] { 0, 0 };
        gbl_panelCentral.rowHeights = new int[] { 18, 0, 0 };
        gbl_panelCentral.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_panelCentral.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        this.panelCentral.setLayout(gbl_panelCentral);
    }

    // Função que configura e inicializa o painel da esquerda
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelLeft() {
        this.panelLeft = new JPanel();
        this.panelLeft.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.panelLeft.setBackground(this.constants.getSideAreasColor());
        this.panelLeft.setLayout(new BorderLayout());

        this.splitPane.add(this.panelLeft);
    }

    // Função que configura e inicializa o painel do topo
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelTop() {
        this.panelTop = new JPanel();
        this.panelTop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.panelTop.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelTop, BorderLayout.NORTH);

        Component verticalStrut = Box.createVerticalStrut(50);

        this.panelTop.add(verticalStrut);
    }

    // Função que configura e inicializa o painel de baixo
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelDown() {
        this.panelDown = new JPanel();
        this.panelDown.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelDown, BorderLayout.SOUTH);
    }

    // Função que configura e inicializa o painel da direita
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelRight() {
        this.panelRight = new JPanel();
        this.panelRight.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelRight, BorderLayout.EAST);
    }

    // *************************
    // * Funções de auxiliares *
    // *************************
    // Função que fecha o arquivo que está aberto
    // Entrada: Nenhuma
    // Retorno: Nenhum
    /*
     * Pŕe-condição: As variáveis this.addedFilesPanel, this.saveFile,
     * this.lastClickedFilePath, this.currentFile, this.editorPane e
     * this.currentFile devem estar instanciadas e configuradas corretamente.
     */
    // Pós-condição: O arquivo aberto atualmente é fechado
    public void closeFile() {
        if (this.currentFile != null) {
            int result = JOptionPane.showConfirmDialog(null, "Save File?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (result != 2) {
                RoundedPanel aux = this.addedFilesPanel.remove(this.currentFile.getAbsolutePath());
                this.openFiles.getOpenFilesPanel().remove(aux);
                SwingUtilities.updateComponentTreeUI(this.panelCentral);

                // Tenta abrir o próximo arquivo, aberto anteriormente, se existir
                try { // Existe arquivo para abrir
                    this.lastClickedFilePath = ((RoundedPanel) this.addedFilesPanel.values().toArray()[0]).getName();
                    this.runOpenFile(((RoundedPanel) this.addedFilesPanel.values().toArray()[0]).getName());
                } catch (Exception e) { // Não existe arquivo para abrir
                    if (result == 0) { // Salva o arquivo atual antes de fechar
                        this.saveFile.saveFile(false, this.currentFile, this.editorPane.getEditorPane().getText());
                    }
                    this.currentFile = null;
                    this.lastClickedFilePath = null;
                    this.decideEditorEnabled(false);
                }
            }
        }
    }

    // Função que atualiza a fonte utilizada no editor
    // Entrada: Nenhuma
    // Retorno: Nenhum
    /*
     * Pŕe-condição: O editorPane deve estar instanciado e configurado. as variáveis
     * this.fontSize e this.fontType devem estar instanciadas e configuradas
     */
    // Pós-condição: A fonte é atualizada para as opções definidas no menu
    public void updateFont() {
        this.fontSize = Integer.parseInt(this.editorPaneConfig.getFontSizeSpinner().getValue().toString());
        this.fontType = this.editorPaneConfig.getFontTypeList().getSelectedItem().toString();
        this.editorPane.getEditorPane().setFont(new Font(this.fontType, Font.PLAIN, this.fontSize));
    }

    // Função que decide se o editor de texto vai estar habilitado ou não baseado se
    // existe ou não um arquivo aberto
    // Entrada: Verdadeiro ou falso que o arquivo em this.currentFile já está aberto
    // Retorno: Nenhum
    // Pré-condição: O editorPane deve estar configurado e instanciado.
    private void decideEditorEnabled(Boolean isFileAlreadyOpen) {
        if (this.currentFile != null) {
            this.editorPane.getEditorPane().setEnabled(true);
            if (!isFileAlreadyOpen) {
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
    }

    // Função que adiciona um novo painel ao visualisador de arquivos abertos
    // Entrada: Nome do arquivo e caminho do arquivo
    // Retorno: Nenhum
    /*
     * Pré-condição: As variáveis this.lastClickedPath e this.addedFilesPanel devem
     * estar instanciadas e configuradas corretamentes. O frame principal deve estar
     * instanciado e configurado corretamente. Não deve existir previamente um
     * painel de arquivos abertos com o mesmo fileName e o mesmo filePath.
     */
    // Pós-condição: Um novo painel com o nome fileName é criado e adicionado à
    // interface
    private void createNewFileOpenPanel() {
        this.createNewFileOpenPanel = new CreateNewFileOpenPanel(this.lastClickedFilePath, this.addedFilesPanel,
                this.currentFile.getName(), this.currentFile.getAbsolutePath(), this.listenerOpenFilesPanel,
                this.listenerOpenFilesPanel, this.openFiles);
        this.lastClickedFilePath = this.createNewFileOpenPanel.getLastClickedFilePath();

        this.createNewFileOpenPanel = null;

        SwingUtilities.updateComponentTreeUI(this.openFiles.getOpenFilesPanel());
    }

    // Função que aplica as rotinas para abrir um arquivo
    // Entrada: Nenhuma
    // Retorno: Nenhum
    /*
     * Pré-condição: As variáveis this.currentFile, this.editorPane,
     * this.addedFilesPanel e this.currentFolder devem estar instanciadas e
     * configuradas.
     */
    // Pós-condição: Abre um arquivo e o coloca na interface para edição
    public void runOpenFile() {
        this.openFile = new OpenFile(this.currentFile);
        this.currentFile = this.openFile.openFile(this.currentFolder, this.editorPane);
        this.openFile = null;

        // Confere se o arquivo já está aberto no visualisador
        if (this.currentFile != null && this.addedFilesPanel.get(this.currentFile.getAbsolutePath()) != null) {
            this.decideEditorEnabled(true);
        } else {
            this.decideEditorEnabled(false);
        }
    }

    // Função que aplica as rotinas para abrir um arquivo
    // Entrada: Caminho para o arquivo a ser aberto
    // Retorno: Nenhum
    /*
     * Pré-condição: As variáveis this.currentFile, this.editorPane,
     * this.addedFilesPanel e this.currentFolder devem estar instanciadas e
     * configuradas.
     */
    // Pós-condição: Abre um arquivo e o coloca na interface para edição
    public void runOpenFile(String filePath) {
        this.openFile = new OpenFile(this.currentFile);
        this.currentFile = this.openFile.openFileUsingPath(filePath, this.editorPane);
        this.openFile = null;

        // Confere se o arquivo já está aberto no visualisador
        if (this.currentFile != null && this.addedFilesPanel.get(this.currentFile.getAbsolutePath()) != null) {
            this.decideEditorEnabled(true);
        } else {
            this.decideEditorEnabled(false);
        }
    }

    // Função que executa as rotinas para criar um novo arquivo
    // Entrada: Nenhuma
    // Retorno: Nenhum
    /*
     * Pré-condição: O editorPane deve estar instanciado e configurado. As variáveis
     * this.currentFile e this.currentFolder devem estar instanciadas e configuradas
     */
    // Pós-condição: Um novo arquivo é criado
    public void runCreateNewFile() {
        this.newFile = new NewFile(this.currentFile, this.editorPane, this.currentFolder);
        this.currentFile = this.newFile.getCurrentFile();
        this.systemView.updateFolder(this.currentFolder, this.panelLeft, this.systemView.getSystemFilesPanel(), this.systemFilePanelListener);
        SwingUtilities.updateComponentTreeUI(frame);
        this.newFile = null;
        this.decideEditorEnabled(false);
    }

    // Função que abre uma pasta como diretório padrão
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: A variável this.currentFolder deve estar instanciada e
    // configurada corretamente.
    // Pós-condição: A pasta escolhida é aberta como diretório padrão
    public void runOpenFolder() {
        this.openFolder = new OpenFolder();
        this.currentFolder = this.openFolder.openFolder(this.currentFolder);
        this.systemView.updateFolder(this.currentFolder, this.panelLeft, this.systemView.getSystemFilesPanel(), this.systemFilePanelListener);
        SwingUtilities.updateComponentTreeUI(frame);
        this.openFolder = null;
    }

    // Função que roda o arquivo atualmente aberto. Somente em Python3 ou em C
    // Entrada: Nenhuma
    // Retorno: Nenhum
    /*
     * Pré-condição: O arquivo deve ter a extensão .py ou .c. O arquivo deve ser um
     * código fonte escrito em c ou em python3. O sistema Operacional deve ser
     * Linux.
     */
    // Pós-condição: O programa é rodado no bash
    public void runSelectedFile() {
        Process process;
        if (this.editorPane.getEditorPane().isEnabled()) { // Verifica se o arquivo está aberto
            if (this.currentFile.getName().split("[.]")[1].equals("py")) {
                try {
                    process = Runtime.getRuntime().exec("python3 " + this.currentFile.getAbsolutePath());
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
            } else if (this.currentFile.getName().split("[.]")[1].equals("c")) {
                try {
                    process = Runtime.getRuntime().exec("gcc " + this.currentFile.getAbsolutePath() + " -o "
                            + this.currentFile.getParent() + "/" + this.currentFile.getName().split("[.]")[0]);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    String inputText = "", aux;
                    while ((aux = reader.readLine()) != null) {
                        inputText += aux;
                    }
                    if (!inputText.equals("")) {
                        JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if (inputText.equals("")) {
                        process = Runtime.getRuntime().exec(
                                this.currentFile.getParent() + "/./" + this.currentFile.getName().split("[.]")[0]);
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
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: Uma nova pasta é criada e definida como diretório padrão
    public void runCreateNewFolder() {
        this.newFolder = new NewFolder();
        this.currentFolder = this.newFolder.createNewFolder(this.currentFolder);
        this.systemView.updateFolder(this.currentFolder, this.panelLeft, this.systemView.getSystemFilesPanel(), this.systemFilePanelListener);
        SwingUtilities.updateComponentTreeUI(frame);
        this.newFolder = null;
    }

    // Getter da instância do singleton
    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    // Getter do arquivo aberto atualmente
    public File getCurrentFile() {
        return this.currentFile;
    }

    // Getter da pasta aberta atualmente
    public String getCurrentFolder() {
        return this.currentFolder;
    }

    // Getter do EditorPane
    public EditorPane getEditorPane() {
        return this.editorPane;
    }

    // Getter saveFile
    public SaveFile getSaveFile() {
        return this.saveFile;
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
