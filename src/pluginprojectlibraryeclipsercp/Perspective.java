package pluginprojectlibraryeclipsercp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		layout.addStandaloneView("Plug-inLibraryProjectEclipseRCP.view", true, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
		layout.setFixed(true);
		
	}

}
