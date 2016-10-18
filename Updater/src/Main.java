
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		String s = null;
		if (args.length > 0)
			s = args[0];
		Downloader d = new Downloader(s, Thread.currentThread());
		Thread t = new Thread(d);
		t.start();
		JOptionPane.showOptionDialog(null, "Подождите, программа 'Методики' обновляется...",
				"Обновление", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, new Object[]{}, null);
		/*try {
			Thread.sleep(1000 * 60 * 10);
		} catch (InterruptedException e) {
			// updated, hide dialog
			System.out.println("!");
			Window[] windows = Window.getWindows();
			for (Window window : windows) {
				if (window instanceof JDialog) {
					JDialog dialog = (JDialog) window;
					if (dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
						dialog.dispose();
					}
				}
			}
		}*/
	}

}
