package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ChatroomRepositoryJdbcImpl implements ChatroomRepository {
	private final Connection dataSource;
	private final UserRepositoryJdbcImpl userRepository;
	private final String QUERY_TEMPLATE = "SELECT * FROM chat.rooms WHERE id=?";

	public ChatroomRepositoryJdbcImpl(Connection dataSource, UserRepositoryJdbcImpl ur) {
		this.dataSource = dataSource;
		this.userRepository = ur;
	}

	@Override
	public Optional<Chatroom> findById(Long id) {
		Chatroom ret = null;
		ResultSet resultSet = null;

		try {
			PreparedStatement query = dataSource.prepareStatement(QUERY_TEMPLATE);
			query.setLong(1, id);
			resultSet = query.executeQuery();
			resultSet.next();
			ret = new Chatroom(
					resultSet.getLong("id"),
					resultSet.getString("name"),
					userRepository.findById(resultSet.getLong("owner")).orElse(null),
					new ArrayList<>()
			);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return (Optional.of(ret));
	}
}
