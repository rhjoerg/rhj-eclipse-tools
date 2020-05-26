package ch.rhj.eclipse.tools;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EclipseToolsPlugin extends AbstractUIPlugin
{
	// The shared instance
	private static EclipseToolsPlugin plugin;

	/**
	 * The constructor
	 */
	public EclipseToolsPlugin()
	{
	}

	@Override
	public void start(BundleContext context) throws Exception
	{
		super.start(context);

		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;

		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EclipseToolsPlugin getDefault()
	{
		return plugin;
	}
}
