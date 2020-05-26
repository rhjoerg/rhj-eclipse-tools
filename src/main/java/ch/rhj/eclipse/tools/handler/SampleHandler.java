package ch.rhj.eclipse.tools.handler;

import static ch.rhj.eclipse.tools.EclipseToolsConstants.CONSOLE_NAME;

import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import ch.rhj.eclipse.ui.console.EclipseConsole;

public class SampleHandler extends AbstractHandler
{
	private final EclipseConsole console = new EclipseConsole(CONSOLE_NAME);

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{

		console.show();
		console.println("Hello, world!");

		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint[] extensionPoints = extensionRegistry.getExtensionPoints();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint("org.eclipse.ui.menus");
		IExtension[] extensions = extensionPoint.getExtensions();

		console.println("--- " + extensionPoints.length + " known extensionPoints ---");

		Stream.of(extensionPoints).map(ep -> ep.getUniqueIdentifier()).sorted().forEach(id -> console.println(id));

		console.println("--- " + extensions.length + " menu extensions ---");

		for (IExtension extension : extensions)
		{

			IContributor contributor = extension.getContributor();
			IConfigurationElement[] configurationElements = extension.getConfigurationElements();

			System.out.println(contributor.getName());

			Stream.of(configurationElements) //
					.filter(ce -> "menuContribution".equals(ce.getName())) //
					.forEach(ce ->
					{
						System.out.println("- locationURI: " + ce.getAttribute("locationURI"));
					});
		}

		return null;
	}
}
