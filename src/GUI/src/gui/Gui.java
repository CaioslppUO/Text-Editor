package gui;

import gui.extragui.TextLineNumber;
import gui.extragui.RoundedBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.PlainDocument;

import gui.extragui.RoundedPanel;
import gui.maingui.Constants;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStreamReader;

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

    //Fonte
    private JSpinner fontSizeSpinner;
    private JComboBox<String> fontTypeList;

    //Painéis secundários
    private JEditorPane editorPane;
    private JDialog editorConfigFrame;
    private JDialog saveFileFrame;
    private JDialog openFileFrame;
    private JDialog createNewFileFrame;
    private JDialog openFolderFrame;
    private JFileChooser chooseSaveDirectory;
    private JFileChooser chooseNewFileDirectory;
    private JFileChooser chooseOpenFile;
    private JFileChooser chooseOpenDirectory;
    private JPanel openFilesPanel;
    private RoundedPanel newFilePanel;

    //Construtor da classe
    public Gui() {
        //Constants
        this.constants = new Constants();
        
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
        if (this.constants.getCurrentFile() != null) {
            this.editorPane.setEnabled(true);
            if (!isFileAlreadyOpen) {
                createNewFileOpenPanel(this.constants.getCurrentFile().getName(), this.constants.getCurrentFile().getAbsolutePath());
            }
        } else {
            this.editorPane.setEnabled(false);
            this.editorPane.setText("");
        }
    }

    //Função que cria um novo painel de arquivo aberto
    //Entrada: Nome do arquivo aberto
    //Retorno: Nenhum
    /* Pré-condição: O painel openFilesPanel, deve existir, pois é ele quem contém o novo painel que será criado. A variável
         * addedFilesPanel deve existir, pois é ela quem guarda quais e quantos painéis de arquivo estão abertos
     */
    //Pós-condição: Um novo painel de arquivo aberto é inserido na interface
    private void createNewFileOpenPanel(String fileName, String filePath) {
        newFilePanel = new RoundedPanel();
        newFilePanel.setBackground(this.constants.getSideAreasColor());
        newFilePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        newFilePanel.setLayout(new BoxLayout(newFilePanel, BoxLayout.LINE_AXIS));
        newFilePanel.setName(filePath);
        newFilePanel.addMouseListener(this);

        JLabel fName = new JLabel(fileName + "  ");
        fName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        fName.setForeground(Color.WHITE);
        if (this.constants.getLastClickedFilePath() == null) {
            this.constants.setLastClickedFilePath(filePath);
        } else {
            for (Component c : this.constants.getAddedFilesPanel().get(this.constants.getLastClickedFilePath()).getComponents()) {
                if (c instanceof JLabel) {
                    ((JLabel) c).setForeground(this.constants.getMenuForeGroundColor());
                }
            }
            this.constants.setLastClickedFilePath(filePath);
        }
        newFilePanel.add(fName);

        JButton closeButton = new JButton("x");
        closeButton.setName("closeButton");
        closeButton.setBorder(new RoundedBorder(5));
        closeButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        closeButton.setActionCommand("buttonCloseFilePressed");
        closeButton.addActionListener(this);
        closeButton.setBackground(null);
        closeButton.addMouseListener(this);

        newFilePanel.add(fName);
        newFilePanel.add(closeButton);

        Component separator = Box.createHorizontalStrut(2);

        this.openFilesPanel.add(newFilePanel);
        this.openFilesPanel.add(separator);

        this.constants.getAddedFilesPanel().put(filePath, newFilePanel);

        SwingUtilities.updateComponentTreeUI(frame);
    }

    //Definições da tool bar de arquivos abertos
    private void defineOpenFilesPanel() {
        //Configurando o painel que retém os arquivos que estão abertos no momento
        this.openFilesPanel = new JPanel();
        this.openFilesPanel.setBackground(this.constants.getSideAreasColor());
        this.openFilesPanel.setLayout(new BoxLayout(this.openFilesPanel, BoxLayout.LINE_AXIS));
        JScrollPane scrollPane = new JScrollPane(this.openFilesPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 35));
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        //Configura o layout manager do openFilesPanel para inserção no painel central
        GridBagConstraints gbc_openFilesPanel = new GridBagConstraints();
        gbc_openFilesPanel.fill = GridBagConstraints.BOTH;
        gbc_openFilesPanel.insets = new Insets(0, 0, 0, 0);
        gbc_openFilesPanel.gridx = 0;
        gbc_openFilesPanel.gridy = 0;

        this.panelCentral.add(scrollPane, gbc_openFilesPanel);
    }

    //Definições do painel do editor
    public void defineEditorPane() {
        this.editorPane = new JEditorPane();
        this.editorPane.setForeground(this.constants.getEditorPaneFontColor());
        this.editorPane.setBackground(this.constants.getPaneEditorColor());

        GridBagConstraints gbc_editorPane = new GridBagConstraints();
        gbc_editorPane.gridx = 0;
        gbc_editorPane.gridy = 1;
        gbc_editorPane.fill = GridBagConstraints.BOTH;

        this.editorPane.setCaretColor(Color.WHITE);
        this.editorPane.setFont(new Font(this.constants.getFontType(), Font.PLAIN, this.constants.getFontSize()));

        //Mudando a quantidade de espaçõs da tecla TAB
        javax.swing.text.Document doc = this.editorPane.getDocument();
        doc.putProperty(PlainDocument.tabSizeAttribute, 2);

        //Incluindo o contador de linhas
        //Comentar as pŕoximas 4 linhas para utilizar o Design do eclipse
        JScrollPane scrollPane = new JScrollPane(this.editorPane);
        TextLineNumber tln = new TextLineNumber(this.editorPane);
        scrollPane.setRowHeaderView(tln);
        this.panelCentral.add(scrollPane, gbc_editorPane);
        this.editorPane.addKeyListener(this);
        this.decideEditorEnabled(false);
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

    //Launcher da tela de configuração do editor
    private void launchEditorConfigFrame() {
        this.editorConfigFrame = new JDialog();
        this.editorConfigFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.editorConfigFrame.setSize(300, 300);
        this.editorConfigFrame.setLocationRelativeTo(null);
        this.editorConfigFrame.setTitle("Editor Settings");
        this.editorConfigFrame.getContentPane().setLayout(null);
        this.editorConfigFrame.addKeyListener(this);

        //Botão para salvar as configurações escolhidas
        JButton buttonOk = new JButton("OK");
        buttonOk.setActionCommand("FontSizeChanged");
        buttonOk.addActionListener(this);
        buttonOk.setSize(55, 30);
        buttonOk.setLocation(125, 230);
        this.editorConfigFrame.getContentPane().add(buttonOk);

        JLabel labelFontSize;
        labelFontSize = new JLabel();
        labelFontSize.setText("Font Size");
        labelFontSize.setSize(75, 20);
        labelFontSize.setLocation(10, 5);
        labelFontSize.setForeground(this.constants.getMenuForeGroundColor());
        this.editorConfigFrame.getContentPane().add(labelFontSize);

        //Spinner para escolher o tamanho da fonte
        this.fontSizeSpinner = new JSpinner();
        this.fontSizeSpinner.setValue(this.constants.getFontSize());
        this.fontSizeSpinner.setSize(35, 20);
        this.fontSizeSpinner.setLocation(100, 7);
        this.editorConfigFrame.getContentPane().add(this.fontSizeSpinner);

        JLabel labelFontType;
        labelFontType = new JLabel();
        labelFontType.setText("Font Type");
        labelFontType.setSize(75, 20);
        labelFontType.setLocation(10, 40);
        labelFontType.setForeground(this.constants.getMenuForeGroundColor());
        this.editorConfigFrame.getContentPane().add(labelFontType);

        //ComboBox para escolher o tipo da fonte
        @SuppressWarnings("deprecation")
        String fontTypes[] = Toolkit.getDefaultToolkit().getFontList();

        this.fontTypeList = new JComboBox(fontTypes);
        this.fontTypeList.setSelectedItem(this.constants.getFontType());
        this.fontTypeList.setSize(120, 20);
        this.fontTypeList.setLocation(100, 42);
        this.editorConfigFrame.getContentPane().add(this.fontTypeList);

        this.editorConfigFrame.setVisible(true);
        this.editorConfigFrame.setFocusable(true);
    }

    //Função que salva o arquivo que está aberto na variável this.currentFile
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhuma
    //Pós-condição: O arquivo aberto na variável this.currentFile é salvo
    private void saveFile(Boolean showSaveMessage) {
        if (this.constants.getCurrentFile() != null) {
            try {
                PrintWriter print_line = new PrintWriter(new FileWriter(this.constants.getCurrentFile().toString()));
                print_line.printf("%s", this.editorPane.getText());
                print_line.close();
                if (showSaveMessage) {
                    JOptionPane.showMessageDialog(null, "File Saved");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error while trying to save the file");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No Files Open");
        }
    }

    //Função que define o frame de salvar um arquivo
    //Entrada: Nenhuma
    //Retorno: FileChooser.APPROVE_OPTION se o diretório for escolhido ou o contrário caso não seja
    //Pré-condição: Nenhuma
    //Pós-condição: Nenhuma
    private int defineSaveFileFrame() {
        this.saveFileFrame = new JDialog();
        this.saveFileFrame.getContentPane().setBackground(this.constants.getSideAreasColor());
        this.saveFileFrame.setSize(600, 600);
        this.saveFileFrame.setLocationRelativeTo(null);
        this.saveFileFrame.setTitle("Save File As");

        this.chooseSaveDirectory = new JFileChooser();
        this.chooseSaveDirectory.setCurrentDirectory(new File(this.constants.getCurrentFolder()));
        this.chooseSaveDirectory.setDialogTitle("Save to");
        this.chooseSaveDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.saveFileFrame.getContentPane().add(chooseSaveDirectory);
        this.saveFileFrame.setVisible(true);
        this.saveFileFrame.dispose();
        return chooseSaveDirectory.showOpenDialog(null);
    }

    //Função que salva o arquivo que está aberto na variável this.currentFile
    //Entrada: Nenhuma
    //Retorno: Nenhum
    //Pŕe-condição: Nenhum
    //Pós-condição: É aberto um menu para escolher como e onde salvar o arquivo aberto na variável this.currentFile
    private void saveFileAs() {
        if (this.constants.getCurrentFile() != null) {
            String fileSeparator = System.getProperty("file.separator");

            if (this.defineSaveFileFrame() == JFileChooser.APPROVE_OPTION) { //Salvar o arquivo
                File directory = this.chooseSaveDirectory.getSelectedFile();
                if (directory != null) {
                    String fileName;
                    fileName = JOptionPane.showInputDialog("File Name");
                    if (fileName != null) {
                        File newFile = new File(directory.toString() + fileSeparator + fileName);
                        try {
                            if (newFile.createNewFile()) {
                                this.constants.setCurrentFile(newFile);
                                this.saveFile(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "File Already Exists");
                            }
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "Error while trying to save the file");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid File Name");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Directory");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No Files Open");
        }
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
        chooseOpenFile.setCurrentDirectory(new File(this.constants.getCurrentFolder()));
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
                    this.editorPane.setText(contentToLoad);
                    this.constants.setCurrentFile(file);
                    if (this.constants.getAddedFilesPanel().get(this.constants.getCurrentFile().getAbsolutePath()) != null) {
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
                this.saveFile(false);
                this.editorPane.setText(contentToLoad);
                this.constants.setCurrentFile(file);
                if (this.constants.getAddedFilesPanel().get(this.constants.getCurrentFile().getAbsolutePath()) != null) {
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
        this.editorConfigFrame.dispose();
        this.constants.setFontSize(Integer.parseInt(this.fontSizeSpinner.getValue().toString()));
        this.constants.setFontType(this.fontTypeList.getSelectedItem().toString());
        this.editorPane.setFont(new Font(this.constants.getFontType(), Font.PLAIN, this.constants.getFontSize()));
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
        this.chooseNewFileDirectory.setCurrentDirectory(new File(this.constants.getCurrentFolder()));
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
        if (this.constants.getCurrentFile() == null) {
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
                            this.constants.setCurrentFile(newFile);
                            this.editorPane.setText("");
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
            this.constants.setCurrentFile(null);
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
        if (this.constants.getCurrentFile().getName().split("[.]")[1].equals("py")) {
            try {
                process = Runtime.getRuntime().exec("python3 " + this.constants.getCurrentFile().getAbsolutePath());
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
        } else if (this.constants.getCurrentFile().getName().split("[.]")[1].equals("c")) {
            try {
                process = Runtime.getRuntime().exec("gcc " + this.constants.getCurrentFile().getAbsolutePath() + " -o " + this.constants.getCurrentFile().getParent() + "/" + this.constants.getCurrentFile().getName().split("[.]")[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String inputText = "", aux;
                while ((aux = reader.readLine()) != null) {
                    inputText += aux;
                }
                if (!inputText.equals("")) {
                    JOptionPane.showMessageDialog(null, inputText, "Output", JOptionPane.INFORMATION_MESSAGE);
                }
                if (inputText.equals("")) {
                    process = Runtime.getRuntime().exec(this.constants.getCurrentFile().getParent() + "/./" + this.constants.getCurrentFile().getName().split("[.]")[0]);
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
        if (this.constants.getCurrentFile() != null) {
            int result = JOptionPane.showConfirmDialog(null, "Save File?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            RoundedPanel aux = this.constants.getAddedFilesPanel().remove(this.constants.getCurrentFile().getAbsolutePath());
            this.openFilesPanel.remove(aux);
            SwingUtilities.updateComponentTreeUI(frame);

            try {
                this.openFile(((RoundedPanel) this.constants.getAddedFilesPanel().values().toArray()[0]).getName());
            } catch (Exception e) {
                if (result == 0) {
                    this.saveFile(false);
                }
                this.constants.setCurrentFile(null);
                this.constants.setLastClickedFilePath(null);
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
        this.chooseOpenDirectory.setCurrentDirectory(new File(this.constants.getCurrentFolder()));
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
            this.constants.setCurrentFolder(this.chooseOpenDirectory.getSelectedFile().getAbsolutePath());
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
                    this.launchEditorConfigFrame();
                    break;
                case "buttonNewFilePressed":
                    this.createNewFile();
                    break;
                case "FontSizeChanged":
                    this.updateFont();
                    break;
                case "buttonSaveAsPressed":
                    this.saveFileAs();
                    break;
                case "buttonOpenFilePressed":
                    this.openFile();
                    break;
                case "buttonSavePressed":
                    this.saveFile(true);
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
        this.defineOpenFilesPanel();
        this.defineEditorPane();
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
        if (this.editorPane != null && e.isShiftDown() == false && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
            this.saveFile(true);
        }

        //Salva como o arquivo ao apertar ctrl+shift+s
        if (this.editorPane != null && e.isShiftDown() && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
            this.saveFileAs();
        }

        //Resposta do botão OK da tela de configuração do editor
        if (this.editorConfigFrame != null && this.editorConfigFrame.isActive() && e.getKeyCode() == 27) {
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
        if (this.editorPane != null && e.isControlDown() && e.getKeyChar() != 'r' && e.getKeyCode() == 82) {
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
            if (this.constants.getLastClickedFilePath() == null) {
                for (Component c : ((RoundedPanel) me.getComponent()).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(Color.WHITE);
                    }
                }
                SwingUtilities.updateComponentTreeUI(this.openFilesPanel);
                this.constants.setLastClickedFilePath(me.getComponent().getName());
            } else {
                for (Component c : ((RoundedPanel) this.constants.getAddedFilesPanel().get(this.constants.getLastClickedFilePath())).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(this.constants.getMenuForeGroundColor());
                    }
                }
                SwingUtilities.updateComponentTreeUI(this.openFilesPanel);
                for (Component c : ((RoundedPanel) me.getComponent()).getComponents()) {
                    if (c instanceof JLabel) {
                        ((JLabel) c).setForeground(Color.WHITE);
                    }
                }
                SwingUtilities.updateComponentTreeUI(this.openFilesPanel);
                this.constants.setLastClickedFilePath(me.getComponent().getName());
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
            SwingUtilities.updateComponentTreeUI(this.openFilesPanel);
        }
        if (!"closeButton".equals(me.getComponent().getName())) {
            ((RoundedPanel) me.getComponent()).setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            SwingUtilities.updateComponentTreeUI(this.openFilesPanel);
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if ("closeButton".equals(me.getComponent().getName())) {
            ((JButton) me.getComponent()).setBackground(null);
            ((JButton) me.getComponent()).setForeground(null);
            SwingUtilities.updateComponentTreeUI(this.openFilesPanel);
        }
        if (!"closeButton".equals(me.getComponent().getName())) {
            ((RoundedPanel) me.getComponent()).setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
            SwingUtilities.updateComponentTreeUI(this.openFilesPanel);
        }
    }
}
