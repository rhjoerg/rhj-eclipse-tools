package ch.rhj.eclipse.tools;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import ch.rhj.eclipse.tools.console.EclipseToolsConsole;

/**
 * The activator class controls the plug-in life cycle
 */
public class EclipseToolsPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ch.rhj.eclipse.tools"; //$NON-NLS-1$

	// The shared instance
	private static EclipseToolsPlugin plugin;

	private EclipseToolsConsole console;

	/**
	 * The constructor
	 */
	public EclipseToolsPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {

		super.start(context);

		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {

		plugin = null;

		super.stop(context);
	}

	public synchronized EclipseToolsConsole getConsole() {

		if (console == null) {

			console = new EclipseToolsConsole();
		}

		return console;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EclipseToolsPlugin getDefault() {

		return plugin;
	}
}
