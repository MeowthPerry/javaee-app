package meperry.servlets;

import meperry.models.Image;
import meperry.services.ImageDAO;
import meperry.services.UserDAO;
import org.springframework.context.ApplicationContext;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

public class ImageServlet extends HttpServlet {
	ServletContext servletContext;

	UserDAO userDAO;
	ImageDAO imageDAO;

	MyProperties myProperties;

	@Override
	public void init(ServletConfig config) {
		servletContext = config.getServletContext();
		ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
		userDAO = springContext.getBean("userDAO", UserDAO.class);
		imageDAO = springContext.getBean("imageDAO", ImageDAO.class);
		myProperties = springContext.getBean("myProperties", MyProperties.class);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String uniqueName = request.getRequestURL().toString().split("/")[5];

		File file = new File(myProperties.getProperty("imagesPath") + uniqueName);
		try {
			response.setContentType(imageDAO.getEntityByUniqueName(uniqueName).getMime());

			FileInputStream inputStream = new FileInputStream(file);
			OutputStream out = response.getOutputStream();

			byte[] buf = new byte[1024];
			int count;
			while ((count = inputStream.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}

			out.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
