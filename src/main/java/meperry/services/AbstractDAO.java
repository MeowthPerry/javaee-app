package meperry.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public abstract class AbstractDAO <E> {
	private final String URL;
	private final String USERNAME;
	private final String PASSWORD;

	Connection connection;

	PreparedStatement getAllStatement;
	PreparedStatement getEntityByIdStatement;
	PreparedStatement updateStatement;
	PreparedStatement createStatement;

	public AbstractDAO(String url, String username, String password) {
		URL = url;
		USERNAME = username;
		PASSWORD = password;
	}

	public abstract List<E> getAll();
	public abstract E getEntityById(int id);
	public abstract E update(E entity);
	public abstract boolean create(E entity);
}
