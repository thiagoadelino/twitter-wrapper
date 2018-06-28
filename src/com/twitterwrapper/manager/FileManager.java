package com.twitterwrapper.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class responsible for managing creation of temporary files of the application.
 * @author Thiago Adelino
 *
 */
public class FileManager {

	/**
	 * Downloads a file based on a URL.
	 * @param urlStr
	 * @return
	 */
	public static File downloadFile(String urlStr) {
		
		URL url;
		File file;
		
		String path = System.getProperty("PATH_FILE_TWITTER_WRAPPER");
		if(path == null)
			path = "tmp/";
		
		try {
			url = new URL(urlStr);
			file = new File(path + urlStr.substring(urlStr.lastIndexOf('/') + 1));
			copyURLToFile(url, file);
			return file;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Copies a file from a URL to an existent file on disk.
	 * @param url
	 * @param file
	 */
	private static void copyURLToFile(URL url, File file) {

		try {
			InputStream input = url.openStream();
			if (file.exists()) {
				if (file.isDirectory())
					throw new IOException("File '" + file + "' is a directory");

				if (!file.canWrite())
					throw new IOException("File '" + file + "' cannot be written");
			} else {
				File parent = file.getParentFile();
				if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
					throw new IOException("File '" + file + "' could not be created");
				}
			}

			FileOutputStream output = new FileOutputStream(file);

			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}

			input.close();
			output.close();

			System.out.println("File '" + file + "' downloaded successfully!");
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
	}

}
