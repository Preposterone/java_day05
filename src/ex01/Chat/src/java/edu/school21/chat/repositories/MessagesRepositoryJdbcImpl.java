package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
	private final Connection dataSource;
	private final String QUERY_TEMPLATE = "SELECT * FROM chat.messages WHERE id=?";
	UserRepositoryJdbcImpl userRepository;
	ChatroomRepositoryJdbcImpl chatroomRepository;

	public MessagesRepositoryJdbcImpl(Connection dataSource, UserRepositoryJdbcImpl ur, ChatroomRepositoryJdbcImpl cr) {
		this.dataSource = dataSource;
		this.userRepository = ur;
		this.chatroomRepository = cr;
	}

	@Override
	public Optional<Message> findById(Long id) {
		Message ret = null;
		ResultSet resultSet = null;

		try {
			PreparedStatement query = dataSource.prepareStatement(QUERY_TEMPLATE);
			query.setLong(1, id);
			resultSet = query.executeQuery();
			resultSet.next();
			if (resultSet.next()) {
				ret = new Message(
						resultSet.getLong("id"),
						userRepository.findById(resultSet.getLong("author")).orElse(null),
						chatroomRepository.findById(resultSet.getLong("room")).orElse(null),
						resultSet.getString("text"),
						resultSet.getTimestamp("timestamp").toLocalDateTime()
				);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return (Optional.ofNullable(ret));
	}
}
