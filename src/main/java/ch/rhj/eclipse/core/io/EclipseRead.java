package ch.rhj.eclipse.core.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public interface EclipseRead
{
	public static byte[] bytes(String caller, URL url) throws CoreException
	{
		try (InputStream input = url.openStream())
		{
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			EclipseCopy.copy(caller, input, output);

			return output.toByteArray();
		}
		catch (IOException e)
		{
			throw new CoreException(new Status(IStatus.ERROR, caller, e.getMessage(), e));
		}
	}

	public static String string(String caller, URL url, Charset charset) throws CoreException
	{
		return new String(bytes(caller, url), charset);
	}
}
