package pluginprojectlibraryeclipsercp.api;

public interface IBook {

	static int counter = 0;

	public int getId();

	public String getTitle();

	public String getAuthor();

	public int getPublicationYear();

	public int getISBN();

	public boolean isStatus();

	public void setStatus(boolean status);

	@Override
	public String toString();

}
