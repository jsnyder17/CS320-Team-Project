package nullpointerexception.tbag.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nullpointerexception.tbag.controller.GameManagerController;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Random rand;
	private GameManagerModel gm;
	private GameManagerController gmc;
	private DerbyDatabase db;
	private String username;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Game Servlet: doPost");
		
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Game Servlet: doPost");
		
		ServletContext servletContext = getServletContext();
		String username = (String)servletContext.getAttribute("username");
		
		if (username == null) {
			System.out.println("    User: <" + username + "> not logged in or session timed out. ");
			
			resp.sendRedirect(req.getContextPath() + "/index");
		}
		
		Random rand = new Random();
		
		GameManagerModel gm = new GameManagerModel();
		//GameManagerController gmc = new GameManagerController(gm);
		
		DerbyDatabase db = new DerbyDatabase();
		db.setUsername(username);
		db.initialize();
		
		GameManagerController gmc = new GameManagerController(username, gm, db);
		
		gmc.setCommand(req.getParameter("commandInput"));
		gmc.doGame();
		
		if (gm.getDeathEnding()) {
			if (((Clothing) gm.getPlayer().getInventory().getItem("mask")).getWearing() == false) {
				resp.sendRedirect(req.getContextPath() + "/nomask");
			}
			else {
				resp.sendRedirect(req.getContextPath() + "/youdied");
			}
		}
		else if (gm.getCompletedEnding()) {
			resp.sendRedirect(req.getContextPath() + "/youWon");
		}
		else {
			req.setAttribute("gameManager", gm);
			req.setAttribute("player", gm.getPlayer());
			
			req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
		}
	}
}
