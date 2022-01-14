package meperry.listeners;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import meperry.config.SpringConfig;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@PropertySource("classpath:application.properties")
public class MyServletContextListener implements ServletContextListener {
	ServletContext context;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context = sce.getServletContext();
		AnnotationConfigApplicationContext springContext =
				new AnnotationConfigApplicationContext((SpringConfig.class));
		context.setAttribute("springContext", springContext);
	}
}
