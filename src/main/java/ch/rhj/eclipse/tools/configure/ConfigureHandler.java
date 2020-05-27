package ch.rhj.eclipse.tools.configure;

import static ch.rhj.eclipse.tools.EclipseToolsConstants.CONSOLE_NAME;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import ch.rhj.eclipse.ui.console.EclipseConsole;

public class ConfigureHandler extends AbstractHandler
{
	private final EclipseConsole console = new EclipseConsole(CONSOLE_NAME);

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		console.println("configuration complete");

		return null;
	}
}
