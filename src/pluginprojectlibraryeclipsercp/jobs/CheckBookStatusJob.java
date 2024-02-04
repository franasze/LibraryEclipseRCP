package pluginprojectlibraryeclipsercp.jobs;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class CheckBookStatusJob extends Job {

	public static final String JOB_NAME = "checkStatus";
	private String filePath;

	public CheckBookStatusJob(String name, String filePath) {
		super(name);
		this.filePath = filePath;
		setSystem(true); // zadanie systemowe, wiekszy priorytet
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path directory = Paths.get(filePath).getParent();
			directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

			Map<String, String> previousStatusMap = new HashMap<>();

			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
						Path modifiedFile = (Path) event.context();
						if (modifiedFile.endsWith("books.xml")) {

							DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
							DocumentBuilder builder = factory.newDocumentBuilder();
							Document doc = builder.parse(
									"C:/Users/areka/eclipse-workspace/Plug-inProjectLibraryEclipseRCPP/resources/books.xml");
							NodeList bookList = doc.getElementsByTagName("book");
							for (int i = 0; bookList.getLength() > i; i++) {
								Element bookElement = (Element) bookList.item(i);
								String bookId = bookElement.getAttribute("id");

								String status = bookElement.getElementsByTagName("status").item(0).getTextContent();
								System.out.println("Wartość zmiennej status: " + status + " : " + bookId);
//								System.out.println(previousStatusMap);
								if (!previousStatusMap.containsKey(bookId)) {
									String previousStatus = previousStatusMap.get(bookId);

									if (!status.equals(previousStatus)) {

										System.out.println("//Statusy różne- kolejny if przeszedl");

										Display.getDefault().asyncExec(() -> {
											Shell activeShell = Display.getDefault().getActiveShell();
											if (activeShell != null) {

												System.out.println("//activeShell nie jest nullem w koncu");

												MessageBox messageBox = new MessageBox(activeShell);
												messageBox.setMessage("Zmiana w pliku XML!");
												messageBox.open();
											}
										});
									}
								}
								previousStatusMap.put(bookId, status);
							}
						}
					}
				}
				key.reset();
			}
		} catch (SAXParseException e) {
			System.out.println("Your XML file is incorrect! " + e);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (SWTException e) {
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	}
}