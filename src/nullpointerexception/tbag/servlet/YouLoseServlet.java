package nullpointerexception.tbag.servlet;

import java.util.Random;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nullpointerexception.tbag.persist.DerbyDatabase;


public class YouLoseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random rand = new Random();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nYouLoseServlet: doGet");
		
		ArrayList<String> loseList = new ArrayList<String>();
		loseList.add("bluekid");
		loseList.add("youdied");
		loseList.add("mayor");
		loseList.add("chariots");
		loseList.add("frogg");
		loseList.add("frogg2");
		loseList.add("therules");
		loseList.add("tyler");
		loseList.add("nicejacket");
		loseList.add("bobby");
		loseList.add("patrick");
		loseList.add("emoji");
		loseList.add("ohbrother");
		
		
		int random = rand.nextInt(loseList.size());
		
		String fileName = loseList.get(random);

		req.getRequestDispatcher("/_view/" + fileName +".jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

			System.out.println("\nYouLoseServlet: doPost");
			
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