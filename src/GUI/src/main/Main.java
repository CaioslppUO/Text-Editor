package main;

import java.awt.EventQueue;
import gui.Gui;

public class Main {
	//Inicia a janela
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = Gui.getInstance();
					window.getMainFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
