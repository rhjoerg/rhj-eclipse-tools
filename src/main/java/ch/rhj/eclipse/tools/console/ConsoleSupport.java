package ch.rhj.eclipse.tools.console;

import ch.rhj.eclipse.tools.EclipseToolsPlugin;

public interface ConsoleSupport {

	default EclipseToolsConsole getConsole() {

		return EclipseToolsPlugin.getDefault().getConsole();
	}

	default void showConsole() {

		getConsole().show();
	}

	default void print(String message) {

		getConsole().print(message);
	}

	default void println() {

		getConsole().println();
	}

	default void println(String message) {

		getConsole().println(message);
	}
}
