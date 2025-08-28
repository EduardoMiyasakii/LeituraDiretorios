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
			try {
				listarSubdiretorios(pastaSelecionada);
			} catch (ParserConfigurationException | TransformerException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Nenhuma pasta foi selecionada.");
		}
	}

	private static void listarSubdiretorios(File pasta)
			throws ParserConfigurationException, TransformerException {
		
		File[] arquivos = pasta.listFiles();

		if (arquivos != null) {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = (Document) docBuilder.newDocument();

			Element rootElement = doc.createElement("?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?");
			doc.appendChild(rootElement);

			Element eclipseVersion = doc.createElement("<eclipse-userlibraries version=\"2\">");
			rootElement.appendChild(eclipseVersion);

			System.out.println("Subdiretórios encontrados:");
			for (File arquivo : arquivos) {

				if (arquivo.isDirectory()) {
					Element libraryName = doc
							.createElement("<library name=\"${arquivo.getName()} \" systemlibrary=\"false\">");
					rootElement.appendChild(libraryName);
					System.out.println(" - " + arquivo.getName());
					listarArquivos(arquivo);

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();

					transformer.setOutputProperty(OutputKeys.INDENT, "yes");

					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("all.userlibraries.xml"));

					transformer.transform(source, result);
					System.out.println("Arquivo XML gerado com sucesso!");
				}
			}
		} else {
			System.out.println("Não foi possível listar os arquivos da pasta.");
		}
	}

	private static void listarArquivos(File subDiretorio) throws ParserConfigurationException {
		
		File[] arquivos = subDiretorio.listFiles();
		
		if(arquivos != null) {
			
			System.out.println("Jars encontrados:");
			for(File arquivo: arquivos) {
				
				if(arquivo.isFile()) {
					String jarNome = arquivo.getName()
					Element jarName = doc.createElement("<archive path='${jarNome}'/>"); 
					rootElement.appendChild(jarName);
				}
				else if(arquivo.isDirectory()){
					listarArquivos(arquivo.getAbsolutePath());
				}
			}
		}
	}
}

