package meperry.config;

import meperry.servlets.MyProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import meperry.services.ImageDAO;
import meperry.services.LoginDAO;
import meperry.services.PasswordEncoder;
import meperry.services.UserDAO;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfig {
	@Value("${APP.NAME}")
	private String appName;

	@Value("${IMAGES.PATH}")
	private String imagesPath;

	@Value("${DB.URL}")
	private String url;
	@Value("${DB.USERNAME}")
	private String username;
	@Value("${DB.PASSWORD}")
	private String password;

	@Bean
	public MyProperties myProperties() {
		MyProperties myProperties = new MyProperties();
		myProperties.setProperty("appName", appName);
		myProperties.setProperty("imagesPath", imagesPath);
		return myProperties;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}

	@Bean
	public UserDAO userDAO() {
		return new UserDAO(url, username, password);
	}

	@Bean
	public LoginDAO loginDAO() {
		return new LoginDAO(url, username, password);
	}

	@Bean
	public ImageDAO imageDAO() {
		return new ImageDAO(url, username, password);
	}
}
