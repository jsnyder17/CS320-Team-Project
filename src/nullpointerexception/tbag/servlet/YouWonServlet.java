package nullpointerexception.tbag.servlet;

import java.util.Random;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nullpointerexception.tbag.persist.DerbyDatabase;


public class YouWonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random rand = new Random();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nLoginServlet: doGet");
		
		ArrayList<String> winList = new ArrayList<String>();
		winList.add("youWon");
		winList.add("congradulations");
		winList.add("amongus");
	
		
		
		int random = rand.nextInt(winList.size());
		
		String fileName = winList.get(random);

		req.getRequestDispatcher("/_view/" + fileName +".jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

			System.out.println("\nYouWonServlet: doPost");
			
			System.out.println("Restarting game...");
			
			DerbyDatabase db = new DerbyDatabase();
			db.dropTables();
			db.createTables();
			db.loadInitialData();
		
			System.out.println("   Redirecting to /game");

			
			//resp.sendRedirect(req.getContextPath() + "/game");
			req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
			return;
	}
}