package pluginprojectlibraryeclipsercp.views;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;

import pluginprojectlibraryeclipsercp.controllers.Controller;
import pluginprojectlibraryeclipsercp.jobs.CheckBookStatusJob;
import pluginprojectlibraryeclipsercp.model.Book;

public class View extends ViewPart {
	public static final String ID = "Plug-inProjectLibraryEclipseRCPP.view";
	private StackLayout stackLayout;
	private Composite stackComposite;

	private Composite mainPanel;
	private Composite formPanel;
	private Composite deletePanel;
	private Composite borrowPanel;
	Font font;
	private Controller controller = Controller.getInstance();

	@Override
	public void createPartControl(Composite parent) {
		try {
			CheckBookStatusJob job = new CheckBookStatusJob("checkStatus",
					"C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCPP/resources/books.xml");
			job.schedule();
		} catch (NullPointerException except) {
			System.out.println("Object is NULL!!!  " + except);
		}

		getSite().getShell().setMinimumSize(800, 600);
		stackComposite = new Composite(parent, SWT.NONE);
		stackLayout = new StackLayout();
		stackComposite.setLayout(stackLayout);

		mainPanel = createMainPanel(stackComposite);

		formPanel = createAddBookPanel(stackComposite);

		deletePanel = createDeleteBookPanel(stackComposite);

		borrowPanel = createBorrowBookPanel(stackComposite);

		stackLayout.topControl = mainPanel;
		stackComposite.layout();
	}

	private Composite createMainPanel(Composite parent) {
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayout(new GridLayout(1, false));
		
		mainPanel.setBackground(new Color(215,226,252) );
		Label bigLabel = new Label(mainPanel, SWT.NONE);
		bigLabel.setText("Library in Eclipse RCP");
		font = new Font(Display.getCurrent(), "Forte", 44, SWT.BOLD);
		bigLabel.setFont(font);
		bigLabel.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, true));

		GridData buttonLayoutData = new GridData(SWT.CENTER, SWT.TOP, true, true);
		mainPanel.setLayoutData(buttonLayoutData);

		Button button = new Button(mainPanel, SWT.PUSH);
		Button button2 = new Button(mainPanel, SWT.PUSH);
		Button button3 = new Button(mainPanel, SWT.PUSH);
		Button button4 = new Button(mainPanel, SWT.PUSH);
		button.setText("Show book list");
		button2.setText("Add a book");
		button3.setText("Delete a book");
		button4.setText("Borrow a book");

		buttonLayoutData.widthHint = 150;
		buttonLayoutData.heightHint = 60;
		button.setLayoutData(buttonLayoutData);
		button2.setLayoutData(buttonLayoutData);
		button3.setLayoutData(buttonLayoutData);
		button4.setLayoutData(buttonLayoutData);

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleButtonClicked();
			}
		});
		button2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = formPanel;
				stackComposite.layout();
			}
		});
		button3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = deletePanel;
				stackComposite.layout();
			}
		});
		button4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stackLayout.topControl = borrowPanel;
				stackComposite.layout();
			}
		});
		return mainPanel;
	}

	private Composite createAddBookPanel(Composite parent) {

		Composite formPanel = new Composite(parent, SWT.NONE);
		formPanel.setLayout(new FillLayout(SWT.VERTICAL));
		Text title;
		Text author;
		Text year;
		Text ISBN;

		// title
		Label titleLabel = new Label(formPanel, SWT.NONE);
		titleLabel.setText("Title:");
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 5);
		titleLabel.setLayoutData(formData);

		title = new Text(formPanel, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(titleLabel, 5);
		formData.width = 200;
		title.setLayoutData(formData);

		// author
		Label authorLabel = new Label(formPanel, SWT.NONE);
		authorLabel.setText("Author:");
		formData = new FormData();
		formData.left = new FormAttachment(0, 5);
		formData.top = new FormAttachment(authorLabel, 10);
		authorLabel.setLayoutData(formData);

		author = new Text(formPanel, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(authorLabel, 5);
		formData.width = 50;
		author.setLayoutData(formData);

		// Year
		Label yearLabel = new Label(formPanel, SWT.NONE);
		yearLabel.setText("Publication year:");
		formData = new FormData();
		formData.left = new FormAttachment(0, 5);
		formData.top = new FormAttachment(yearLabel, 10);
		yearLabel.setLayoutData(formData);

		year = new Text(formPanel, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(yearLabel, 5);
		formData.width = 50;
		year.setLayoutData(formData);

		// ISBN
		Label ISBNLabel = new Label(formPanel, SWT.NONE);
		ISBNLabel.setText("ISBN:");
		formData = new FormData();
		formData.left = new FormAttachment(0, 5);
		formData.top = new FormAttachment(ISBNLabel, 10);
		ISBNLabel.setLayoutData(formData);

		ISBN = new Text(formPanel, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(ISBNLabel, 5);
		formData.width = 50;
		ISBN.setLayoutData(formData);

		Button submitButton = new Button(formPanel, SWT.PUSH);
		submitButton.setText("Submit");

		submitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					stackLayout.topControl = mainPanel;
					formPanel.setVisible(false);
					stackComposite.layout();

					String titleValue = title.getText();
					String authorValue = author.getText();
					int yearValue = Integer.parseInt(year.getText());
					int ISBNValue = Integer.parseInt(ISBN.getText());
					Book newBook = new Book(titleValue, authorValue, yearValue, ISBNValue, true);
					System.out.println("New Book: \n" + newBook);
					controller.getRepository().addBook(newBook);
					controller.notifyObservers();
				} catch (NumberFormatException except) {
					System.out.println("You entered incorrect values " + except);
				} catch (NullPointerException except) {
					System.out.println("Object is NULL!!!  " + except);
				}
			}
		});

		return formPanel;

	}

	private Composite createDeleteBookPanel(Composite parent) {

		Composite deletePanel = new Composite(parent, SWT.NONE);
		deletePanel.setLayout(new FillLayout(SWT.VERTICAL));
		Text id;

		Label idLabel = new Label(deletePanel, SWT.NONE);
		idLabel.setText("ID:");
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 5);
		idLabel.setLayoutData(formData);

		id = new Text(deletePanel, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(idLabel, 5);
		formData.width = 200;
		id.setLayoutData(formData);

		Button submitButton = new Button(deletePanel, SWT.PUSH);
		submitButton.setText("Submit");
		submitButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					stackLayout.topControl = mainPanel;
					deletePanel.setVisible(false);
					stackComposite.layout();

					int ID = Integer.parseInt(id.getText());
					controller.getRepository().deleteBook(ID);
					controller.notifyObservers();

				} catch (NumberFormatException except) {
					System.out.println("You have to enter correct ID number " + except);
				} catch (NullPointerException except) {
					System.out.println("Object is NULL!!!  " + except);
				}
			}

		});

		return deletePanel;
	}

	private Composite createBorrowBookPanel(Composite parent) {
		Composite borrowPanel = new Composite(parent, SWT.NONE);
		borrowPanel.setLayout(new FillLayout(SWT.VERTICAL));
		Text title;

		Label titleLabel = new Label(borrowPanel, SWT.NONE);
		titleLabel.setText("Title:");
		FormData formData = new FormData();
		formData.left = new FormAttachment(0, 5);
		titleLabel.setLayoutData(formData);

		title = new Text(borrowPanel, SWT.BORDER);
		formData = new FormData();
		formData.left = new FormAttachment(titleLabel, 5);
		formData.width = 200;
		title.setLayoutData(formData);

		Button submitButton = new Button(borrowPanel, SWT.PUSH);
		submitButton.setText("Submit");
		submitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					stackLayout.topControl = mainPanel;
					borrowPanel.setVisible(false);
					stackComposite.layout();

					String titleValue = title.getText();
					controller.getRepository().borrowBook(titleValue);
					controller.notifyObservers();

				} catch (NumberFormatException except) {
					System.out.println("You have to enter correct ID number " + except);
				} catch (NullPointerException except) {
					System.out.println("Object is NULL!!!  " + except);
				}
			}
		});

		return borrowPanel;
	}

	private void handleButtonClicked() {
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = commandService.getCommand("com.example.ViewCommand");
		try {
			command.executeWithChecks(new ExecutionEvent());
		} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void setFocus() {

	}

	public void dispose() {
		if (font != null && !font.isDisposed()) {
			font.dispose();
		}
		super.dispose();
	}
}