package xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import hebrewLetters.HebrewLetters;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.net.URL;

public class readXML {
	public static void main(String argv[]) {
		try {
			// creating a constructor of file class and parsing an XML file
			// Load the directory as a resource
			URL url = ClassLoader.getSystemResource("xml/books/Genesis.xml");
			// Turn the resource into a File object
			File file = new File(url.toURI());
			// List the directory
			// an instance of factory that gives a document builder
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			// an instance of builder to parse the specified xml file
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			// Get all the transaction elements and then loop over them
			NodeList transaction = doc.getElementsByTagName("book");
			Node bookName = (Node) ((Element) ((Element) transaction.item(0)).getElementsByTagName("names").item(0))
					.getElementsByTagName("hebrewname").item(0);
			System.out.println("ספר:  " + bookName.getTextContent());

			for (int i = 0; i < transaction.getLength(); i++) {
				// Traverse down the transaction node till we get the billing info
				NodeList chapters = ((Element) transaction.item(0)).getElementsByTagName("c");
				for (int j = 0; j < chapters.getLength(); j++) {
					// Get all children nodes from billing info
					String attr = ((Element) chapters.item(j)).getAttribute("n");
					System.out.println("פרק: " + HebrewLetters.findHebrewLetters(Integer.valueOf(attr)));
					NodeList verses = ((Element) chapters.item(j)).getElementsByTagName("v");
					for (int k = 0; k < verses.getLength(); k++) {
						NodeList words = ((Element) verses.item(k)).getElementsByTagName("w");
						// Only want stuff from ELEMENT nodes
						attr = ((Element)verses.item(k)).getAttributes().getNamedItem("n").getNodeValue();
						System.out.println(
								"פסוק " + HebrewLetters.findHebrewLetters(Integer.valueOf(attr)) + ": ");
						for (int l = 0; l < words.getLength(); l++) {
							Node current = words.item(l);
							if ((current.getNodeType() == Node.ELEMENT_NODE)) {
								System.out.print(HebrewLetters.removeVowels(current.getTextContent().replace("/", "").replace("־", ""))+" ");
							}
						}
						System.out.println();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
