package ch.rhj.eclipse.tools.console;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class EclipseToolsConsole {

	public final static String NAME = "RHJ Eclipse Tools";

	private MessageConsole console;
	private MessageConsoleStream stream;

	private synchronized MessageConsole getConsole() {

		if (console == null) {

			ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
			IConsoleManager consoleManager = consolePlugin.getConsoleManager();

			console = new MessageConsole(NAME, null);
			consoleManager.addConsoles(new IConsole[] { console });
		}

		return console;
	}

	private synchronized MessageConsoleStream getStream() {

		return stream == null ? (stream = getConsole().newMessageStream()) : stream;
	}

	public synchronized void show() {

		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();

		try {

			IConsoleView view = (IConsoleView) page.showView(IConsoleConstants.ID_CONSOLE_VIEW);

			view.display(getConsole());

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void print(String message) {

		getStream().print(message);
	}

	public void println() {

		getStream().println();
	}

	public void println(String message) {

		getStream().println(message);
	}
}
