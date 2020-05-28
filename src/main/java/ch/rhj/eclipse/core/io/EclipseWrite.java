package ch.rhj.eclipse.core.io;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public interface EclipseWrite
{
	public static void write(IFile file, byte[] content, boolean replace) throws CoreException
	{
		if (file.exists())
		{
			if (replace)
			{
				file.delete(true, null);
			}
			else
			{
				return;
			}
		}

		file.create(new ByteArrayInputStream(content), true, null);
	}

	public static void write(IFile file, String content, Charset charset, boolean replace) throws CoreException
	{
		write(file, content.getBytes(charset), replace);
	}
}
