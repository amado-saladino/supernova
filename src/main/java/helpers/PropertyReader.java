package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * get the configuration properties from the .properties
 * file
 */
public class PropertyReader {
	private static PropertyReader INSTANCE = null;
	private final Properties props = new Properties();

	private PropertyReader() {
		loadProperties("config.properties");
		props.putAll(System.getProperties());
	}

	private static PropertyReader getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PropertyReader();
		}
		return INSTANCE;
	}

	public static String getProperty(final String key) {
		return getInstance().props.getProperty(key);
	}

	public static int getIntegerProperty(final String key, final int defaultValue) {
		int value = 0;
		String val = getInstance().props.getProperty(key);
		if (val == null) return defaultValue;
		value = Integer.parseInt(val);
		return value;
	}

	public static String getProperty(final String key, final String defaultValue) {
		return getInstance().props.getProperty(key, defaultValue);
	}

	public static void loadPropertiesFromFile(String path) {
		getInstance().loadProperties(path);
	}

	private void loadProperties(final String path) {
		InputStream inputStream = null;
		try {
			inputStream = ClassLoader.getSystemResourceAsStream(path);
			if (inputStream !=null) {
				props.load(inputStream);
			}
			else {
				throw new UnableToLoadPropertiesException("Property file " + path
				 + " not found");
			}
		} catch (Exception exeption) { exeption.printStackTrace(); }
		finally {
			try {
				inputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static Properties getProps() {
		return getInstance().props;
	}
}

class UnableToLoadPropertiesException extends RuntimeException {
	UnableToLoadPropertiesException(final String file) {
		super(file);
	}
	public UnableToLoadPropertiesException(final String message, final Exception ex) {
		super(message, ex);
	}
}
