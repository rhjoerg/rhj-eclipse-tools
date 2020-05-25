package ch.rhj.eclipse.tools;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import ch.rhj.eclipse.tools.console.ConsoleSupport;

public class ConfigureAction implements IViewActionDelegate, ConsoleSupport {

	@Override
	public void run(IAction action) {

		showConsole();
		println("Configuring...");
		println("Done.");
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// intentionally empty
	}

	@Override
	public void init(IViewPart view) {
		// intentionally empty
	}

}
