package pluginprojectlibraryeclipsercp.api;

import java.util.List;


public interface IBookAPI {
	List<IBook> getBooks();
	
	void addBook(IBook book);
	
	void deleteBook(final int id);

	void borrowBook(String title);
}
