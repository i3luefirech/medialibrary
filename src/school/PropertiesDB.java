package school;

import com.mysql.jdbc.ResultSet;

public interface PropertiesDB extends dbclient {
	ResultSet getPropertiesByFileID(int[ ] id);
	int editPropertiesByFileID(int[ ] id, String[ ][ ] names, String[ ][ ] values);
	int addPropertiesByFileID(int[ ] id, String[ ][ ] names, String[ ][ ] values);
}
