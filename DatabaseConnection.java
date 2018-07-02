package menuPackage;
import java.sql.*;
import javax.swing.JOptionPane;



public class DatabaseConnection 
{
	
	static Connection conn = null;
	
	public static Connection sqliteConnection() {
		
		try {
			String url = "jdbc:sqlite:"+System.getProperty("user.dir")+"\\src\\Resources\\Database\\First.db";
			conn = DriverManager.getConnection(url);
			JOptionPane.showMessageDialog(null, "Connection to the database has been successfully established.");
			return conn;
		}
		
		catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "The following error has occured: " +e);
			return null;
		}
//		finally {
//			
//			/*try {
//			*	if (conn != null) {
//			*		
//			*		conn.close();
//			*	}
//			*}
//			*
//			*catch(Exception ex) {
//			*	JOptionPane.showMessageDialog(null, ex);
//			**/}
		}
	
	
	public DatabaseConnection() {
		
	}
	
	public void insertDetails(String username, String password) {
		
		String command = "INSERT INTO LoginDetails(Username, Password) VALUES(?, ?) ";
		
		try {
			conn = sqliteConnection();
			PreparedStatement prepState = conn.prepareStatement(command);
			
			prepState.setString(1, username);
			prepState.setString(2, password);
			prepState.executeUpdate();
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "The following username already exists!");
			
		}
		finally {
			
			try {
				if(conn != null)
					conn.close();
			}
			catch(Exception not) {
				JOptionPane.showMessageDialog(null, "The following error has occured: " +not, "Error closing the database", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public boolean selectData(String username, String password) {
		
		String sqlCommand = "SELECT Username, Password FROM LoginDetails WHERE Username = ? AND Password = ? ";
		
		try {
			conn = sqliteConnection();
			PreparedStatement prepState = conn.prepareStatement(sqlCommand);
			prepState.setString(1, username);
			prepState.setString(2, password);
			ResultSet rS = prepState.executeQuery();
			
			while(rS.next())
				return true;
		}
		catch(Exception exx) {
			JOptionPane.showMessageDialog(null, "The following error has occured: " +exx, "Connection error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		finally {
			
			try {
				
				if(conn != null)
					conn.close();
					//JOptionPane.showMessageDialog(null, "Database has been successfully closed.", "Database closed", JOptionPane.INFORMATION_MESSAGE);
				
			}
			
			catch(Exception not) {
				
				JOptionPane.showMessageDialog(null, "The following error has occured: " +not, "Error closing the database", JOptionPane.ERROR_MESSAGE);
				
			}
		}
		return false;
	}

}
