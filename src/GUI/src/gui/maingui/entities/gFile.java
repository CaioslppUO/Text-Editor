package gui.maingui.entities;

import java.io.File;
import gui.Gui;

public class gFile {
    private String fullPath;
    private String initialContent;
    private String fileName;
    private Boolean isSaved;
    private Boolean isModified;
    private Boolean isOpen;
    private File file;

    private static gFile instance;

    // Construtor da classe gFile
    // Entrada: Nenhum
    // Retorno: Instancia da classe
    // Pré-condição: Nenhuma
    // Pós-condição: A classe é instanciada e o arquivo aberto é nulo
    private gFile() {
        this.file = null;
        this.fullPath = null;
        this.fileName = null;
        this.isSaved = null;
        this.isModified = null;
        this.initialContent = null;
        this.isOpen = false;
    }

    // Função que verifica se um arquivo foi ou não modificado, ajustando o valor
    // interno da classe
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: A variável this.isModified é atualizada
    public void checkModification() {
        if (this.file != null) {
            if (this.initialContent.equals(this.getFileContent())) {
                this.isModified = false;
            } else {
                this.isModified = true;
                this.isSaved = false;
            }
        } else {
            this.isModified = null;
        }
    }

    // Função que retorna o conteúdo de um arquivo
    // Entrada: Arquivo para ser lido
    // Retorno: Conteúdo lido do arquivo
    // Pré-condição: O arquivo passado como parâmetro deve ser um arquivo válido
    // para leitura
    // Pós-condição: O arquivo é lido e seu conteúdo é retornado
    private String getFileContent() {
        return Gui.getInstance().getEditorPane().getEditorPane().getText();
    }

    // Função que fecha o arquivo que está aberto
    // Entrada: Nenhuma
    // Retorno: Nenhum
    // Pré-condição: Nenhuma
    // Pós-condição: O arquivo aberto é fechado
    public void closeFile() {
        this.file = null;
        this.fullPath = null;
        this.fileName = null;
        this.isSaved = null;
        this.isModified = null;
        this.initialContent = null;
        this.isOpen = false;
    }

    // Função que retorna a instância da classe
    // Entrada: Nenhuma
    // Retorno: Instância da classe
    // Pré-condição: Nenhuma
    // Pós-condição: A instância da classe é retornada
    public static gFile getInstance() {
        if (instance == null)
            instance = new gFile();
        return instance;
    }

    // Setter do arquivo atualmente aberto
    public void setFile(String path) {
        try {
            this.file = new File(path);
            this.fullPath = path;
            this.fileName = this.file.getName();
            this.isSaved = true;
            this.isModified = false;
            this.initialContent = this.getFileContent();
            this.isOpen = true;
        } catch (Exception e) {
        }
    }

    // Setter do arquivo atualmente aberto
    public void setFile(File file){
        try {
            this.file = file;
            this.fullPath = this.file.getAbsolutePath();
            this.fileName = this.file.getName();
            this.isSaved = true;
            this.isModified = false;
            this.initialContent = this.getFileContent();
            this.isOpen = true;
        } catch (Exception e) {
        }
    }

    // Setter da verificação se o arquivo está ou não está salvo
    public void setIsSaved(Boolean isSaved) {
        this.isSaved = isSaved;
    }

    // Setter da verificação se o arquivo está ou não aberto
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    // Getter do arquivo
    public File getFile() {
        return this.file;
    }

    // Getter do caminho completo do arquivo
    public String getFullPath() {
        return this.fullPath;
    }

    // Getter do nome do arquivo
    public String getName() {
        return this.fileName;
    }

    // Getter da verificação de se o arquivo está salvo
    public Boolean isSaved() {
        return this.isSaved;
    }

    // Getter da verificação se o arquivo foi modificado
    public Boolean isModified() {
        return this.isModified;
    }

    // Getter do conteúdo inicial do arquivo
    public String getInitialContent() {
        return this.initialContent;
    }

    // Getter do conteúdo atual do arquivo
    public String getCurrentContent() {
        return this.getFileContent();
    }

    // Getter da verificação de se o arquivo está aberto
    public Boolean isOpen() {
        return this.isOpen;
    }

}