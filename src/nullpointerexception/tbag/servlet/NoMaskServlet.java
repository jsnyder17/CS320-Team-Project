package nullpointerexception.tbag.servlet;

import java.util.Random;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nullpointerexception.tbag.persist.DerbyDatabase;


public class NoMaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random rand = new Random();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("YouLoseServlet: doGet");
		
		req.getRequestDispatcher("/_view/nomask.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

			System.out.println("\nNoMaskServlet: doPost");
			
			System.out.println("Restarting game...");
			
			DerbyDatabase db = new DerbyDatabase();
			db.setUsername((String)getServletContext().getAttribute("username"));
			db.dropTables();
			db.createTables();
			db.loadInitialData();
		
			System.out.println("   Redirecting to /game");
			
			//resp.sendRedirect(req.getContextPath() + "/game");
			resp.sendRedirect(req.getContextPath() + "/game");
			return;
	}
}