package pluginprojectlibraryeclipsercp.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import pluginprojectlibraryeclipsercp.api.IBookAPI;
import pluginprojectlibraryeclipsercp.views.BooksObserver;

public class Controller {
	private IBookAPI bookDB;
	private List<BooksObserver> observers = new ArrayList<>();

	public Controller() {
		initializeRepository();
	}

	public void addObserver(BooksObserver observer) {
		observers.add(observer);
	}

	public void notifyObservers() {
		for (BooksObserver observer : observers) {
			observer.updateBooks();
		}
	}

	private void initializeRepository() {
		try {
			Properties prop = new Properties();
			InputStream input = new FileInputStream(
					"C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCP/resources/config.properties");
			prop.load(input);
			String BookImplementation = prop.getProperty("bookdb.class");
			Class<?> classChooser = Class.forName(BookImplementation);
			bookDB = (IBookAPI) classChooser.newInstance();

		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public IBookAPI getRepository() {
		return bookDB;
	}

}
