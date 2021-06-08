package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {
	private final Connection dataSource;
	private final String QUERY_TEMPLATE = "SELECT * FROM chat.users WHERE id=?";

	public UserRepositoryJdbcImpl(Connection dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<User> findById(Long id) {
		User ret = null;
		ResultSet resultSet = null;

		try {
			PreparedStatement query = dataSource.prepareStatement(QUERY_TEMPLATE);
			query.setLong(1, id);
			resultSet = query.executeQuery();
			resultSet.next();
			ret = new User(
					resultSet.getLong("id"),
					resultSet.getString("login"),
					resultSet.getString("password"),
					new ArrayList<>(),
					new ArrayList<>()
			);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return (Optional.of(ret));
	}
}
