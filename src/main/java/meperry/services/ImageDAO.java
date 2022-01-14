package meperry.services;

import meperry.models.Image;

import javax.annotation.PreDestroy;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageDAO extends AbstractDAO<Image> {
	private PreparedStatement getEntityByIdStatement;
	private PreparedStatement getEntitysByUserIdStatement;
	private PreparedStatement getEntityByUniqueNameStatement;

	public ImageDAO(String URL, String USERNAME, String PASSWORD) {
		super(URL, USERNAME, PASSWORD);
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

			getEntityByIdStatement = connection.prepareStatement("SELECT * FROM images WHERE id = ?");
			getEntitysByUserIdStatement = connection.prepareStatement("SELECT * FROM images WHERE user_id = ?");
			getEntityByUniqueNameStatement = connection.prepareStatement("SELECT * FROM images WHERE unique_name = ?");
			createStatement = connection.prepareStatement("INSERT INTO images VALUES(DEFAULT, ?, ?, ?, ?, ?)");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Image> getAll() {
		return null;
	}

	@Override
	public Image getEntityById(int id) {
		try {
			getEntityByIdStatement.setInt(1, id);
			ResultSet resultSet = getEntityByIdStatement.executeQuery();

			if (resultSet.next()) {
				return new Image(
						Integer.parseInt(resultSet.getString("id")),
						Integer.parseInt(resultSet.getString("user_id")),
						resultSet.getString("original_name"),
						resultSet.getString("unique_name"),
						Long.parseLong(resultSet.getString("size")),
						resultSet.getString("mime")
				);
			} else return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Image getEntityByUniqueName(String uniqueName) {
		try {
			getEntityByUniqueNameStatement.setString(1, uniqueName);
			ResultSet resultSet = getEntityByUniqueNameStatement.executeQuery();

			if (resultSet.next()) {
				return new Image(
						Integer.parseInt(resultSet.getString("id")),
						Integer.parseInt(resultSet.getString("user_id")),
						resultSet.getString("original_name"),
						resultSet.getString("unique_name"),
						Long.parseLong(resultSet.getString("size")),
						resultSet.getString("mime")
				);
			} else return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Image> getEntitiesByUserId(int userId) {
		List<Image> images = new ArrayList<>();

		try {
			getEntitysByUserIdStatement.setInt(1, userId);
			ResultSet resultSet = getEntitysByUserIdStatement.executeQuery();

			while (resultSet.next()) {
				Image current = new Image(
						Integer.parseInt(resultSet.getString("id")),
						Integer.parseInt(resultSet.getString("user_id")),
						resultSet.getString("original_name"),
						resultSet.getString("unique_name"),
						Long.parseLong(resultSet.getString("size")),
						resultSet.getString("mime")
				);
				images.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return images;
	}

	@Override
	public Image update(Image entity) {
		return null;
	}

	@Override
	public boolean create(Image entity) {
		try {
			createStatement.setInt(1, entity.getUserId());
			createStatement.setString(2, entity.getOriginalName());
			createStatement.setString(3, entity.getUniqueName());
			createStatement.setLong(4, entity.getSize());
			createStatement.setString(5, entity.getMime());

			createStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public long getUsedMemoryByUserId(int userId) {
		long usedMemory = 0;

		for (Image image : getEntitiesByUserId(userId)) {
			usedMemory += image.getSize();
		}
		return  usedMemory;
	}

	@PreDestroy
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				getEntityByIdStatement.close();
				getEntitysByUserIdStatement.close();
				getEntityByUniqueNameStatement.close();
				createStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
