package gui.maingui.interfacegenerator;

import java.io.File;
import javax.swing.JFileChooser;

public class JFileChooserGenerator {
    private JFileChooserGenerator() {
    }

    // Função que define um JFileChooser genérico
    // Entrada: Pasta em que será aberto o JFileChooser, título do JFileChooser e modo de abertura do JFileChooser
    // Retorno: JFileChooser gerado
    // Pré-condição: Nenhuma
    // Pós-condição: O JFileChooser é criado e retornado
    public static JFileChooser createJFileChooser(String folder, String title, int mode) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(folder));
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(mode);
        return chooser;
    }
}