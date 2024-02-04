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
	private static Controller instance;
	private IBookAPI bookDB;
	private List<BooksObserver> observers = new ArrayList<>();

	private Controller() {
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
					"C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCPP/resources/config.properties");
			prop.load(input);
			String BookImplementation = prop.getProperty("bookdb.class");
			Class<?> classChooser = Class.forName(BookImplementation);
			bookDB = (IBookAPI) classChooser.newInstance();

		} catch (ClassNotFoundException e) {
			System.out.println("Class not found! " + e);
		} catch (NullPointerException e) {
			System.out.println("Object is NULL! " + e);
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public IBookAPI getRepository() {
		return bookDB;
	}

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

}
