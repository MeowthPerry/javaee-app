package meperry.services;

import org.springframework.beans.factory.annotation.Autowired;
import meperry.models.User;

import javax.annotation.PreDestroy;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {
	private PasswordEncoder passwordEncoder;

	private PreparedStatement getEntityByEmailStatement;

	public UserDAO(String URL, String USERNAME, String PASSWORD) {
		super(URL, USERNAME, PASSWORD);
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			getEntityByEmailStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
			updateStatement = connection.prepareStatement("UPDATE users SET avatar_id = ? WHERE id = ?");
			createStatement = connection.prepareStatement("INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<User> getAll() {
		return null;
	}

	@Override
	public User getEntityById(int id) {
		return null;
	}

	public User getEntityByEmail(String email) {
		try {
			getEntityByEmailStatement.setString(1, email);
			ResultSet resultSet = getEntityByEmailStatement.executeQuery();

			if (resultSet.next()) {
				return new User(
						Integer.parseInt(resultSet.getString("id")),
						Integer.parseInt(resultSet.getString("avatar_id")),
						resultSet.getString("name"),
						resultSet.getString("surname"),
						resultSet.getString("phone"),
						resultSet.getString("email"),
						resultSet.getString("password"),
						Long.parseLong(resultSet.getString("common_memory"))
				);
			} else return null;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public User update(User entity) {
		try {
			updateStatement.setInt(1, entity.getAvatarId());
			updateStatement.setInt(2, entity.getId());

			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getEntityById(entity.getId());
	}

	@Override
	public boolean create(User entity) {
		try {
			createStatement.setInt(1, entity.getAvatarId());
			createStatement.setString(2, entity.getName());
			createStatement.setString(3, entity.getSurname());
			createStatement.setString(4, entity.getPhone());
			createStatement.setString(5, entity.getEmail());
			createStatement.setString(6, passwordEncoder.hashPassword(entity.getPassword()));
			createStatement.setLong(7, entity.getCommonMemory());

			createStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
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
				getEntityByEmailStatement.close();
				updateStatement.close();
				createStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
