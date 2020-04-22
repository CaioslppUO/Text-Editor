package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.awt.Insets;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.PlainDocument;

import org.w3c.dom.Document;

import javax.swing.border.BevelBorder;
import javax.swing.JEditorPane;
import java.awt.SystemColor;
import javax.swing.DropMode;
import java.awt.Font;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JFileChooser;

public class Gui {

	private JFrame frame;
	private JPanel panelCentral;
	private JEditorPane editorPane;
	private JPanel panelLeft;
	private JPanel panelTop;
	private JPanel panelDown;
	private JPanel panelRight ;

	public Gui() {
		initialize();
	}
	
	
	public JFrame getMainFrame() {
		return this.frame;
	}
	
	public void defineMainFrame() {
		this.frame = new JFrame();
		this.frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}
	
	public void definePanelCentral() {
		this.panelCentral = new JPanel();
		this.panelCentral.setBackground(Color.GRAY);
		this.frame.getContentPane().add(this.panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0};
		gbl_panelCentral.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.panelCentral.setLayout(gbl_panelCentral);
	}
	
	public void defineEditorPane() {
		this.editorPane = new JEditorPane();
		this.editorPane.setForeground(SystemColor.window);
		this.editorPane.setBackground(Color.GRAY);
		GridBagConstraints gbc_editorPane_1 = new GridBagConstraints();
		gbc_editorPane_1.fill = GridBagConstraints.BOTH;
		gbc_editorPane_1.gridx = 0;
		gbc_editorPane_1.gridy = 0;
		
		//Mudando a quantidade de espaçõs da tecla TAB
		javax.swing.text.Document doc = this.editorPane.getDocument();
		doc.putProperty(PlainDocument.tabSizeAttribute, 2);
		
		//Incluindo o contador de linhas
		//Comentar as pŕoximas 4 linhas para utilizar o Design do eclipse
		JScrollPane scrollPane = new JScrollPane(this.editorPane);
		TextLineNumber tln = new TextLineNumber(this.editorPane);
		scrollPane.setRowHeaderView( tln );
		this.panelCentral.add(scrollPane, gbc_editorPane_1);
	}
	
	private void definePanelLeft() {
		this.panelLeft = new JPanel();
		this.panelLeft.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.panelLeft.setBackground(Color.LIGHT_GRAY);
		this.frame.getContentPane().add(this.panelLeft, BorderLayout.WEST);
		Component horizontalStrut = Box.createHorizontalStrut(110);
		this.panelLeft.add(horizontalStrut);
	}
	
	private void definePanelTop() {
		this.panelTop = new JPanel();
		this.panelTop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.panelTop.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(this.panelTop, BorderLayout.NORTH);
		Component verticalStrut = Box.createVerticalStrut(50);
		this.panelTop.add(verticalStrut);
	}
	
	private void definePanelDown() {
		this.panelDown = new JPanel();
		this.panelDown.setBackground(Color.LIGHT_GRAY);
		this.frame.getContentPane().add(this.panelDown, BorderLayout.SOUTH);
	}
	
	private void definePanelRight() {
		this.panelRight = new JPanel();
		this.panelRight.setBackground(Color.LIGHT_GRAY);
		this.frame.getContentPane().add(this.panelRight, BorderLayout.EAST);
	}

	private void initialize() {
		//Definições de cada elemento da tela
		this.defineMainFrame();
		this.definePanelTop();
		this.definePanelCentral();
		this.definePanelLeft();
		this.definePanelRight();
		this.definePanelDown();
		this.defineEditorPane();
		
		//Definições finais do Main Frame
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
