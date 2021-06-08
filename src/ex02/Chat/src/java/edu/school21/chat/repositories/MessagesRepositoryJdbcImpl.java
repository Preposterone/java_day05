package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
	private final Connection dataSource;
	UserRepositoryJdbcImpl userRepository;
	ChatroomRepositoryJdbcImpl chatroomRepository;

	public MessagesRepositoryJdbcImpl(Connection dataSource, UserRepositoryJdbcImpl ur, ChatroomRepositoryJdbcImpl cr) {
		this.dataSource = dataSource;
		this.userRepository = ur;
		this.chatroomRepository = cr;
	}

	@Override
	public Optional<Message> findById(Long id) {
		final String QUERY_TEMPLATE = "SELECT * FROM chat.messages WHERE id=?";
		Message ret = null;
		ResultSet resultSet = null;

		try {
			PreparedStatement query = dataSource.prepareStatement(QUERY_TEMPLATE);
			query.setLong(1, id);
			resultSet = query.executeQuery();
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

	@Override
	public void save(Message message) {
		final String QUERY_TEMPLATE = "INSERT INTO chat.messages (author, room, text, timestamp) VALUES (?, ?, ?, ?) RETURNING *";

		ResultSet resultSet = null;
		try {
			PreparedStatement query = dataSource.prepareStatement(QUERY_TEMPLATE);
			query.setLong(1, message.getAuthor().getId());
			query.setLong(2, message.getChatroom().getId());
			query.setString(3, message.getText());
			query.setTimestamp(4, Timestamp.valueOf(message.getMessageDateTime()));
			resultSet = query.executeQuery();
			resultSet.next();
			message.setId(resultSet.getLong("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
