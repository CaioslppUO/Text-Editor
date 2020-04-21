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

public class GUI {

	public JFrame frame;

	public GUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentral = new JPanel();
		panelCentral.setBackground(Color.GRAY);
		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentral = new GridBagLayout();
		gbl_panelCentral.columnWidths = new int[]{0, 0};
		gbl_panelCentral.rowHeights = new int[]{0, 0};
		gbl_panelCentral.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelCentral.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelCentral.setLayout(gbl_panelCentral);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setForeground(SystemColor.window);
		editorPane_1.setBackground(Color.GRAY);
		GridBagConstraints gbc_editorPane_1 = new GridBagConstraints();
		gbc_editorPane_1.fill = GridBagConstraints.BOTH;
		gbc_editorPane_1.gridx = 0;
		gbc_editorPane_1.gridy = 0;
		
		javax.swing.text.Document doc = editorPane_1.getDocument();
		doc.putProperty(PlainDocument.tabSizeAttribute, 2);

		
		JScrollPane scrollPane = new JScrollPane(editorPane_1);
		TextLineNumber tln = new TextLineNumber(editorPane_1);
		scrollPane.setRowHeaderView( tln );
		panelCentral.add(scrollPane, gbc_editorPane_1);
		
		JPanel panelLeft = new JPanel();
		panelLeft.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelLeft.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panelLeft, BorderLayout.WEST);
		
		Component horizontalStrut = Box.createHorizontalStrut(110);
		panelLeft.add(horizontalStrut);
		
		JPanel panelTop = new JPanel();
		panelTop.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelTop.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panelTop, BorderLayout.NORTH);
		
		Component verticalStrut = Box.createVerticalStrut(50);
		panelTop.add(verticalStrut);
		
		JPanel panelDown = new JPanel();
		panelDown.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panelDown, BorderLayout.SOUTH);
		
		JPanel panelRight = new JPanel();
		panelRight.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panelRight, BorderLayout.EAST);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setFont(new Font("Dialog", Font.PLAIN, 15));
		editorPane.setForeground(SystemColor.controlHighlight);
		editorPane.setBackground(Color.GRAY);
		GridBagConstraints gbc_editorPane = new GridBagConstraints();
		gbc_editorPane.fill = GridBagConstraints.BOTH;
		gbc_editorPane.gridx = 0;
		gbc_editorPane.gridy = 0;
	}

}
