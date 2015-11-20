package com.chat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SmileyCodes {

	private static HashMap<String, String> smileyCodes = null;

	public static void main(String args[]) {
		new SmileyCodes().replaceCodeBySmiley();
	}

	public HashMap<String, String> getSmileyMap() {
		if (smileyCodes == null) {
			replaceCodeBySmiley();
		}
		return smileyCodes;

	}

	private void replaceCodeBySmiley() {

		try {

			if (smileyCodes == null) {
				SmileyCodes testMain = new SmileyCodes();
				InputStream st = testMain.getClass().getResourceAsStream("/SmileyCode.xml");

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;

				smileyCodes = new HashMap<String, String>();
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(st);

				// Get Root Node and its child
				Node root = doc.getDocumentElement();
				NodeList childNodes = root.getChildNodes();

				for (int i = 0; i < childNodes.getLength(); i++) {
					replaceCodeBySmiley(childNodes.item(i));
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void replaceCodeBySmiley(Node node) {

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) node;
			String[] keyVal = e.getTextContent().trim().split("#");
			smileyCodes.put(keyVal[0].trim(), keyVal[1].trim());
		}
	}

	private String nodeType(short type) {
		switch (type) {
		case Node.ELEMENT_NODE:
			return "Element";
		case Node.DOCUMENT_TYPE_NODE:
			return "Document type";
		case Node.ENTITY_NODE:
			return "Entity";
		case Node.ENTITY_REFERENCE_NODE:
			return "Entity reference";
		case Node.NOTATION_NODE:
			return "Notation";
		case Node.TEXT_NODE:
			return "Text";
		case Node.COMMENT_NODE:
			return "Comment";
		case Node.CDATA_SECTION_NODE:
			return "CDATA Section";
		case Node.ATTRIBUTE_NODE:
			return "Attribute";
		case Node.PROCESSING_INSTRUCTION_NODE:
			return "Attribute";
		}
		return "Unidentified";
	}

}
