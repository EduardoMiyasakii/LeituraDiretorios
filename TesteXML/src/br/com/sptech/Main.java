package br.com.sptech;

import java.io.File;

import javax.swing.JFileChooser;

public class Main {

	public static void main(String[] args) {

		 JFileChooser chooser = new JFileChooser();
		 
	        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	        int retorno = chooser.showOpenDialog(null);

	        if (retorno == JFileChooser.APPROVE_OPTION) {
	            File pastaSelecionada = chooser.getSelectedFile();
	            System.out.println("Pasta selecionada: " + pastaSelecionada.getAbsolutePath());
	            listarSubdiretorios(pastaSelecionada);
	        } else {
	            System.out.println("Nenhuma pasta foi selecionada.");
	        }
	}

	 private static void listarSubdiretorios(File pasta) {
	        File[] arquivos = pasta.listFiles();

	        if (arquivos != null) {
	            System.out.println("Subdiretórios encontrados:");
	            for (File arquivo : arquivos) {
	                if (arquivo.isDirectory()) {
	                    System.out.println(" - " + arquivo.getName());
	                }
	            }
	        } else {
	            System.out.println("Não foi possível listar os arquivos da pasta.");
	        }
	    }
}
