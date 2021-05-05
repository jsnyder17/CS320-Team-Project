package nullpointerexception.tbag.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nullpointerexception.tbag.controller.GameManagerController;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random rand;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Game Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("Game Servlet: doPost");
		
		Random rand = new Random();
		
		GameManagerModel gm = new GameManagerModel();
		//GameManagerController gmc = new GameManagerController(gm);
		
		DerbyDatabase db = new DerbyDatabase();
		
		GameManagerController gmc = new GameManagerController(gm, db);
		
		gmc.setCommand(req.getParameter("commandInput"));
		gmc.doGame();
		
		if (gm.getDeathEnding()) {
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
			
			int random = rand.nextInt(loseList.size());
			
			String fileName = loseList.get(random);

			req.getRequestDispatcher("/_view/" + fileName +".jsp").forward(req, resp);
		}
		else if (gm.getCompletedEnding()) {
			System.out.println("\nLoginServlet: doGet");
			
			ArrayList<String> winList = new ArrayList<String>();
			winList.add("youWon");
			winList.add("congradulations");
			winList.add("amongus");
			
			int random = rand.nextInt(winList.size());
			
			String fileName = winList.get(random);

			req.getRequestDispatcher("/_view/" + fileName +".jsp").forward(req, resp);
			
			req.getRequestDispatcher("/_view/youWon.jsp").forward(req, resp);
		}
		else {
			req.setAttribute("gameManager", gm);
			req.setAttribute("player", gm.getPlayer());
			
			req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
		}
	}
}
