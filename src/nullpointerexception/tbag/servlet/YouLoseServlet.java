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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("YouLoseServlet: doGet");
		
		ArrayList<String> loseList = new ArrayList<String>();
		loseList.add("https://www.youtube.com/embed/DRolhuX-Enw?controls=1&mute=0&autoplay=1");	// Blue kid 
		loseList.add("https://www.youtube.com/embed/2JYJF9L5YW4?controls=1&mute=0&autoplay=1");	// Baby eats phone 
		loseList.add("https://www.youtube.com/embed/2JYJF9L5YW4?controls=1&mute=0&autoplay=1");	// Not sure 
		loseList.add("https://www.youtube.com/embed/8Ed3zmavQrA?controls=1&mute=0&autoplay=1"); // Also not sure 
		loseList.add("https://www.youtube.com/embed/5FS3OwyJ9Fw?controls=1&mute=0&autoplay=1");	// One of the frog things 
		loseList.add("https://www.youtube.com/embed/8iMDRfUCLmw?controls=1&mute=0&autoplay=1"); // Another one of the frog things 
		loseList.add("https://www.youtube.com/embed/-2ySzV-HGxM?controls=1&mute=0&autoplay=1"); // Uh 
		loseList.add("https://www.youtube.com/embed/ImQ7K9iIupg?controls=1&mute=0&autoplay=1"); // Tyler1 dies 
		loseList.add("https://www.youtube.com/embed/3DY_WDaaK9w?controls=1&mute=0&autoplay=1"); // Nice jacket 
		loseList.add("https://www.youtube.com/embed/XH4qbL-PGhI?controls=1&mute=0&autoplay=1"); // The dude from king of the hill dies 
		loseList.add("https://www.youtube.com/embed/C3E_sjZDVUo?controls=1&mute=0&autoplay=1"); // Patrick dies 
		loseList.add("https://www.youtube.com/embed/frpKp420_Qk?controls=1&mute=0&autoplay=1"); // Emoji dies 
		loseList.add("https://www.youtube.com/embed/j9V78UbdzWI?controls=1&mute=0&autoplay=1&amp;start=31"); // coffin dance 
		
		int random = rand.nextInt(loseList.size());
		
		String fileName = loseList.get(random);
		
		req.setAttribute("videoURL", fileName);
		
		req.getRequestDispatcher("/_view/youdied.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

			System.out.println("\nYouLoseServlet: doPost");
			
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