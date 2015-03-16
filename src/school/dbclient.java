package school;

import com.mysql.jdbc.ResultSet;

public interface dbclient {
	// Data
	int createTable(String table, String[ ] properties, int[ ] Type);
	int deleteTable(String name);
	ResultSet[ ] selectQry(String[ ] table, String[ ][ ] values, String[ ] filter);
	int insertQry(String[ ] table, String[ ][ ] names, String[ ][ ] values);
	int updateQry(String[ ] table, String[ ][ ] names, String[ ][ ] values);
}
