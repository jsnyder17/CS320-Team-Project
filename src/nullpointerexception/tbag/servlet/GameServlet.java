package nullpointerexception.tbag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nullpointerexception.tbag.controller.GameManagerController;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Game Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("Game Servlet: doPost");
		
		GameManagerModel gm = new GameManagerModel();
		//GameManagerController gmc = new GameManagerController(gm);
		
		DerbyDatabase db = new DerbyDatabase();
		
		GameManagerController gmc = new GameManagerController(gm, db);
		
		gmc.setCommand(req.getParameter("commandInput"));
		gmc.doGame();
		
		req.setAttribute("gameManager", gm);
		req.setAttribute("player", gm.getPlayer());
		
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
}
