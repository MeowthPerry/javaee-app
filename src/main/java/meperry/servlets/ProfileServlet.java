package meperry.servlets;

import meperry.models.Login;
import org.springframework.context.ApplicationContext;
import meperry.models.User;
import meperry.services.ImageDAO;
import meperry.services.LoginDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProfileServlet extends HttpServlet {
	ServletContext servletContext;

	LoginDAO loginDAO;
	ImageDAO imageDAO;

	MyProperties myProperties;

	@Override
	public void init(ServletConfig config) throws ServletException {
		servletContext = config.getServletContext();
		ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
		loginDAO = springContext.getBean("loginDAO", LoginDAO.class);
		imageDAO = springContext.getBean("imageDAO", ImageDAO.class);
		myProperties = springContext.getBean("myProperties", MyProperties.class);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<Login> logins = loginDAO.getEntitiesByUserId(user.getId());
		if (logins.size() > 3)
			request.setAttribute("logins", logins.subList(logins.size() - 3, logins.size()));
		else
			request.setAttribute("logins", logins);
		request.setAttribute("images", imageDAO.getEntitiesByUserId(user.getId()));
		request.setAttribute("avatar", imageDAO.getEntityById(user.getAvatarId()).getUniqueName());
		request.setAttribute("user", user);
		request.setAttribute("usedMemory", imageDAO.getUsedMemoryByUserId(user.getId()));
		request.setAttribute("commonMemory", user.getCommonMemory());

		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/profile.jsp");
		requestDispatcher.forward(request, response);
	}
}
