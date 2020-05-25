package ch.rhj.eclipse.tools.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import ch.rhj.eclipse.tools.console.ConsoleSupport;

public class SampleHandler extends AbstractHandler implements ConsoleSupport {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		showConsole();
		println("Hello, world!");

		return null;
	}
}
