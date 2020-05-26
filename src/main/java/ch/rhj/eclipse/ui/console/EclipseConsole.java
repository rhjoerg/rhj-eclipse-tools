package ch.rhj.eclipse.ui.console;

import static org.eclipse.ui.console.IConsoleConstants.ID_CONSOLE_VIEW;

import java.util.function.Consumer;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class EclipseConsole
{
	public final String name;

	public EclipseConsole(String name)
	{
		this.name = name;
	}

	public void show()
	{
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try
		{
			IConsoleView.class.cast(page.showView(ID_CONSOLE_VIEW)).display(console());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void print(String message)
	{
		consume(s -> s.print(message));
	}

	public void println(String message)
	{
		consume(s -> s.println(message));
	}

	public void println()
	{
		consume(s -> s.println());
	}

	private void consume(Consumer<MessageConsoleStream> consumer)
	{
		try (MessageConsoleStream stream = console().newMessageStream())
		{
			consumer.accept(stream);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private MessageConsole console()
	{
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager manager = plugin.getConsoleManager();

		for (IConsole console : manager.getConsoles())
		{
			if (name.equals(console.getName()))
			{
				return MessageConsole.class.cast(console);
			}
		}

		MessageConsole console = new MessageConsole(name, null);

		manager.addConsoles(new IConsole[] { console });

		return console;
	}
}
