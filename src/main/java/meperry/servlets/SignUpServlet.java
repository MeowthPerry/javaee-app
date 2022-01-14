package meperry.servlets;

import org.springframework.context.ApplicationContext;
import meperry.models.User;
import meperry.services.LoginDAO;
import meperry.services.PasswordEncoder;
import meperry.services.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
	ServletContext servletContext;

	UserDAO userDAO;
	LoginDAO loginDAO;

	PasswordEncoder passwordEncoder;

	MyProperties myProperties;

	@Override
	public void init(ServletConfig config) throws ServletException {
		servletContext = config.getServletContext();
		ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
		userDAO = springContext.getBean("userDAO", UserDAO.class);
		loginDAO = springContext.getBean("loginDAO", LoginDAO.class);
		passwordEncoder = springContext.getBean("passwordEncoder", PasswordEncoder.class);
		myProperties = springContext.getBean("myProperties", MyProperties.class);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/static/signUp.html");
		requestDispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		userDAO.create(new User(
				request.getParameter("name"),
				request.getParameter("surname"),
				request.getParameter("phone"),
				request.getParameter("email"),
				request.getParameter("password")
		));

		User user = userDAO.getEntityByEmail(request.getParameter("email"));

		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		loginDAO.createNow(user.getId());

		response.sendRedirect("/" + myProperties.getProperty("appName") + "/profile");
	}

	@Override
	public void destroy() {

	}
}
