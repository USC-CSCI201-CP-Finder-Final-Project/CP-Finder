import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import db.*;
import models.*;
import util.ImmutableList;

/**
 * Servlet implementation class DetailsServ
 */
@WebServlet("/QueueServlet")
public class QueueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CREDENTIALS_STRING = "jdbc:mysql://google/cpfinder?cloudSqlInstance=cpfinder-259622:us-west2:db1&socke"
			+ "tFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root" +
		"&password=uscCs201!";
	static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueueServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	PrintWriter out = response.getWriter();
    	QueueClient qc = (QueueClient) session.getAttribute("queueClient");
    	if(request.getParameter("func").equals("getNewQueue")) {
    		qc.code= qc.getQ();
    		session.setAttribute("queueChanged", "false");
    		out.print(qc.code);
    		qc.code = "";
    		return;
    	}
    	else if(request.getParameter("func").equals("sendChange")) {
    		System.out.println(qc.changed);
    		qc.change();
    	}
    	else if(request.getParameter("func").equals("pollChange")) {
    		boolean b = false;
    		if(qc != null) {
    			b = qc.newQueue;
    		}
    		if(b == true) {
    			qc.code= qc.getQ();
    			System.out.println("p");
    			out.print(qc.code);
    			out.flush();
    			qc.code = "";
    			qc.newQueue = false;
    			session.setAttribute("queueClient", qc);
    			return;
    		}
    		else {
    			out.print("false");
    			out.flush();
    			return;
    		}
    	}
    	RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/Details.jsp");
		dispatch.forward(request, response);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
