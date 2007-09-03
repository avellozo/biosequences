/*
 * Created on 07/10/2004
 */
package tools.buildCompmat;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Augusto
 */
public class Messages
{
	private static final String			BUNDLE_NAME		= "tools.buildCompmat.messages";		//$NON-NLS-1$

	private static final ResourceBundle	RESOURCE_BUNDLE	= ResourceBundle
															.getBundle(BUNDLE_NAME);

	private Messages()
	{
	}

	public static String getString(String key)
	{
		try
		{
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e)
		{
			return '!' + key + '!';
		}
	}
}