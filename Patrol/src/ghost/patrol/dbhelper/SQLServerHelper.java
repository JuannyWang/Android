/**
 * 
 */
package ghost.patrol.dbhelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.util.Log;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-3-31
 */
public class SQLServerHelper {

	private static Connection con;
	private static Statement statement;

	public static void connect() {
		if (con != null) {
			disConnect();
		}
		String driver = "net.sourceforge.jtds.jdbc.Driver";
		String url = "jdbc:jtds:sqlserver://113.250.152.187:1433/worthcom1";
		String user = "sa";
		String password = "shixue";
		try {
			Class.forName(driver);
			con = (Connection) DriverManager.getConnection(url, user, password);
			if (!con.isClosed()) {
				Log.v("SqlServerHelper",
						"Succeeded connecting to the Database!");
			} else {
				Log.v("SqlServerHelper", "Failed connecting to the Database!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void disConnect() {
		if (con == null)
			return;
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet doSql(String sql) {
		ResultSet rs = null;
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
