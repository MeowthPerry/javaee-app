package meperry.servlets;

import org.apache.commons.io.FilenameUtils;
import org.springframework.context.ApplicationContext;
import meperry.models.Image;
import meperry.models.User;
import meperry.services.ImageDAO;
import meperry.services.UserDAO;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@PropertySource("classpath:application.properties")
public class ImagesServlet extends HttpServlet {
	ServletContext servletContext;

	UserDAO userDAO;
	ImageDAO imageDAO;

	MyProperties myProperties;

	@Override
	public void init(ServletConfig config) throws ServletException {
		servletContext = config.getServletContext();
		ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
		userDAO = springContext.getBean("userDAO", UserDAO.class);
		imageDAO = springContext.getBean("imageDAO", ImageDAO.class);
		myProperties = springContext.getBean("myProperties", MyProperties.class);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		for (Part part : request.getParts()) {
			if (part.getName().equals("image")) {
				String originalName = part.getSubmittedFileName();
				String extension = FilenameUtils.getExtension(originalName);
				String uniqueName = UUID.randomUUID().toString() + '.' + extension;
				long size = part.getSize();
				String mime = part.getContentType();

				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("user");

				if (imageDAO.getUsedMemoryByUserId(user.getId()) + size > user.getCommonMemory())
					break;

				part.write(myProperties.getProperty("imagesPath") + uniqueName);

				imageDAO.create(new Image(user.getId(), originalName, uniqueName, size, mime));

				user.setAvatarId(imageDAO.getEntityByUniqueName(uniqueName).getId());
				userDAO.update(user);
			}
		}
		response.sendRedirect("/" + myProperties.getProperty("appName") + "/profile");
	}
}
