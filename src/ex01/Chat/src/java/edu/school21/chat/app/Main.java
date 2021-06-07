package edu.school21.chat.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	private static final String DB_URL = "jdbc:postgresql://localhost/postgres";
	private static final String DB_USER = "postgres";
	private static final String DB_PWD = "";
	private static final String DB_SCHEMA = "/resources/schema.sql";
	private static final String DB_DATA = "/resources/data.sql";

	public static void main(String[] args) throws FileNotFoundException {
		Connection connection = connect();
		System.out.println("Creating tables...");
		runQueriesFromFile(connection, DB_SCHEMA);
		System.out.println("Tables created!");
		System.out.println("Populating tables!");
		runQueriesFromFile(connection, DB_DATA);
		System.out.println("Data inserted");
	}

	public static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public static void runQueriesFromFile(Connection connection, String filename) throws FileNotFoundException {
		Scanner scanner = new Scanner(
				new File(System.getProperty("user.dir") + "/src/ex01/Chat/src/main" + filename))
				.useDelimiter(";");
		try {
			while (scanner.hasNext()) {
				connection.createStatement().execute(scanner.next().trim());
			}
		} catch (SQLException throwables) {
			System.out.println(throwables.getMessage());
		}
		scanner.close();
	}
}
