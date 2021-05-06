package nullpointerexception.tbag.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nullpointerexception.tbag.controller.LoginController;
import nullpointerexception.tbag.model.Library;


public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Library model;
	private LoginController controller;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("\nLoginServlet: doGet");

		req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("IndexServlet: doPost");

		String errorMessage = null;
		String name         = null;
		String pw           = null;
		boolean validLogin  = false;

		// Decode form parameters and dispatch to controller
		name = req.getParameter("username");
		pw   = req.getParameter("password");

		System.out.println("   Name: <" + name + "> PW: <" + pw + ">");			

		if (name == null || pw == null || name.equals("") || pw.equals("")) {
			errorMessage = "Please specify both user name and password";
		} 
		else {
			model      = new Library();
			controller = new LoginController(model);

			if (req.getParameter("new") != null) {
				errorMessage = controller.createNewUser(name, pw);
			}
			else {
				validLogin = controller.validateCredentials(name, pw);
	
				if (!validLogin) {
					errorMessage = "Username and/or password invalid";
				}
			}
		}

		// Add parameters as request attributes
		req.setAttribute("username", req.getParameter("username"));
		req.setAttribute("password", req.getParameter("password"));

		// Add result objects as request attributes
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("login",        validLogin);

		// if login is valid, start a session
		if (validLogin) {
			System.out.println("   Valid login - starting session, redirecting to /game");

			ServletContext servletContext = getServletContext();
			
			servletContext.setAttribute("username", name);

			// redirect to /index page
			req.getRequestDispatcher("/_view/game.jsp").forward(req,  resp);
		}
		else {
			System.out.println("Invalid login - returning to /Login");

			// Forward to view to render the result HTML document
			req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
		}
	}
}
