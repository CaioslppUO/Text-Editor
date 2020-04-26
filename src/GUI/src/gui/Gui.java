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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
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
import gui.maingui.secondarypanels.menu.MenuBar;

public class Gui implements ActionListener, KeyListener, MouseListener {

    //Frame e Painéis principais
    private JFrame frame;
    private JPanel panelCentral;
    private JPanel panelLeft;
    private JPanel panelTop;
    private JPanel panelDown;
    private JPanel panelRight;

    //Constantes
    private Constants constants;

    //Variáveis "globais"
    private File currentFile;
    private String currentFolder;
    private Map<String, RoundedPanel> addedFilesPanel;
    private String lastClickedFilePath;

    //Fonte
    private Integer fontSize;
    private String fontType;

    //Gerenciador do editorPane
    private EditorPane editorPane;

    //Gerenciador da tela de configuração do editorPane
    private EditorPaneConfig editorPaneConfig;

    //Gerenciador do painel do visualisador de arquivos abertos
    private OpenFiles openFiles;

    //Gerenciador do visualisador de arquivos abertos
    private CreateNewFileOpenPanel createNewFileOpenPanel;

    //Gerenciador do salvamento de arquivos
    private SaveFile saveFile;

    //Gerenciador do abridor de arquivos
    private OpenFile openFile;

    //Gerenciador do criador de arquivos
    private NewFile newFile;

    //Gerenciador do abridor de pastas
    private OpenFolder openFolder;

    //Gerenciador da barra de menu
    private MenuBar menuBar;

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
        this.includeMenuBar();

        //Definições finais do Main Frame
        this.frame.setBackground(Color.LIGHT_GRAY);
        this.frame.setBounds(100, 100, 450, 300);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Getter do frame principal
    public JFrame getMainFrame() {
        return this.frame;
    }

    //***********************
    //* Funções de inclusão *
    //***********************
    //Função que inclui a barra de menu na interface
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: O Frame principal deve estar instanciado e configurado corretamente
    //Pós-condição: A barra de menu é adicionada à interface
    private void includeMenuBar() {
        this.menuBar = new MenuBar(this,this.frame);
    }

    //Função que inclui o painel de arquivos abertos à interface
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: O painel central deve estar instanciado e configurado.
    //Pós-condição: O painel que gerencia os arquivos abertos é adicionado à interface
    private void includeOpenFilesPanel() {
        this.openFiles = new OpenFiles(this.panelCentral);
    }

    //Função que inclui o editroPane na interface
    //Entrada: Nenhuma
    //Retorno: Nenhum
    /*Pré-condição: O painel central deve estar instanciado e configurado. A classe Gui deve implementar a classe
     * KeyListener para tratar atalhos pressionados no editor
     */
    //Pós-condição: O editor é adicionado à interface
    private void includeEditorPane() {
        this.editorPane = new EditorPane(this.fontType, this.fontSize, this.panelCentral, this);
        this.decideEditorEnabled(false);
    }

    //Função que inclui a tela de configuração do editorPane na interface
    //Entrada: Nenhuma
    //Retorno: Nenhum
    /*Pré-condição: As variáveis this.fontTypee this.fontSize devem estar instanciadas e configuradas.
     * A classe Gui deve implementar as classes ActionListener e KeyListener para tratar interações
     */
    //Pós-condição: A tela de configuração do editorPane é incluida à interface
    private void includeEditorPaneConfig() {
        this.editorPaneConfig = new EditorPaneConfig(this.fontType, this.fontSize);
        this.editorPaneConfig.getOkButton().addActionListener(this);
        this.editorPaneConfig.getEditorConfigFrame().addKeyListener(this);
    }

    //************************
    //* Funções de definição *
    //************************
    //Função que configura e inicializa o frame principal
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: Nenhuma
    ///Pós-condição: O frame principal é criado e configurado
    private void defineMainFrame() {
        this.frame = new JFrame("Text Editor");
        this.frame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
        this.frame.setFocusable(true);
        this.frame.addKeyListener(this);
    }

    //Função que configura e inicializa o painel central
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelCentral() {
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

    //Função que configura e inicializa o painel da esquerda
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelLeft() {
        this.panelLeft = new JPanel();
        this.panelLeft.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.panelLeft.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelLeft, BorderLayout.WEST);

        Component horizontalStrut = Box.createHorizontalStrut(110);

        this.panelLeft.add(horizontalStrut);
    }

    //Função que configura e inicializa o painel do topo
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelTop() {
        this.panelTop = new JPanel();
        this.panelTop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        this.panelTop.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelTop, BorderLayout.NORTH);

        Component verticalStrut = Box.createVerticalStrut(50);

        this.panelTop.add(verticalStrut);
    }

    //Função que configura e inicializa o painel de baixo
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelDown() {
        this.panelDown = new JPanel();
        this.panelDown.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelDown, BorderLayout.SOUTH);
    }

    //Função que configura e inicializa o painel da direita
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: O frame principal deve estar instanciado e configurado
    private void definePanelRight() {
        this.panelRight = new JPanel();
        this.panelRight.setBackground(this.constants.getSideAreasColor());
        this.frame.getContentPane().add(this.panelRight, BorderLayout.EAST);
    }

    //*************************
    //* Funções de auxiliares *
    //*************************
    //Função que fecha o arquivo que está aberto
    //Entrada: Nenhuma
    //Retorno: Nenhum
    /*Pŕe-condição: As variáveis this.addedFilesPanel, this.saveFile, this.lastClickedFilePath, this.currentFile,
     * this.editorPane e this.currentFile devem estar instanciadas e configuradas corretamente.
     */
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

    //Função que atualiza a fonte utilizada no editor
    //Entrada: Nenhuma
    //Retorno: Nenhum
    /*Pŕe-condição: O editorPane deve estar instanciado e configurado. as variáveis this.fontSize e this.fontType
     * devem estar instanciadas e configuradas
     */
    //Pós-condição: A fonte é atualizada para as opções definidas no menu
    private void updateFont() {
        this.editorPaneConfig.getEditorConfigFrame().dispose();
        this.fontSize = Integer.parseInt(this.editorPaneConfig.getFontSizeSpinner().getValue().toString());
        this.fontType = this.editorPaneConfig.getFontTypeList().getSelectedItem().toString();
        this.editorPane.getEditorPane().setFont(new Font(this.fontType, Font.PLAIN, this.fontSize));
    }

    //Função que decide se o editor de texto vai estar habilitado ou não baseado se existe ou não um arquivo aberto
    //Entrada: Verdadeiro ou falso que o arquivo em this.currentFile já está aberto
    //Retorno: Nenhum
    //Pré-condição: O editorPane deve estar configurado e instanciado.
    private void decideEditorEnabled(Boolean isFileAlreadyOpen) {
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

    //Função que adiciona um novo painel ao visualisador de arquivos abertos
    //Entrada: Nome do arquivo e caminho do arquivo
    //Retorno: Nenhum
    /*Pré-condição: As variáveis this.lastClickedPath e this.addedFilesPanel devem estar instanciadas e configuradas
     * corretamentes. O frame principal deve estar instanciado e configurado corretamente. Não deve existir previamente
     * um painel de arquivos abertos com o mesmo fileName e o mesmo filePath.
     */
    //Pós-condição: Um novo painel com o nome fileName é criado e adicionado à interface
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

    //Função que aplica as rotinas para abrir um arquivo
    //Entrada: Nenhuma
    //Retorno: Nenhum
    /*Pré-condição: As variáveis this.currentFile, this.editorPane, this.addedFilesPanel e this.currentFolder devem
     * estar instanciadas e configuradas.
     */
    //Pós-condição: Abre um arquivo e o coloca na interface para edição
    private void runOpenFile() {
        this.openFile = new OpenFile(this.currentFile, this.editorPane.getEditorPane(), this.addedFilesPanel, this.currentFolder);
        this.openFile.openFile();
        this.currentFile = this.openFile.getCurrentFile();
        this.addedFilesPanel = this.openFile.getAddedFilesPanel();
        this.editorPane.setEditorPane(this.openFile.getEditorPane());
        this.openFile = null;
        if (this.addedFilesPanel.get(this.currentFile.getAbsolutePath()) != null) {
            this.decideEditorEnabled(true);
        } else {
            this.decideEditorEnabled(false);
        }
    }

    //Função que aplica as rotinas para abrir um arquivo
    //Entrada: Caminho para o arquivo a ser aberto
    //Retorno: Nenhum
    /*Pré-condição: As variáveis this.currentFile, this.editorPane, this.addedFilesPanel e this.currentFolder devem
     * estar instanciadas e configuradas.
     */
    //Pós-condição: Abre um arquivo e o coloca na interface para edição
    private void runOpenFile(String filePath) {
        this.openFile = new OpenFile(this.currentFile, this.editorPane.getEditorPane(), this.addedFilesPanel, this.currentFolder);
        this.openFile.openFile(filePath);
        this.currentFile = this.openFile.getCurrentFile();
        this.addedFilesPanel = this.openFile.getAddedFilesPanel();
        this.editorPane.setEditorPane(this.openFile.getEditorPane());
        this.openFile = null;
        if (this.addedFilesPanel.get(this.currentFile.getAbsolutePath()) != null) {
            this.decideEditorEnabled(true);
        } else {
            this.decideEditorEnabled(false);
        }
    }

    //Função que executa as rotinas para criar um novo arquivo
    //Entrada: Nenhuma
    //Retorno: Nenhum
    /*Pré-condição: O editorPane deve estar instanciado e configurado. As variáveis this.currentFile e this.currentFolder
     * devem estar instanciadas e configuradas
     */
    //Pós-condição: Um novo arquivo é criado
    public void runCreateNewFile() {
        this.newFile = new NewFile(this.currentFile, this.editorPane.getEditorPane(), this.currentFolder);
        this.newFile.createNewFile();
        this.currentFile = this.newFile.getCurrentFile();
        this.editorPane.setEditorPane(this.newFile.getEditorPane());
        this.newFile = null;
        this.decideEditorEnabled(false);
    }

    //Função que abre uma pasta como diretório padrão
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pré-condição: A variável this.currentFolder deve estar instanciada e configurada corretamente.
    //Pós-condição: A pasta escolhida é aberta como diretório padrão
    public void runOpenFolder() {
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

    //***********************
    //* Funções de listener *
    //***********************
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

    @Override
    public void keyPressed(KeyEvent e) {
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

    //Responde aos clicks de mouse na interface
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
