package pluginprojectlibraryeclipsercp.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pluginprojectlibraryeclipsercp.model.Book;
import pluginprojectlibraryeclipsercp.api.IBook;
import pluginprojectlibraryeclipsercp.api.IBookAPI;

public class BookDBImplementationList implements IBookAPI {
	private final List<IBook> books = new ArrayList<>();

	public BookDBImplementationList() {
		this.books.add(new Book("Pan Tadeusz", "Adam Mickiewicz", 1834, 100, true));
		this.books.add(new Book("Lalka", "Boleslaw Prus", 1889, 101, true));
		this.books.add(new Book("Kordian", "Juliusz Slowacki", 1834, 102, true));
	}

	@Override
	public List<IBook> getBooks() {
		return this.books;
	}

	@Override
	public void addBook(IBook book) {
		this.books.add(new Book(book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getISBN(),
				book.isStatus()));

	}

	@Override
	public void deleteBook(final int id) {
		this.books.removeIf(b -> b.getId() == id);
		System.out.println("Book has been deleted");
	}

	@Override
	public void borrowBook(String title) {

		Optional<IBook> book = this.books.stream().filter(b -> b.getTitle().equals(title)).findFirst();

		if (book.isPresent()) {
			IBook foundBook = book.get();
			if (foundBook.isStatus()) {
				foundBook.setStatus(false);
				System.out.println("Book borrowed successfully");
			} else {
				System.out.println("Book is already borrowed");
			}
		} else {
			System.out.println("Book not found");
		}
	}
}
