package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.ChatroomRepositoryJdbcImpl;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.UserRepositoryJdbcImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
	private static final String DB_URL = "jdbc:postgresql://localhost/postgres";
	private static final String DB_USER = "postgres";
	private static final String DB_PWD = "";
	private static final String DB_SCHEMA = "/resources/schema.sql";
	private static final String DB_DATA = "/resources/data.sql";

	public static void main(String[] args) throws FileNotFoundException, SQLException {
		Connection connection = connect();
		runInit(connection);

		UserRepositoryJdbcImpl userRep = new UserRepositoryJdbcImpl(connection);
		ChatroomRepositoryJdbcImpl chatRep = new ChatroomRepositoryJdbcImpl(connection, userRep);
		MessagesRepository msgRep = new MessagesRepositoryJdbcImpl(connection, userRep, chatRep);

		User creator = new User(2L, "user", "user", new ArrayList<>(), new ArrayList<>());
		Chatroom room = new Chatroom(3L, "room", creator, new ArrayList<>());
		Optional<Message> messageOptional = msgRep.findById(5L);
		if (messageOptional.isPresent()) {
			Message message = messageOptional.get();
			message.setText("STUFF");
			message.setMessageDateTime(null);
			msgRep.update(message);
		}
	}

	private static void runInit(Connection connection) throws FileNotFoundException {
		runQueriesFromFile(connection, DB_SCHEMA);
		runQueriesFromFile(connection, DB_DATA);
	}

	private static Connection connect() {
		Connection conn = null;
		try {
			conn = HikariConnect().getConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	private static void runQueriesFromFile(Connection connection, String filename) throws FileNotFoundException {
		Scanner scanner = new Scanner(
				new File(System.getProperty("user.dir") + "/src/ex03/Chat/src/" + filename))
				.useDelimiter(";");
		try {
			while (scanner.hasNext()) {
				connection.createStatement().execute(scanner.next().trim());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		scanner.close();
	}

	private static HikariDataSource HikariConnect() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(DB_URL);
		config.setUsername(DB_USER);
		config.setPassword(DB_PWD);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		return (new HikariDataSource(config));
	}
}
