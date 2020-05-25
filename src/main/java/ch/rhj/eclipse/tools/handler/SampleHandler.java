package ch.rhj.eclipse.tools.handler;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;

public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		MessageDialog.openInformation(window.getShell(), "RHJ Eclipse Tools", "Hello from RHJ Eclipse Tools");

		ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
		IConsoleManager consoleManager = consolePlugin.getConsoleManager();
		IConsole[] consoles = consoleManager.getConsoles();
		IConsole console = null;

		for (IConsole candidate : consoles) {

			if ("RHJ Console".equals(candidate.getName())) {

				console = candidate;
				break;
			}
		}

		if (console == null) {

			console = new MessageConsole("RHJ Console", null);
			consoleManager.addConsoles(new IConsole[] { console });
		}

		try {

			IWorkbenchPage workbenchPage = window.getActivePage();
			IConsoleView consoleView = (IConsoleView) workbenchPage.showView(IConsoleConstants.ID_CONSOLE_VIEW);

			consoleView.display(console);

		} catch (PartInitException e) {
			throw new ExecutionException("", e);
		}

		MessageConsole messageConsole = (MessageConsole) console;

		try (MessageConsoleStream messageConsoleStream = messageConsole.newMessageStream()) {

			messageConsoleStream.println("Hello from RHJ Eclipse Tools");

		} catch (IOException e) {
			throw new ExecutionException("", e);
		}

		return null;
	}
}
