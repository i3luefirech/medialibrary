/**
 * 
 */
package media_lib;


import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/*import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;*/

/**
 * @author bluefire
 *
 */
public class media_lib {
	static GUI_search myGUI_s;
	static Pref myPref;
	static mydb my_db;

	static JTabbedPane tabbedPane;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		tabbedPane = new JTabbedPane();

		myPref = new Pref();
		myGUI_s = new GUI_search(myPref);
		my_db = new mydb(myPref);
		
		myGUI_s.setLayout(new BoxLayout(myGUI_s, BoxLayout.Y_AXIS));
		
		if(my_db.connect()==0){
			if(!myPref.gethasDB())
			{
				if(my_db.createDB()!=0)
				{
					myGUI_s.initErr(2,"Create DB");
				}
			}
		}
		else{
			myGUI_s.initErr(1,"SQL Connect");
		}
		
		tabbedPane.addTab("Suchpfade", null, myGUI_s, "Suchpfade");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.addTab("Bibliothek", null, new JPanel(), "Bibliothek");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		tabbedPane.addTab("Einstellungen", null, new JPanel(), "Einstellungen");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		
		JFrame frame = new JFrame("");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/logo.png"));
		frame.setTitle("AML - Another Media Library");
		frame.setLocationByPlatform(true);
		frame.add(tabbedPane);
		frame.setSize(myPref.getPreferredSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

/*Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
for (Path name: dirs) {
	System.err.println(name);
	try (DirectoryStream<Path> stream = Files.newDirectoryStream(name)) {
	    for (Path file: stream) {
	        System.out.println(file.getFileName());
	    }
	} catch (IOException | DirectoryIteratorException x) {
	    // IOException can never be thrown by the iteration.
	    // In this snippet, it can only be thrown by newDirectoryStream.
	    System.err.println(x);
	}
}*/
