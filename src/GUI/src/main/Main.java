/*  Versão: 1.0.0
 *  Data: 21/04/2020, 18:25
 *  Autores: Caio
 */

package main;


import java.awt.EventQueue;
import gui.GUI;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
