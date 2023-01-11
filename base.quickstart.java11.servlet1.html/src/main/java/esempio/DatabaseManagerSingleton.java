package esempio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DatabaseManagerSingleton {

	private static DatabaseManagerSingleton instance;
	//private final static Logger logger = LoggerFactory.getLogger(DatabaseManagerSingleton.class);

	private DatabaseManagerSingleton() {
		//logger.debug("Instanziato Costruttore Privato Database Manager Singleton");
	}

	public static DatabaseManagerSingleton getInstance() {
		if (instance == null) {
			instance = new DatabaseManagerSingleton();
		}
		return instance;
	}

	public ArrayList<Message> getMessages(LocalDateTime lastCall) throws ClassNotFoundException, SQLException, IOException {
		ArrayList<Message> messagesReturn = new ArrayList();
		
		StringBuilder sb = new StringBuilder();
		
		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);
		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String dbName= PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+dbName;
		
		String userName = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");
		Connection con = DriverManager.getConnection(url, userName, password);

		Statement cmd = con.createStatement();

		String query = "SELECT * FROM messages WHERE serverReceivedTime > '" + lastCall + "';";
		ResultSet res = cmd.executeQuery(query);
		
		sb.append("<html><body><table>");
		while (res.next()) {
			Message msg = new Message();
			sb.append("<tr>");
			
			sb.append("<td>");
			msg.setUserName(res.getString("userName"));
			sb.append("<tr>");
			
			sb.append("<td>");
			msg.setText(res.getString("textMessage"));
			sb.append("<tr>");
			
			sb.append("<td>");
			msg.setUserInsertedTime((res.getTimestamp("userInsertedTime")).toLocalDateTime());
			sb.append("<tr>");
			
			sb.append("<td>");
			msg.setServerReceivedTime((res.getTimestamp("serverReceivedTime")).toLocalDateTime());
			sb.append("<tr>");
			
			sb.append("</tr>");
			messagesReturn.add(msg);
		}
		sb.append("</table></body></html>");

		return messagesReturn;
	}

	public int insertMessage(Message messageToInsert) throws ClassNotFoundException, SQLException, IOException {
		int rowsUpdate = 0;

		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);

		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String dbName= PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+dbName;
		
		String userName = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");

		Connection con = DriverManager.getConnection(url, userName, password);
		Statement cmd = con.createStatement();

		Timestamp userInsertedTime = Timestamp.valueOf(messageToInsert.getUserInsertedTime());
		Timestamp serverReceivedTime = Timestamp.valueOf(messageToInsert.getServerReceivedTime());

		String query = "insert into messages(userName,textMessage,userInsertedTime,serverReceivedTime) values('"
				+ messageToInsert.getUserName() + "','" + messageToInsert.getText() + "','" + userInsertedTime + "','"
				+ serverReceivedTime + "');";

		rowsUpdate = cmd.executeUpdate(query);

		//logger.debug("sono state modificate : " + rowsUpdate + " righe");

		con.close();
		//logger.debug("connessione chiusa");

		return rowsUpdate;
	}

	public void updateMessage() throws ClassNotFoundException, SQLException, IOException {

		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);

		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String dbName= PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+dbName;
		
		String userName = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");

		Connection con = DriverManager.getConnection(url, userName, password);

		Statement cmd = con.createStatement();

		String query = "UPDATE messages SET textMessage = 'hello' WHERE id = 45";

		int rowsUpdated = cmd.executeUpdate(query);

		//logger.debug("sono state modificate : " + rowsUpdated + " righe");

		con.close();
		//logger.debug("connessione chiusa");
	}

	public void deleteMessage() throws ClassNotFoundException, SQLException, IOException {

		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);

		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String dbName= PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+dbName;
		
		String userName = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");

		Connection con = DriverManager.getConnection(url, userName, password);

		Statement cmd = con.createStatement();

		String query = "DELETE FROM messages where id = 46";

		int rowsUpdated = cmd.executeUpdate(query);

		//logger.debug("sono state modificate : " + rowsUpdated + " righe");

		con.close();
		//logger.debug("connessione chiusa");
	}
	
	public String getStringMessages() throws ClassNotFoundException, SQLException, IOException {
		StringBuilder sb = new StringBuilder();
		
		String driver = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.driver");
		Class.forName(driver);
		String host = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.host");
		String port = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.port");
		String dbName= PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.name");
		String url = "jdbc:mariadb://"+host+":"+port+"/"+dbName;
		
		String userName = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.username");
		String password = PropertiesManagerSingleton.getInstance().getProperty("database.mysql.db.password");
		Connection con = DriverManager.getConnection(url, userName, password);

		Statement cmd = con.createStatement();

		String query = "SELECT * FROM messages";
		ResultSet res = cmd.executeQuery(query);
		
		sb.append("<table>");
		while (res.next()) {
			sb.append("<tr>");
			
			sb.append("<td>");
			sb.append(res.getString(2));
			sb.append("</td>");
			
			sb.append("<td>");
			sb.append(res.getString(3));
			sb.append("</td>");
			
			sb.append("<td>");
			sb.append(res.getString(4));
			sb.append("</td>");
			
			sb.append("<td>");
			sb.append(res.getString(5));
			sb.append("</td>");
			
			sb.append("</tr>");
		}
		sb.append("</table></body></html>");

		return sb.toString();
	}

}