package pluginprojectlibraryeclipsercp.model;

import pluginprojectlibraryeclipsercp.api.IBook;

public class Book implements IBook {

	private static int counter = 0;

	private final int id;
	private final String title;
	private final String author;
	private final int publicationYear;
	private final int ISBN;
	private boolean status;

	public Book(String title, String author, int publicationYear, int ISBN, boolean status) {
		id = ++counter;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
		this.ISBN = ISBN;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public int getISBN() {
		return ISBN;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("%-1s %-12s %-20s %-10s %-10s %-1s", id, title, author, publicationYear, ISBN,
				status ? "AVAILABLE" : "BORROWED");
	}

}
