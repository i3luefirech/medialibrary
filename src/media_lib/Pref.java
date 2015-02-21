/**
 * 
 */
package media_lib;

import java.awt.Dimension;
import java.util.prefs.Preferences;

/**
 * @author bluefire
 *
 */
public class Pref {
	private Preferences prefs;
	
	final String PREF_DB_PATH = "DB_PATH";
	final String PREF_SEARCH_PATH = "SEARCH_PATH";
	
	public Pref()
	{
		// read Prefs
		prefs = Preferences.userRoot().node(this.getClass().getName());
	}
	
	public void setDB(boolean hasdb) {
		prefs.putBoolean(PREF_DB_PATH, hasdb);
	}

	public boolean gethasDB() {
		return prefs.getBoolean(PREF_DB_PATH, false);
	}
	
	public void setSF(String searchpath) {
		prefs.put(PREF_SEARCH_PATH, searchpath);
	}

	public String getSF() {
		return prefs.get(PREF_SEARCH_PATH, "");
	}

	public Dimension getPreferredSize(){
		return new Dimension(900, 800);
	}
}