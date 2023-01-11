package esempio;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InsertMessageServlet
 */
@WebServlet("/InsertMessageServlet")
public class InsertMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LocalDateTime lastCallServer = LocalDateTime.MIN;

    /**
     * Default constructor. 
     */
    public InsertMessageServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String usernameInserito = request.getParameter("username_input");
		String messaggioInserito = request.getParameter("messaggio_input");
		String timeInserito = request.getParameter("localTime_input");
		String output= "<html><body>";
		
		int righeInserite=0;
		
		try {
			righeInserite = insertNewMessage (usernameInserito,messaggioInserito,timeInserito);
		} catch (ClassNotFoundException | SQLException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(righeInserite>0) {
			output += "<h2>HO INSERITO CORRETTAMENTE IL MESSAGGIO CHE MI HAI DATO</h2>";
		}else {
			output += "<h2>NON è STATO POSSIBILE AGGIUNGERE IL MESSAGGIO</h2>";
		}
		
		
		try {
			output += getAllMessages();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.getWriter().append(output);
		

	}
	
	private int insertNewMessage(String insertedUsername,String insertedMessage,String time) throws ClassNotFoundException, SQLException, IOException {
		Message messageToInsert = new Message();
		messageToInsert.setUserName(insertedUsername);
		messageToInsert.setText(insertedMessage);
		LocalDateTime localtime = LocalDateTime.parse(time);
		messageToInsert.setUserInsertedTime(localtime);
		messageToInsert.setServerReceivedTime(localtime);
		int rowsUpdate=DatabaseManagerSingleton.getInstance().insertMessage(messageToInsert);
		return rowsUpdate;
	}
	
	private String getAllMessages() throws ClassNotFoundException, SQLException, IOException {
		return DatabaseManagerSingleton.getInstance().getStringMessages();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
