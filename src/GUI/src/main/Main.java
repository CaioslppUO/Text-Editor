/*  Versão: 0.1.0
 *  Data: 21/04/2020, 18:25
 *  Autores: Caio
 */

package main;


import java.awt.EventQueue;
import gui.Gui;

public class Main {
	
	//Inicia a janela
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.getMainFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}