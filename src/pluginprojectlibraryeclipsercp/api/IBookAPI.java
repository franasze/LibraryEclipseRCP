package pluginprojectlibraryeclipsercp.api;

import java.util.List;

import pluginprojectlibraryeclipsercp.model.Book;


public interface IBookAPI {
	List<Book> getBooks();
	
	void addBook(Book book);
	
	void deleteBook(final int id);

	void rentBook(String title);
}
