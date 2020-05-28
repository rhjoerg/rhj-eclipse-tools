package ch.rhj.eclipse.tools.configure;

import static ch.rhj.eclipse.tools.EclipseToolsConstants.CONSOLE_NAME;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.framework.Bundle;

import ch.rhj.eclipse.core.io.EclipseFolders;
import ch.rhj.eclipse.core.io.EclipseResources;
import ch.rhj.eclipse.core.io.EclipseWrite;
import ch.rhj.eclipse.tools.EclipseToolsPlugin;
import ch.rhj.eclipse.ui.console.EclipseConsole;

public class ConfigureHandler extends AbstractHandler
{
	private final static String GITIGNORE_TEMPLATE = ".gitignore.template";
	private final static String GITIGNORE = ".gitignore";

	private final static String CLASSPATH = ".classpath";

	private final static String POM_XML = "pom.xml";
	private final static String SETTINGS_XML = "settings.xml";

	private final static String CORE_PREFS = "org.eclipse.core.resources.prefs";
	private final static String JAVA_PREFS = "org.eclipse.jdt.core.prefs";
	private final static String MAVEN_PREFS = "org.eclipse.m2e.core.prefs";

	private final EclipseConsole console = new EclipseConsole(CONSOLE_NAME);

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		IProject project = getProject(event);

		if (confirmProject(event, project))
		{
			console.show();

			addGitIgnore(project);
			addSourceFolderTree(project);
			addPomXml(project);
			addSettingsXml(project);
			addSettings(project);
			addClasspath(project);
			addJavaNature(project);
			addMavenNature(project);

			console.println("configuration complete");
		}

		return null;
	}

	private void addGitIgnore(IProject project) throws ExecutionException
	{
		console.println("adding .gitignore");

		exportTemplate(project.getFile(GITIGNORE), GITIGNORE_TEMPLATE, s -> s);
	}

	private void addSourceFolderTree(IProject project) throws ExecutionException
	{
		console.println("adding source folder tree");

		try
		{
			EclipseFolders.createDirectories(project, "src", "main", "java");
			EclipseFolders.createDirectories(project, "src", "main", "resources");
			EclipseFolders.createDirectories(project, "src", "test", "java");
			EclipseFolders.createDirectories(project, "src", "test", "resources");
		}
		catch (CoreException e)
		{
			throw new ExecutionException("", e);
		}
	}

	private void addPomXml(IProject project) throws ExecutionException
	{
		console.println("adding pom.xml");

		Function<String, String> mapper = s -> s.replace("ARTIFACT_ID", project.getName());

		exportTemplate(project.getFile(POM_XML), POM_XML, mapper);
	}

	private void addSettingsXml(IProject project) throws ExecutionException
	{
		console.println("adding settings.xml");

		exportTemplate(project.getFile(SETTINGS_XML), SETTINGS_XML, s -> s);
	}

	private void addSettings(IProject project) throws ExecutionException
	{
		console.println("adding settings");

		try
		{
			IFolder folder = EclipseFolders.createDirectories(project, ".settings");
			Function<String, String> mapper = s -> s;

			exportTemplate(folder.getFile(CORE_PREFS), ".settings/" + CORE_PREFS, mapper);
			exportTemplate(folder.getFile(JAVA_PREFS), ".settings/" + JAVA_PREFS, mapper);
			exportTemplate(folder.getFile(MAVEN_PREFS), ".settings/" + MAVEN_PREFS, mapper);
		}
		catch (CoreException e)
		{
			throw new ExecutionException("", e);
		}
	}

	private void addClasspath(IProject project) throws ExecutionException
	{
		console.println("adding .classpath");

		exportTemplate(project.getFile(CLASSPATH), CLASSPATH, s -> s);
	}

	private void addJavaNature(IProject project) throws ExecutionException
	{
		console.println("adding Java nature");

		addNature(project, JavaCore.NATURE_ID);
	}

	private void addMavenNature(IProject project) throws ExecutionException
	{
		console.println("adding Maven nature");

		addNature(project, "org.eclipse.m2e.core.maven2Nature");
	}

	private void addNature(IProject project, String nature) throws ExecutionException
	{
		try
		{
			if (!project.hasNature(nature))
			{
				IProjectDescription description = project.getDescription();
				List<String> natureIds = Arrays.asList(description.getNatureIds());

				natureIds = new ArrayList<>(natureIds);
				natureIds.add(nature);
				description.setNatureIds(natureIds.toArray(String[]::new));
				project.setDescription(description, null);
			}
		}
		catch (CoreException e)
		{
			throw new ExecutionException("", e);
		}
	}

	private void exportTemplate(IFile target, String name, Function<String, String> mapper) throws ExecutionException
	{
		try
		{
			String caller = EclipseToolsPlugin.PLUGIN_ID;
			Bundle bundle = EclipseToolsPlugin.getDefault().getBundle();
			String content = EclipseResources.string(caller, bundle, "templates/" + name, UTF_8);

			content = mapper.apply(content);
			EclipseWrite.write(target, content, UTF_8, false);
		}
		catch (CoreException e)
		{
			throw new ExecutionException("", e);
		}
	}

	private boolean confirmProject(ExecutionEvent event, IProject project)
	{
		if (project == null)
		{
			return false;
		}

		Shell shell = HandlerUtil.getActiveShell(event);
		String message = String.format("Configure '%1$s'?", project.getName());

		return MessageDialog.openConfirm(shell, "Configure Project", message);
	}

	private IProject getProject(ExecutionEvent event)
	{
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);

		if (selection == null)
		{
			return null;
		}

		for (Object object : selection)
		{
			if (object instanceof IProject)
			{
				return IProject.class.cast(object);
			}
		}

		return null;
	}
}
