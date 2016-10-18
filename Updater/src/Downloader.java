import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JOptionPane;

public class Downloader implements Runnable {

	private static String SERVER;// = "http://disk.karelia.pro/ghe9yQs/cogn_reab.jar";
	private final static String PATH = "methods.jar";
	private final static String TMP_PATH = "tmp.jar";
	Thread mainThread;

	public Downloader(String server, Thread mainThread) {
		if (server != null)
			SERVER = server;
		this.mainThread = mainThread;
	}

	public void downloadFiles() {
		File f = new File(TMP_PATH);
		File ffinal = new File(PATH);
		try {
			URL connection = new URL(SERVER);
			HttpURLConnection urlconn;
			urlconn = (HttpURLConnection) connection.openConnection();
			urlconn.setRequestMethod("GET");
			int responseCode = urlconn.getResponseCode();			
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// try to download
				f.createNewFile();
				InputStream in = null;
				in = urlconn.getInputStream();
				Files.copy(in, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
				in.close();
				// success
				ffinal.createNewFile();
				Files.copy(f.toPath(), ffinal.toPath(), StandardCopyOption.REPLACE_EXISTING);
				f.delete();
				mainThread.interrupt();
				Object[] options = { "OK" };
				JOptionPane.showOptionDialog(null, "Программа 'Методики' успешно обновлена!",
						"Обновление", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			// inform user, delete files
			if (f.exists())
				f.delete();
			Object[] options = { "OK" };
			JOptionPane.showOptionDialog(null, "Ошибка обновления!",
					"Обновление", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options,
					options[0]);
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void run() {
		downloadFiles();
	}

}