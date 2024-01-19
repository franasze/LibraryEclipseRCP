package pluginprojectlibraryeclipsercp.jobs;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import pluginprojectlibraryeclipsercp.api.IBookAPI;
import pluginprojectlibraryeclipsercp.controllers.Controller;
import pluginprojectlibraryeclipsercp.model.Book;

public class CheckBookStatusJob extends Job {
	private Controller controller = new Controller();
	private boolean[] StatusTabel;

	public CheckBookStatusJob() {
		super("Check Book Status Job");
	}

	private boolean statusChanged() {
		IBookAPI repository = controller.getRepository();
		List<Book> books = repository.getBooks();

		if (StatusTabel == null) {
			StatusTabel = new boolean[books.size()];
			Arrays.fill(StatusTabel, true);
		}
		boolean statusChanged = false;

		for (int i = 0; i < books.size(); i++) {
			Book book = books.get(i);
			boolean obecnyStatus = book.isStatus();
			boolean poprzedniStatus = StatusTabel[i];

			if (obecnyStatus != poprzedniStatus) {
				statusChanged = true;
				StatusTabel[i] = obecnyStatus;
			}
		}

		return statusChanged;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		if (statusChanged()) {
			Display.getDefault().asyncExec(() -> {
				if (!Display.getDefault().isDisposed()) {
					wyswietlAlert("Status has changed!");
				}
			});
		}

		return Status.OK_STATUS;
	}

	private void wyswietlAlert(String message) {
		MessageBox messageBox = new MessageBox(Display.getDefault().getActiveShell());
		messageBox.setMessage(message);
		messageBox.open();
	}

}