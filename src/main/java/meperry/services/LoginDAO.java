package meperry.services;

import meperry.models.Login;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginDAO extends AbstractDAO<Login> {
	private PreparedStatement getEntitysByUserIdStatement;

	public LoginDAO(String URL, String USERNAME, String PASSWORD) {
		super(URL, USERNAME, PASSWORD);
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			getEntitysByUserIdStatement = connection.prepareStatement("SELECT * FROM logins WHERE user_id = ?");
			createStatement = connection.prepareStatement("INSERT INTO logins VALUES(DEFAULT, ?, ?, ?, ?)");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Login> getAll() {
		return null;
	}

	@Override
	public Login getEntityById(int id) {
		return null;
	}

	public List<Login> getEntitiesByUserId(int userId) {
		List<Login> logins = new ArrayList<>();

		try {
			getEntitysByUserIdStatement.setInt(1, userId);
			ResultSet resultSet = getEntitysByUserIdStatement.executeQuery();

			while (resultSet.next()) {
				Login current = new Login(
						Integer.parseInt(resultSet.getString("id")),
						Integer.parseInt(resultSet.getString("user_id")),
						resultSet.getString("date"),
						resultSet.getString("time"),
						resultSet.getString("ip")
				);
				logins.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return logins;
	}

	@Override
	public Login update(Login entity) {
		return null;
	}

	@Override
	public boolean create(Login entity) {
		try {
			createStatement.setInt(1, entity.getUserId());
			createStatement.setString(2, entity.getDate());
			createStatement.setString(3, entity.getTime());
			createStatement.setString(4, entity.getIp());

			createStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createNow(int userId) {
		Date now = new Date();
		SimpleDateFormat date = new SimpleDateFormat("MMMM dd, yyyy");
		SimpleDateFormat time = new SimpleDateFormat("HH:mm");

		String ip;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			return create(new Login(userId, date.format(now), time.format(now), ip));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}

	@PreDestroy
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				getEntitysByUserIdStatement.close();
				createStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
