/**
 * 
 */
package media_lib;


import javax.swing.BoxLayout;
import javax.swing.JFrame;

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
	static GUI myGUI;
	static Pref myPref;
	static mydb my_db;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		myPref = new Pref();
		myGUI = new GUI(myPref);
		my_db = new mydb();
		
		myGUI.setLayout(new BoxLayout(myGUI, BoxLayout.Y_AXIS));
		
		if(my_db.connect()==0){
			if(!myPref.gethasDB())
			{
				if(my_db.createDB()!=0)
				{
					myGUI.initErr(2,"Create DB");
				}
			}
			else
			{
				myGUI.setSearchfolders(myPref.getSF());
			}
		}
		else{
			myGUI.initErr(1,"SQL Connect");
		}
		
		JFrame frame = new JFrame("");
		frame.setLocationByPlatform(true);
		frame.add(myGUI);
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
