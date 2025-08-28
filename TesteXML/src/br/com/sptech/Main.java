package br.com.sptech;

import java.io.File;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int retorno = chooser.showOpenDialog(null);

        if (retorno == JFileChooser.APPROVE_OPTION) {
            File pastaSelecionada = chooser.getSelectedFile();
            System.out.println("Pasta selecionada: " + pastaSelecionada.getAbsolutePath());

            gerarXML(pastaSelecionada);
        } else {
            System.out.println("Nenhuma pasta foi selecionada.");
        }
    }

    private static void gerarXML(File pasta) throws ParserConfigurationException, TransformerException {

    	// criando documento
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // elemento raiz
        Element rootElement = doc.createElement("eclipse-userlibraries");
        rootElement.setAttribute("version", "2");
        doc.appendChild(rootElement);

        // listando diretórios e arquivos
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isDirectory()) {
                    System.out.println("Diretório: " + arquivo.getName());

                    // <library name="ibpj_banc_contratacao" systemlibrary="false">
                    Element library = doc.createElement("library");
                    library.setAttribute("name", arquivo.getName());
                    library.setAttribute("systemlibrary", "false");
                    rootElement.appendChild(library);

                    // adicionando arquivos desse diretório
                    listarArquivos(arquivo, doc, library);
                }
            }
        }

        // salvando XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("all.userlibraries.xml"));
        transformer.transform(source, result);

        System.out.println("Arquivo XML gerado!!!!");
    }

    private static void listarArquivos(File subDiretorio, Document doc, Element library) {
    	
        File[] arquivos = subDiretorio.listFiles();
        
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".jar")) {
                    System.out.println(" JAR encontrado: " + arquivo.getAbsolutePath());

                    Element archive = doc.createElement("archive");
                    archive.setAttribute("path", arquivo.getAbsolutePath());
                    library.appendChild(archive);
                } else if (arquivo.isDirectory()) {
                    listarArquivos(arquivo, doc, library);
                }
            }
        }
    }
}
