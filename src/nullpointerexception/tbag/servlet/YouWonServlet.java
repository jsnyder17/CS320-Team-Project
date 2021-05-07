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

		System.out.println("YouWinServlet: doGet");
		
		ArrayList<String> winList = new ArrayList<String>();
		winList.add("https://www.youtube.com/embed/MoI8Z8Dq1yY?controls=1&mute=0&autoplay=1");
		winList.add("https://www.youtube.com/embed/1Bix44C1EzY?controls=1&mute=0&autoplay=1");
		winList.add("https://www.youtube.com/embed/96YhBRqW6T4?controls=1&mute=0&autoplay=1");
		
		int random = rand.nextInt(winList.size());
		
		String fileName = winList.get(random);

		req.setAttribute("videoURL", fileName);
		
		req.getRequestDispatcher("/_view/youWon.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

			System.out.println("\nYouWonServlet: doPost");
			
			System.out.println("Restarting game...");
			
			DerbyDatabase db = new DerbyDatabase();
			db.setUsername((String)getServletContext().getAttribute("username"));
			db.dropTables();
			db.createTables();
			db.loadInitialData();
		
			System.out.println("   Redirecting to /game");

			
			//resp.sendRedirect(req.getContextPath() + "/game");
			req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
			return;
	}
}