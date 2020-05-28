package ch.rhj.eclipse.core.io;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

public interface EclipseFolders
{
	public static IFolder createDirectories(IProject project, String name, String... more) throws CoreException
	{
		IFolder folder = createFolderIfNotExists(project.getFolder(name));

		for (String s : more)
		{
			folder = createFolderIfNotExists(folder.getFolder(s));
		}

		return folder;
	}

	private static IFolder createFolderIfNotExists(IFolder folder) throws CoreException
	{
		if (!folder.exists())
		{
			folder.create(false, true, null);
		}

		return folder;
	}
}
