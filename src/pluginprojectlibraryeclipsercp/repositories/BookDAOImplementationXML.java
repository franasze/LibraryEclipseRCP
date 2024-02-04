package pluginprojectlibraryeclipsercp.repositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import pluginprojectlibraryeclipsercp.model.Book;
import pluginprojectlibraryeclipsercp.api.IBook;
import pluginprojectlibraryeclipsercp.api.IBookAPI;

public class BookDAOImplementationXML implements IBookAPI {
	@Override
	public List<IBook> getBooks() {

		List<IBook> books = new ArrayList<>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCP/resources/books.xml");
			NodeList booksList = doc.getElementsByTagName("book");
			for (int i = 0; i < booksList.getLength(); i++) {
				Element bookElement = (Element) booksList.item(i);

				String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
				String author = bookElement.getElementsByTagName("author").item(0).getTextContent();
				int publicationYear = Integer
						.parseInt(bookElement.getElementsByTagName("publicationYear").item(0).getTextContent());
				int ISBN = Integer.parseInt(bookElement.getElementsByTagName("ISBN").item(0).getTextContent());
				boolean status = Boolean
						.parseBoolean(bookElement.getElementsByTagName("status").item(0).getTextContent());

				books.add(new Book(title, author, publicationYear, ISBN, status));
			}
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return books;
	}

	public int getNextBookId(Document document) {

		int maxId = 0;

		NodeList bookNodes = document.getElementsByTagName("book");
		for (int i = 0; i < bookNodes.getLength(); i++) {
			Element bookElement = (Element) bookNodes.item(i);
			int currentId = Integer.parseInt(bookElement.getAttribute("id"));
			maxId = Math.max(maxId, currentId);
		}

		return maxId + 1;
	}

	@Override
	public void addBook(IBook book) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCP/resources/books.xml");

			Element bookElement = doc.createElement("book");
			bookElement.setAttribute("id", String.valueOf(getNextBookId(doc)));

			Element titleElement = doc.createElement("title");
			titleElement.appendChild(doc.createTextNode(book.getTitle()));

			Element authorElement = doc.createElement("author");
			authorElement.appendChild(doc.createTextNode(book.getAuthor()));

			Element publicationYearElement = doc.createElement("publicationYear");
			publicationYearElement.appendChild(doc.createTextNode(String.valueOf(book.getPublicationYear())));

			Element ISBNElement = doc.createElement("ISBN");
			ISBNElement.appendChild(doc.createTextNode(String.valueOf(book.getISBN())));

			Element statusElement = doc.createElement("status");
			statusElement.appendChild(doc.createTextNode(String.valueOf(book.isStatus())));

			bookElement.appendChild(titleElement);
			bookElement.appendChild(authorElement);
			bookElement.appendChild(publicationYearElement);
			bookElement.appendChild(ISBNElement);
			bookElement.appendChild(statusElement);

			doc.getDocumentElement().appendChild(bookElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult("C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCP/resources/books.xml");
			transformer.transform(source, result);

			System.out.println("Added a new book");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteBook(final int id) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCP/resources/books.xml");

			NodeList bookList = doc.getElementsByTagName("book");

			for (int i = 0; i < bookList.getLength(); i++) {
				Element bookElement = (Element) bookList.item(i);
				int currentBookId;

				currentBookId = Integer.parseInt(bookElement.getAttribute("id"));

				if (currentBookId == id) {
					bookElement.getParentNode().removeChild(bookElement);
					break;
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult("C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCP/resources/books.xml");
			transformer.transform(source, result);

			System.out.println("Deleted a new book");

		} catch (NumberFormatException e) {

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void borrowBook(String title) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCP/resources/books.xml");
			NodeList bookList = doc.getElementsByTagName("book");
			for (int i = 0; bookList.getLength() > i; i++) {
				Element bookElement = (Element) bookList.item(i);
				Element titleElement = (Element) bookElement.getElementsByTagName("title").item(0);

				if (titleElement.getTextContent().equals(title)) {
					Element statusElement = (Element) bookElement.getElementsByTagName("status").item(0);
					statusElement.setTextContent("false");
					break;
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult("C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCP/resources/books.xml");
			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
