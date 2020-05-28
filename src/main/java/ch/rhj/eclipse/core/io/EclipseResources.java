package ch.rhj.eclipse.core.io;

import java.net.URL;
import java.nio.charset.Charset;

import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.Bundle;

public interface EclipseResources
{
	public static byte[] bytes(String caller, Bundle bundle, String name) throws CoreException
	{
		URL url = bundle.getResource(name);

		return url == null ? null : EclipseRead.bytes(caller, bundle.getResource(name));
	}

	public static String string(String caller, Bundle bundle, String name, Charset charset) throws CoreException
	{
		byte[] bytes = bytes(caller, bundle, name);

		return bytes == null ? null : new String(bytes, charset);
	}
}
