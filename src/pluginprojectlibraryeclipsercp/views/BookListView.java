package pluginprojectlibraryeclipsercp.views;

import java.util.List;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import pluginprojectlibraryeclipsercp.controllers.Controller;
import pluginprojectlibraryeclipsercp.model.Book;

public class BookListView extends ViewPart implements BooksObserver {
	public static final String ID = "Plug-inProjectLibraryEclipseRCP.booklistview";
	private ListViewer listViewer;
	private Controller controller = new Controller();

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());

		List<Book> books = controller.getRepository().getBooks();

		listViewer = new ListViewer(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.BORDER);
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof Book) {
					Book book = (Book) element;
					return String.format(" %-20s %-25s %-20s %-20s %-10s", book.getTitle(), book.getAuthor(),
							book.getPublicationYear(), book.getISBN(), book.isStatus() ? "AVAILABLE" : "BORROWED");
				}
				return super.getText(element);
			}
		});
		listViewer.setInput(books);
		controller.addObserver(this);
		this.controller.getRepository().getBooks().forEach(System.out::println);
	}

	@Override
	public void updateBooks() {
		Display.getDefault().asyncExec(() -> {
			if (!listViewer.getControl().isDisposed()) {
				List<Book> books = controller.getRepository().getBooks();
				listViewer.setInput(books);
			}
		});
	}

	@Override
	public void setFocus() {
	}

}
