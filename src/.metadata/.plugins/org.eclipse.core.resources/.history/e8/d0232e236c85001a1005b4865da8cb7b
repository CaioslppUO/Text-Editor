package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.PlainDocument;

public class Gui implements ActionListener, KeyListener{

	private JFrame frame;
	private JPanel panelCentral;
	private JPanel panelLeft;
	private JPanel panelTop;
	private JPanel panelDown;
	private JPanel panelRight;
	
	private Color PaneEditorColor;
	private Color MenuBarColor;
	private Color sideAreasColor;
	private Color MenuForeGroundColor;
	
	private Integer fontSize;
	private String fontType;
	private JEditorPane editorPane;
	private JDialog editorConfigFrame;
	private JSpinner fontSizeSpinner;
	private JComboBox fontTypeList;
	
	private JDialog saveFileFrame;
	
	private File currentFile;
	private JDialog openFileFrame;

	public Gui() {
		this.PaneEditorColor = new Color(51, 51, 51);
		this.sideAreasColor = new Color(82, 82, 82);
		this.MenuBarColor = new Color(28, 28, 28);
		this.MenuForeGroundColor = new Color(137, 163, 201);
		this.fontSize = 12;
		this.fontType = "Monospaced";
		this.currentFile = null;
		initialize();
	}
	
	public JFrame getMainFrame() {
		return this.frame;
	}
	
	public void defineMainFrame() {
		this.frame = new JFrame("Text Editor");
		this.frame.getContentPane().setBackground(this.sideAreasColor);
		this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}
	
	public void definePanelCentral() {
		this.panelCentral = new JPanel();
		this.panelCentral.setBackground(this.PaneEditorColor);
		this.frame.getContentPane().add(this.panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0};
		gbl_panelCentral.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.panelCentral.setLayout(gbl_panelCentral);
	}
	
	private void definePanelLeft() {
		this.panelLeft = new JPanel();
		this.panelLeft.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.panelLeft.setBackground(this.sideAreasColor);
		this.frame.getContentPane().add(this.panelLeft, BorderLayout.WEST);
		Component horizontalStrut = Box.createHorizontalStrut(110);
		this.panelLeft.add(horizontalStrut);
	}
	
	private void definePanelTop() {
		this.panelTop = new JPanel();
		this.panelTop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.panelTop.setBackground(this.sideAreasColor);
		frame.getContentPane().add(this.panelTop, BorderLayout.NORTH);
		Component verticalStrut = Box.createVerticalStrut(50);
		this.panelTop.add(verticalStrut);
	}
	
	private void definePanelDown() {
		this.panelDown = new JPanel();
		this.panelDown.setBackground(this.sideAreasColor);
		this.frame.getContentPane().add(this.panelDown, BorderLayout.SOUTH);
	}
	
	private void definePanelRight() {
		this.panelRight = new JPanel();
		this.panelRight.setBackground(this.sideAreasColor);
		this.frame.getContentPane().add(this.panelRight, BorderLayout.EAST);
	}
	
	public void defineEditorPane() {
		this.editorPane = new JEditorPane();
		this.editorPane.setForeground(SystemColor.window);
		this.editorPane.setBackground(this.PaneEditorColor);
		GridBagConstraints gbc_editorPane_1 = new GridBagConstraints();
		gbc_editorPane_1.fill = GridBagConstraints.BOTH;
		gbc_editorPane_1.gridx = 0;
		gbc_editorPane_1.gridy = 0;
		
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
		this.panelCentral.add(scrollPane, gbc_editorPane_1);
		this.editorPane.addKeyListener(this);
		if(this.currentFile != null) {
			this.editorPane.setEnabled(true);
		}else {
			this.editorPane.setEnabled(false);
		}
	}
	
	private void defineMenuBar() {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		//Inicializando a barra do menu
		menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		menuBar.setBackground(this.MenuBarColor);
		
		//Inicializando os menus e os itens dos menus
		
		//Botão new
		menu = new JMenu("File");
		menu.setBackground(this.sideAreasColor);
		menu.setForeground(this.MenuForeGroundColor);
		
		//Sub botão new file
		menuItem = new JMenuItem("New File");
		menuItem.setForeground(this.MenuForeGroundColor);
		menuItem.setBackground(this.sideAreasColor);
		menuItem.setActionCommand("buttonNewFilePressed");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		//Sub botão open file
		menuItem = new JMenuItem("Open File");
		menuItem.setForeground(this.MenuForeGroundColor);
		menuItem.setBackground(this.sideAreasColor);
		menuItem.setActionCommand("buttonOpenFilePressed");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		//Adicionando o botão new ao menu bar
		menuBar.add(menu);
		
		//Botão save
		menu = new JMenu("Save");
		menu.setBackground(this.sideAreasColor);
		menu.setForeground(this.MenuForeGroundColor);
		
		//Sub botão save as
		menuItem = new JMenuItem("Save as");
		menuItem.setForeground(this.MenuForeGroundColor);
		menuItem.setBackground(this.sideAreasColor);
		menuItem.setActionCommand("buttonSaveAsPressed");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		//Sub botão save
		menuItem = new JMenuItem("Save");
		menuItem = new JMenuItem("Save as");
		menuItem.setForeground(this.MenuForeGroundColor);
		menuItem.setBackground(this.sideAreasColor);
		menuItem.setActionCommand("buttonSavePressed");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		//Adicionando o botão save ao menu bar
		menuBar.add(menu);
		
		//Botão settings
		menu = new JMenu("Settings");
		menu.setBackground(this.sideAreasColor);
		menu.setForeground(this.MenuForeGroundColor);
		
		//Sub botão editor
		menuItem = new JMenuItem("Editor");
		menuItem.setForeground(this.MenuForeGroundColor);
		menuItem.setBackground(this.sideAreasColor);
		menuItem.setActionCommand("buttonEditorPressed");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		//Sub botão themes
		menuItem = new JMenuItem("Themes");
		menuItem.setForeground(this.MenuForeGroundColor);
		menuItem.setBackground(this.sideAreasColor);
		menuItem.setActionCommand("buttonThemesPressed");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		//Adicionando o botão settings ao menu bar
		menuBar.add(menu);
		
		//Adding Itens
		this.frame.setJMenuBar(menuBar);
	}
	
	private void launchEditorConfigFrame() {		
		this.editorConfigFrame = new JDialog();
		this.editorConfigFrame.getContentPane().setBackground(this.sideAreasColor);
		this.editorConfigFrame.setSize(300, 300);
		this.editorConfigFrame.setLocationRelativeTo(null);
		this.editorConfigFrame.setTitle("Editor Settings");
		this.editorConfigFrame.setLayout(null);
		
		JButton buttonOk = new JButton("OK");
		buttonOk.setActionCommand("FontSizeChanged");
		buttonOk.addActionListener(this);
		buttonOk.setSize(55, 30);
		buttonOk.setLocation(125, 230);
		
		JLabel labelFontSize;
		labelFontSize = new JLabel();
		labelFontSize.setText("Font Size");
		labelFontSize.setSize(75, 20);
		labelFontSize.setLocation(10, 5);
		labelFontSize.setForeground(this.MenuForeGroundColor);
		this.editorConfigFrame.add(labelFontSize);
		
		this.fontSizeSpinner = new JSpinner();
		this.fontSizeSpinner.setValue(this.fontSize);
		this.fontSizeSpinner.setSize(35, 20);
		this.fontSizeSpinner.setLocation(100, 7);
		this.editorConfigFrame.add(this.fontSizeSpinner);
		
		JLabel labelFontType;
		labelFontType = new JLabel();
		labelFontType.setText("Font Type");
		labelFontType.setSize(75, 20);
		labelFontType.setLocation(10, 40);
		labelFontType.setForeground(this.MenuForeGroundColor);
		this.editorConfigFrame.add(labelFontType);
		
		@SuppressWarnings("deprecation")
		String fontTypes[] = Toolkit.getDefaultToolkit().getFontList();
		
		this.fontTypeList = new JComboBox(fontTypes);
		this.fontTypeList.setSelectedItem(this.fontType);
		this.fontTypeList.setSize(120, 20);
		this.fontTypeList.setLocation(100, 42);
		this.editorConfigFrame.add(this.fontTypeList);
		
		this.editorConfigFrame.add(buttonOk);
		
		this.editorConfigFrame.setVisible(true);
	}
	
	private void saveFile() {
		if(this.currentFile != null) {
			String contentToSave = this.editorPane.getText();
			FileWriter writer;
			try {
				writer = new FileWriter(this.currentFile.toString());
				PrintWriter print_line = new PrintWriter(writer);
				print_line.printf("%s", contentToSave);
				print_line.close();
				JOptionPane.showMessageDialog(null, "File Saved");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error while trying to save the file");
			}
		}
	}
	
	private void saveFileAs(){
		if(this.currentFile != null) {
			String fileSeparator = System.getProperty("file.separator");
			
			this.saveFileFrame = new JDialog();
			this.saveFileFrame.getContentPane().setBackground(this.sideAreasColor);
			this.saveFileFrame.setSize(600, 600);
			this.saveFileFrame.setLocationRelativeTo(null);
			this.saveFileFrame.setTitle("Save File As");
			
			String contentToSave;
			contentToSave = this.editorPane.getText();
			
			JFileChooser chooseSaveDirectory;
			chooseSaveDirectory = new JFileChooser();
			chooseSaveDirectory.setCurrentDirectory(new File("."));
			chooseSaveDirectory.setDialogTitle("Save to");
			chooseSaveDirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooseSaveDirectory.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) { //Salvar o arquivo
				File directory = chooseSaveDirectory.getSelectedFile();
				String fileName;
				fileName = JOptionPane.showInputDialog("File Name");
				File newFile = new File(directory.toString() + fileSeparator + fileName);
				try {
					if(newFile.createNewFile()) {
						FileWriter writer = new FileWriter(directory.toString() + fileSeparator + fileName, false);
						PrintWriter print_line = new PrintWriter(writer);
						print_line.printf("%s", contentToSave);
						print_line.close();
						JOptionPane.showMessageDialog(null, "File Saved");
					}else {
						JOptionPane.showMessageDialog(null, "File Already Exists");
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error while trying to save the file");
				}
			}
			this.saveFileFrame.add(chooseSaveDirectory);
			
			this.saveFileFrame.setVisible(true);
			this.saveFileFrame.dispose();
		}else {
			JOptionPane.showMessageDialog(null, "No Files Open");
		}
	}
	
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
					this.editorPane.setEnabled(true);
				} catch (IOException e) {
					//do Nothing
				}
				
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Error while trying to load file");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Invalid File Type");
		}
		this.openFileFrame.add(chooseOpenFile);
		
		this.openFileFrame.setVisible(true);
		this.openFileFrame.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("buttonThemesPressed".equals(e.getActionCommand())) {
			JOptionPane.showMessageDialog(null, "Button Themes Pressed");
		}else if("buttonEditorPressed".equals(e.getActionCommand())) {
			this.launchEditorConfigFrame();
		}else if("buttonNewFilePressed".equals(e.getActionCommand())) {
			JOptionPane.showMessageDialog(null, "Button New File Pressed");
		}else if("FontSizeChanged".equals(e.getActionCommand())) {
			this.editorConfigFrame.dispose();
			this.fontSize = Integer.parseInt(this.fontSizeSpinner.getValue().toString());
			this.fontType = this.fontTypeList.getSelectedItem().toString();
			this.editorPane.setFont(new Font(this.fontType, Font.PLAIN, this.fontSize));
		}else if("buttonSaveAsPressed".equals(e.getActionCommand())) {
			this.saveFileAs();
		}else if("buttonOpenFilePressed".equals(e.getActionCommand())) {
			this.openFile();
		}else if("buttonSavePressed".equals(e.getActionCommand())) {
			this.saveFile();
		}
	}
	
	private void initialize() {
		//Definições dos elementos principais da tela
		this.defineMainFrame();
		this.definePanelTop();
		this.definePanelCentral();
		this.definePanelLeft();
		this.definePanelRight();
		this.definePanelDown();
		
		//Definições dos elementos secundários de cada espaço na tela
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
		if (e.isControlDown() && e.getKeyChar() != 's' && e.getKeyCode() == 83) {
			this.saveFile();
		}	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
