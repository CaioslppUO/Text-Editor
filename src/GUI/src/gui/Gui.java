package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.Action;
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
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.PlainDocument;

public class Gui implements ActionListener, KeyListener{
	
	//Frame e Painéis principais
	private JFrame frame;
	private JPanel panelCentral;
	private JPanel panelLeft;
	private JPanel panelTop;
	private JPanel panelDown;
	private JPanel panelRight;
	
	//Cores do editor
	private Color PaneEditorColor;
	private Color MenuBarColor;
	private Color sideAreasColor;
	private Color MenuForeGroundColor;
	private Color editorPaneFontColor;
        private Color newToolBarFileColor;
	
	//Fonte
	private Integer fontSize;
	private String fontType;
	private JSpinner fontSizeSpinner;
	private JComboBox<String> fontTypeList;
	
	//Painéis secundários
	private JEditorPane editorPane;
	private JDialog editorConfigFrame;
	private JDialog saveFileFrame;
	private JDialog openFileFrame;
	private JDialog createNewFileFrame;
	private JFileChooser chooseSaveDirectory;
        private JPanel fileToolBarWrapPanel;
        private JToolBar fileToolBar;
        private JPanel fileToolBarPanel;
	
	//Variáveis "globais"
	private File currentFile;

	//Construtor da classe
	public Gui() {
		//Cores padrões
		this.PaneEditorColor = new Color(51, 51, 51);
		this.sideAreasColor = new Color(82, 82, 82);
		this.MenuBarColor = new Color(28, 28, 28);
		this.MenuForeGroundColor = new Color(137, 163, 201);
		this.editorPaneFontColor = new Color(191, 191, 191);
                this.newToolBarFileColor = new Color(125, 125, 125);
		
		//Fonte padrão
		this.fontSize = 12;
		this.fontType = "Monospaced";
		
		//Arquivo inicialmente aberto
		this.currentFile = null;
		
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
		this.frame.getContentPane().setBackground(this.sideAreasColor);
		this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
                this.frame.setFocusable(true);
                this.frame.addKeyListener(this);
	}
	
	//Definições do painel central
	public void definePanelCentral() {
		this.panelCentral = new JPanel();
		this.panelCentral.setBackground(this.PaneEditorColor);
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
		this.panelLeft.setBackground(this.sideAreasColor);
		this.frame.getContentPane().add(this.panelLeft, BorderLayout.WEST);
		
		Component horizontalStrut = Box.createHorizontalStrut(110);
		
		this.panelLeft.add(horizontalStrut);
	}
	
	//Definições do painel do topo
	private void definePanelTop() {
		this.panelTop = new JPanel();
		this.panelTop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.panelTop.setBackground(this.sideAreasColor);
		frame.getContentPane().add(this.panelTop, BorderLayout.NORTH);
		
		Component verticalStrut = Box.createVerticalStrut(50);
		
		this.panelTop.add(verticalStrut);
	}
	
	//Definições do painel de baixo
	private void definePanelDown() {
		this.panelDown = new JPanel();
		this.panelDown.setBackground(this.sideAreasColor);
		this.frame.getContentPane().add(this.panelDown, BorderLayout.SOUTH);
	}
	
	//Definições do painel da direita
	private void definePanelRight() {
		this.panelRight = new JPanel();
		this.panelRight.setBackground(this.sideAreasColor);
		this.frame.getContentPane().add(this.panelRight, BorderLayout.EAST);
	}
	
        //Função que decide se o editor de texto vai estar habilitado ou não baseado se existe ou não um arquivo aberto
	public void decideEditorEnabled() {
		if(this.currentFile != null) {
			this.editorPane.setEnabled(true);
                        createNewFileOpenToolBar(this.currentFile.getName());
		}else {
			this.editorPane.setEnabled(false);
		}
	}
        
        private void createNewFileOpenToolBar(String fileName){
            JPanel newFilePanel = new JPanel();
            newFilePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
            newFilePanel.setBackground(this.newToolBarFileColor);
            newFilePanel.setLayout(new BoxLayout(newFilePanel, BoxLayout.LINE_AXIS));
            
            JLabel fn = new JLabel("( " + fileName + " | ");
            fn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            JButton close = new JButton("X");
            close.setBorderPainted(false);
            close.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
            close.setBackground(null);
            close.setActionCommand("buttonCloseFilePressed");
            JLabel fnEnd = new JLabel(" )");
            fnEnd.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            
            newFilePanel.add(fn);
            newFilePanel.add(close);
            newFilePanel.add(fnEnd);
            
            Component separator = Box.createHorizontalStrut(50);
            
            this.fileToolBarPanel.add(newFilePanel);
            this.fileToolBarPanel.add(separator);
            SwingUtilities.updateComponentTreeUI(frame);
        }
        
        //Definições da tool bar de arquivos abertos
        private void defineFilesToolBar(){
            //Configurando o painel externo da toolbar
            this.fileToolBarWrapPanel = new JPanel();
            this.fileToolBarWrapPanel.setBackground(this.sideAreasColor);
            
            GridBagLayout gbl_fielToolBarWrapPanel = new GridBagLayout();
            gbl_fielToolBarWrapPanel.rowWeights = new double[]{1.0};
            gbl_fielToolBarWrapPanel.columnWeights = new double[]{1.0};
            this.fileToolBarWrapPanel.setLayout(gbl_fielToolBarWrapPanel);
            
            //Configurando a toolBar
            this.fileToolBar = new JToolBar("File Tool Bar");
            this.fileToolBar.setFloatable(false);
            this.fileToolBar.setBackground(this.sideAreasColor);
            this.fileToolBar.setBorder(null);
            
            GridBagConstraints gbc_fileToolBar = new GridBagConstraints();
            gbc_fileToolBar.fill = GridBagConstraints.VERTICAL;
            gbc_fileToolBar.anchor = GridBagConstraints.WEST;
            this.fileToolBarWrapPanel.add(this.fileToolBar, gbc_fileToolBar);
            
            //Configura o layout da toolbar
            GridBagConstraints gbc_toolBar = new GridBagConstraints();
            gbc_toolBar.fill = GridBagConstraints.BOTH;
            gbc_toolBar.insets = new Insets(0, 0, 0, 0);
            gbc_toolBar.gridx = 0;
            gbc_toolBar.gridy = 0;
            
            panelCentral.add(this.fileToolBarWrapPanel, gbc_toolBar);
            
            //Configura o painel interno da toolbar
            this.fileToolBarPanel = new JPanel();
            this.fileToolBarPanel.setBackground(this.sideAreasColor);
            this.fileToolBarPanel.setLayout(new BoxLayout(this.fileToolBarPanel, BoxLayout.LINE_AXIS));
            
            JScrollPane scrollPane = new JScrollPane(this.fileToolBarPanel);
            scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
            
            this.fileToolBar.add(scrollPane);
        }
	
	//Definições do painel do editor
	public void defineEditorPane() {
		this.editorPane = new JEditorPane();
		this.editorPane.setForeground(this.editorPaneFontColor);
		this.editorPane.setBackground(this.PaneEditorColor);
                
		GridBagConstraints gbc_editorPane = new GridBagConstraints();
		gbc_editorPane.gridx = 0;
		gbc_editorPane.gridy = 1;
		gbc_editorPane.fill = GridBagConstraints.BOTH;
                
		this.editorPane.setCaretColor(Color.WHITE);
		this.editorPane.setFont(new Font(this.fontType, Font.PLAIN, this.fontSize));
		
		//Mudando a quantidade de espaçõs da tecla TAB
		javax.swing.text.Document doc = this.editorPane.getDocument();
		doc.putProperty(PlainDocument.tabSizeAttribute, 2);
		
		//Incluindo o contador de linhas
		//Comentar as pŕoximas 4 linhas para utilizar o Design do eclipse
		JScrollPane scrollPane = new JScrollPane(this.editorPane);
		TextLineNumber tln = new TextLineNumber(this.editorPane);
		scrollPane.setRowHeaderView( tln );
		this.panelCentral.add(scrollPane, gbc_editorPane);
		this.editorPane.addKeyListener(this);
		this.decideEditorEnabled();
	}
	
	//Cria e retorna um sub item de menu
	//Entrada: Nome do sub menu e comando ativado ao clicar no sub menu gerado
	//Retorno: Menu Item gerado
	//Pŕe-condição: Nenhuma
	//Pós-condição: O Menu Item é gerado e retornado
	private JMenuItem createMenuItem(String name, String actionCommand) {
		JMenuItem menuItem;
		menuItem = new JMenuItem(name);
		menuItem.setForeground(this.MenuForeGroundColor);
		menuItem.setBackground(this.sideAreasColor);
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
		menu.setBackground(this.sideAreasColor);
		menu.setForeground(this.MenuForeGroundColor);
		for(JMenuItem j:itens) {
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
		menuBar.setBackground(this.MenuBarColor);
		
		//Botão File
		menuBar.add(
			this.createMenu(
			    "File", //Menu File
				new JMenuItem[]{
					this.createMenuItem("New File","buttonNewFilePressed"), //Sub botão new file
					this.createMenuItem("Open File", "buttonOpenFilePressed") //Sub botão open file
				}
			)
		);
				
		//Botão save
		menuBar.add(
			this.createMenu(
				"Save", //Menu Save
				new JMenuItem[] {
					this.createMenuItem("Save as", "buttonSaveAsPressed"), //Sub botão Save as
					this.createMenuItem("Save", "buttonSavePressed") //Sub botão Save
				}
			)
		);
		
		//Botão settings
		menuBar.add(
			this.createMenu(
				"Settings", //Menu Settings
				new JMenuItem[] {
					this.createMenuItem("Editor", "buttonEditorPressed"), //Sub botão Editor
					this.createMenuItem("Themes", "buttonThemesPressed") //Sub botão Themes
				}
			)
		);
		
		//Adicionando a menuBar à interface
		this.frame.setJMenuBar(menuBar);
	}
	
	//Launcher da tela de configuração do editor
	private void launchEditorConfigFrame() {		
		this.editorConfigFrame = new JDialog();
		this.editorConfigFrame.getContentPane().setBackground(this.sideAreasColor);
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
		labelFontSize.setForeground(this.MenuForeGroundColor);
		this.editorConfigFrame.getContentPane().add(labelFontSize);
		
		//Spinner para escolher o tamanho da fonte
		this.fontSizeSpinner = new JSpinner();
		this.fontSizeSpinner.setValue(this.fontSize);
		this.fontSizeSpinner.setSize(35, 20);
		this.fontSizeSpinner.setLocation(100, 7);
		this.editorConfigFrame.getContentPane().add(this.fontSizeSpinner);
		
		JLabel labelFontType;
		labelFontType = new JLabel();
		labelFontType.setText("Font Type");
		labelFontType.setSize(75, 20);
		labelFontType.setLocation(10, 40);
		labelFontType.setForeground(this.MenuForeGroundColor);
		this.editorConfigFrame.getContentPane().add(labelFontType);
		
		//ComboBox para escolher o tipo da fonte
		@SuppressWarnings("deprecation")
		String fontTypes[] = Toolkit.getDefaultToolkit().getFontList();
		
		this.fontTypeList = new JComboBox(fontTypes);
		this.fontTypeList.setSelectedItem(this.fontType);
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
	private void saveFile() {
		if(this.currentFile != null) {
			try {
				PrintWriter print_line = new PrintWriter(new FileWriter(this.currentFile.toString()));
				print_line.printf("%s", this.editorPane.getText());
				print_line.close();
				JOptionPane.showMessageDialog(null, "File Saved");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error while trying to save the file");
			}
		}else {
			JOptionPane.showMessageDialog(null, "No Files Open");
		}
	}
	
	private int defineSaveFileFrame() {
		this.saveFileFrame = new JDialog();
		this.saveFileFrame.getContentPane().setBackground(this.sideAreasColor);
		this.saveFileFrame.setSize(600, 600);
		this.saveFileFrame.setLocationRelativeTo(null);
		this.saveFileFrame.setTitle("Save File As");
		
		this.chooseSaveDirectory = new JFileChooser();
		this.chooseSaveDirectory.setCurrentDirectory(new File("."));
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
	private void saveFileAs(){
		if(this.currentFile != null) {
			String fileSeparator = System.getProperty("file.separator");
			
			if(this.defineSaveFileFrame() == JFileChooser.APPROVE_OPTION) { //Salvar o arquivo
				File directory = this.chooseSaveDirectory.getSelectedFile();
				if(directory != null) {	
					String fileName;
					fileName = JOptionPane.showInputDialog("File Name");
					if(fileName != null) {
						File newFile = new File(directory.toString() + fileSeparator + fileName);
						try {
							if(newFile.createNewFile()) {
								this.currentFile = newFile;
								this.saveFile();
							}else {
								JOptionPane.showMessageDialog(null, "File Already Exists");
							}
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Error while trying to save the file");
						}
					}else {
						JOptionPane.showMessageDialog(null, "Invalid File Name");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Invalid Directory");
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "No Files Open");
		}
	}
	
	//Função que abre um menu para escolher um arquivo para abrir
	//Entrada: Nenhuma
	//Retorno: Nenhum
	//Pŕe-condição: Nenhuma
	//Pós-condição: O arquivo é aberto no editor
	private void openFile() {			
		this.openFileFrame = new JDialog();
		this.openFileFrame.getContentPane().setBackground(this.sideAreasColor);
		this.openFileFrame.setSize(600, 600);
		this.openFileFrame.setLocationRelativeTo(null);
		this.openFileFrame.setTitle("Save File");
			
		JFileChooser chooseOpenFile;
		chooseOpenFile = new JFileChooser();
		chooseOpenFile.setCurrentDirectory(new File("."));
		chooseOpenFile.setDialogTitle("Save to");
		chooseOpenFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int result = chooseOpenFile.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) { //Abre o arquivo
			File file = new File(chooseOpenFile.getSelectedFile().toString());
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String contentToLoad="",aux;
				try {
					while((aux = br.readLine()) != null) {
						contentToLoad+=aux;
						contentToLoad+="\n";
					}
					this.editorPane.setText(contentToLoad);
					this.currentFile = file;
					this.decideEditorEnabled();
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
	
	//Função que atualiza a fonte utilizada no editor
	//Entrada: Nenhuma
	//Retorno: Nenhum
	//Pŕe-condição: Nenhuma
	//Pós-condição: A fonte é atualizada para as opções definidas no menu
	private void updateFont() {
		this.editorConfigFrame.dispose();
		this.fontSize = Integer.parseInt(this.fontSizeSpinner.getValue().toString());
		this.fontType = this.fontTypeList.getSelectedItem().toString();
		this.editorPane.setFont(new Font(this.fontType, Font.PLAIN, this.fontSize));
	}
	
	//Função que cria um novo arquivo caso ele já não exista
	//Entrada: Nenhuma
	//Retorno: Nenhum
	//Pŕe-condição: Nenhuma
	//Pós-condição: O arquivo é criado e salvo no disco
	private void createNewFile() {
		if(this.currentFile == null) {
			String fileSeparator = System.getProperty("file.separator");
			
			this.createNewFileFrame = new JDialog();
			this.createNewFileFrame.getContentPane().setBackground(this.sideAreasColor);
			this.createNewFileFrame.setSize(600, 600);
			this.createNewFileFrame.setLocationRelativeTo(null);
			this.createNewFileFrame.setTitle("Save File As");
			
			JFileChooser chooseNewFileDirectory;
			chooseNewFileDirectory = new JFileChooser();
			chooseNewFileDirectory.setCurrentDirectory(new File("."));
			chooseNewFileDirectory.setDialogTitle("Save to");
			chooseNewFileDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooseNewFileDirectory.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) { //Salvar o arquivo
				File directory = chooseNewFileDirectory.getSelectedFile();
				String fileName;
				fileName = JOptionPane.showInputDialog("File Name");
				if(fileName != null) {
					File newFile = new File(directory.toString() + fileSeparator + fileName);
					try {
						if(newFile.createNewFile()) {
							JOptionPane.showMessageDialog(null, "File Created");
							this.currentFile = newFile;
                                                        this.decideEditorEnabled();
						}else {
							JOptionPane.showMessageDialog(null, "File Already Exists");
						}
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error while trying to create the file");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Invalid File Name");
				}
			}
			this.createNewFileFrame.getContentPane().add(chooseNewFileDirectory);
			
			this.createNewFileFrame.setVisible(true);
			this.createNewFileFrame.dispose();
		}else {
			this.saveFile();
			this.currentFile = null;
			this.createNewFile();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("buttonThemesPressed".equals(e.getActionCommand())) {
			JOptionPane.showMessageDialog(null, "Button Themes Pressed");
		}else if("buttonEditorPressed".equals(e.getActionCommand())) {
			this.launchEditorConfigFrame();
		}else if("buttonNewFilePressed".equals(e.getActionCommand())) {
			this.createNewFile();
		}else if("FontSizeChanged".equals(e.getActionCommand())) {
			this.updateFont();
		}else if("buttonSaveAsPressed".equals(e.getActionCommand())) {
			this.saveFileAs();
		}else if("buttonOpenFilePressed".equals(e.getActionCommand())) {
			this.openFile();
		}else if("buttonSavePressed".equals(e.getActionCommand())) {
			this.saveFile();
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
                this.defineFilesToolBar();
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

	@Override
	public void keyReleased(KeyEvent e) {
		//Salva o arquivo aberto ao apertar ctrl+s
		if (this.editorPane != null && e.isShiftDown() == false && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
			this.saveFile();
		}	
		
                //Salva como o arquivo ao apertar ctrl+shift+s
		if (this.editorPane != null && e.isShiftDown() && e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
			this.saveFileAs();
		}
		
                //Resposta do botão OK da tela de configuração do editor
		if(this.editorConfigFrame != null && this.editorConfigFrame.isActive() && e.getKeyCode() == 27) {
			this.updateFont();
		}
                
                if (e.isControlDown() && e.getKeyChar() != 'o' && e.getKeyCode() == 79) {
                    this.openFile();
		}	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
