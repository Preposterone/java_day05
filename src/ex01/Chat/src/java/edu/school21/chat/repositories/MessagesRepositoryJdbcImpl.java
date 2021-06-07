package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
	private final HikariDataSource dataSource;
	private final String QUERY_TEMPLATE = "SELECT ";

	public MessagesRepositoryJdbcImpl(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Optional<Message> findById(Long id) {
		Message ret = null;
		ResultSet resultSet = null;

		try {
			resultSet = dataSource.getConnection().prepareStatement("").executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		resultSet.next()
		return (Optional.of(ret));
	}
}
