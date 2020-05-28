package ch.rhj.eclipse.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public interface EclipseCopy
{
	public static void copy(String caller, InputStream input, OutputStream output) throws CoreException
	{
		try
		{
			byte[] buffer = new byte[0x10000];
			int len = 0;

			while ((len = input.read(buffer)) > 0)
			{
				output.write(buffer, 0, len);
			}
		}
		catch (IOException e)
		{
			throw new CoreException(new Status(IStatus.ERROR, caller, e.getMessage(), e));
		}
	}
}
