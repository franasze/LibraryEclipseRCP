package pluginprojectlibraryeclipsercp.views;

import java.util.List;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import pluginprojectlibraryeclipsercp.controllers.Controller;

import pluginprojectlibraryeclipsercp.api.IBook;


public class BookListView extends ViewPart implements BooksObserver {
	public static final String ID = "Plug-inProjectLibraryEclipseRCPP.booklistview";

	private Controller controller = Controller.getInstance();
	private TableViewer tableViewer;
	private Composite buttonPanel;
	private Composite tableComposite;
	private Composite mainComposite;

	@Override
	public void createPartControl(Composite parent) {
		mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, false));
		mainComposite.setBackground(new Color(215,226,252) );
		tableComposite = new Composite(mainComposite, SWT.NONE);
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tableComposite.setLayout(new GridLayout(1, false));

		mainComposite.layout();
		try {
			List<IBook> books = controller.getRepository().getBooks();

			tableViewer = new TableViewer(tableComposite,
					SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
			tableViewer.setContentProvider(ArrayContentProvider.getInstance());
	
			tableViewer.getTable().setHeaderVisible(true);
			tableViewer.getTable().setLinesVisible(true);
			tableViewer.setLabelProvider(new LabelProvider());
//			{

//				@Override
//				public String getText(Object element) {
//					if (element instanceof Book) {
//						Book book = (Book) element;
//						return String.format(" %-20s %-20s %-17s %-17s %-10s", book.getTitle(), book.getAuthor(),
//								book.getPublicationYear(), book.getISBN(), book.isStatus() ? "AVAILABLE" : "BORROWED");
//					}
//
//					return super.getText(element);
//				}
//			});

//			tableViewer.setLabelProvider(new BookLabelProvider());
			tableViewer.addSelectionChangedListener(event -> {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					IBook selectedBook = (IBook) selection.getFirstElement();
					System.out.println("Selected Book: " + selectedBook.getTitle());
				}
			});

			tableViewer.setInput(books);
			tableViewer.refresh();

			controller.addObserver(this);

			this.controller.getRepository().getBooks().forEach(System.out::println);
		} catch (NullPointerException e) {
			System.out.println("Object is NULL!!!  " + e);
		}
		buttonPanel = new Composite(mainComposite, SWT.FILL);
		GridData buttonLayoutData = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		buttonPanel.setLayoutData(buttonLayoutData);
		buttonPanel.setLayout(new GridLayout(2, false));

		Button deleteButton = new Button(buttonPanel, SWT.PUSH);
		Button borrowButton = new Button(buttonPanel, SWT.PUSH);
		deleteButton.setText("Delete a book");
		borrowButton.setText("Borrow a book");
		buttonLayoutData.widthHint = 400;
		buttonLayoutData.heightHint = 70;
		deleteButton.setLayoutData(buttonLayoutData);
		borrowButton.setLayoutData(buttonLayoutData);

		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					buttonPanel.setVisible(true);
					mainComposite.layout();

					IBook selectedBook = getSelectedBook();
					if (selectedBook != null) {
						controller.getRepository().deleteBook(selectedBook.getId());
						controller.notifyObservers();
					}
				} catch (NullPointerException except) {
					System.out.println("Object is NULL!!!  " + except);
				}
			}
		});

		borrowButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					buttonPanel.setVisible(true);
					mainComposite.layout();

					IBook selectedBook = getSelectedBook();
					if (selectedBook != null) {
						controller.getRepository().borrowBook(selectedBook.getTitle());
						controller.notifyObservers();
					}
				} catch (NullPointerException except) {
					System.out.println("Object is NULL!!!  " + except);
				}
			}
		});

	}

	private IBook getSelectedBook() {
		IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
		if (!selection.isEmpty()) {
			return (IBook) selection.getFirstElement();
		}
		return null;
	}

	@Override
	public void updateBooks() {
		Display.getDefault().asyncExec(() -> {
			if (!tableViewer.getControl().isDisposed()) {
				List<IBook> books = controller.getRepository().getBooks();
				tableViewer.remove(books);
				tableViewer.setInput(books);

			}
		});
	}

	@Override
	public void setFocus() {
		try {
			tableViewer.getControl().setFocus();
		} catch (NullPointerException e) {
			System.out.println("Object is NULL!!! " + e);
		}
	}

}
