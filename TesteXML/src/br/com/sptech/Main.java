package br.com.sptech;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main {

	public static void main(String[] args) throws ParserConfigurationException {

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

	 private static void listarSubdiretorios(File pasta) throws ParserConfigurationException {
	        File[] arquivos = pasta.listFiles();

	        if (arquivos != null) {
	        	
	        	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        	
	        	Document doc = (Document) docBuilder.newDocument();
	        	
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

/*
 try {
            // Criar o documento XML vazio
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            // Elemento raiz
            Element rootElement = doc.createElement("empresa");
            doc.appendChild(rootElement);

            // Sub-elemento 1
            Element funcionario = doc.createElement("funcionario");
            rootElement.appendChild(funcionario);

            // Adicionando atributos
            funcionario.setAttribute("id", "1");

            // Nome
            Element nome = doc.createElement("nome");
            nome.appendChild(doc.createTextNode("Eduardo"));
            funcionario.appendChild(nome);

            // Cargo
            Element cargo = doc.createElement("cargo");
            cargo.appendChild(doc.createTextNode("Desenvolvedor"));
            funcionario.appendChild(cargo);

            // Transformar o documento em arquivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Formatar saída (indentação bonita)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("empresa.xml"));

            transformer.transform(source, result);

            System.out.println("Arquivo XML gerado com sucesso!");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
    <empresa>
    <funcionario id="1">
        <nome>Eduardo</nome>
        <cargo>Desenvolvedor</cargo>
    </funcionario>
</empresa>

    */ 


