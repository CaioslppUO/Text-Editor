package gui.maingui.interfacegenerator;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JDialog;

public class JDialogGenerator {    
    private JDialogGenerator(){}

    //Função que define um JDialog genérico
    //Entrada: Dimensão do JDialog, Componente para centralizar o JDialog, título do JDialog, cor de fundo do JDialog
    //Retorno: JDialog gerado
    //Pré-condição: Nenhuma
    //Pós-condição: O JDialog é criado e retornado
    public static JDialog createJDialog(Dimension size, Component relativeLocation, String title, Color backGroundColor){
        JDialog newDialog = new JDialog();
        newDialog.setTitle(title);
        newDialog.setSize(size);
        newDialog.setLocationRelativeTo(relativeLocation);
        newDialog.setBackground(backGroundColor);
        return newDialog;
    }
}