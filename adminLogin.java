package Online_Voting_System;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class adminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Connection to DataBase
		DatabaseManager db = new DatabaseManager();
		java.sql.Connection con = db.getConnection();

		String adminName = request.getParameter("adminName");
		String password = request.getParameter("password");

		try {
			PreparedStatement preparedStatement = con.prepareStatement(
					"select * from Admin where adminName = '" + adminName + "' and adminPass = '" + password + "'");
			ResultSet rs = ((java.sql.Statement) preparedStatement).executeQuery(
					"select * from Admin where adminName = '" + adminName + "' and adminPass = '" + password + "'");
			if (rs.next()) {
				// Admin Login ID and password gets match and we request a http session to
				// transfer the page to adminWelcome
				HttpSession session = request.getSession();
				session.setAttribute("adminname", adminName);
				response.sendRedirect("adminWelcome.jsp");
			} else {
				out.println("WRONG CREDENTIALS");
			}
		} catch (Exception e) {
			out.println(e.getStackTrace());
		}
	}

}
