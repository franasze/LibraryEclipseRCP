package pluginprojectlibraryeclipsercp.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pluginprojectlibraryeclipsercp.model.Book;
import pluginprojectlibraryeclipsercp.api.IBookAPI;

public class BookDBImplementation2 implements IBookAPI {
	private final List<Book> books = new ArrayList<>();
	
	public BookDBImplementation2() {
		this.books.add(new Book("Pan Tadeusz", "Adam Mickiewicz", 1834, 100, true));
		this.books.add(new Book("Lalka", "Boleslaw Prus", 1889, 101, true));
		this.books.add(new Book("Kordian", "Juliusz Slowacki", 1834, 102, true));
	}

	@Override
	public List<Book> getBooks() {
		return this.books;
	}

	@Override
	public void addBook(Book book) {
		this.books.add(new Book(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getISBN(),
				book.isStatus()));
	}
	

	@Override
	public void deleteBook(final int id) {
		this.books.removeIf(b -> b.getId() == id);
		System.out.println("Book has been deleted");
	}

	@Override
	public void rentBook(String title) {
		Optional<Book> book = this.books.stream().filter(b -> b.getTitle().equals(title)).findFirst();
		if (book.isEmpty()) {
			System.out.println("Book not found");
		}
		if (book.get().isStatus()) {
			System.out.println("Book is already borrowed");
		}
		book.get().setStatus(false);
	}
}
