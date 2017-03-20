package ua.rozhkov.QueryExecuter;

import java.sql.*;
import java.util.Scanner;

public class Starter {
	
	public static void main(String args[]) {
		Starter starter = new Starter();
		Statement statement;
		try {
			statement = starter.connect().createStatement();
			Scanner scanner = new Scanner(System.in);
			while (!scanner.hasNext("exit")) {
				String input = scanner.nextLine();
				if (statement.execute(input)) {
					starter.print(statement.getResultSet());
				} else {
					if (statement.getUpdateCount() != -1) {
						System.out.println("Updated " + statement.getUpdateCount() + (statement.getUpdateCount()>1 ? "rows.": "row."));
					}else{
						System.out.println(input);
					}
				}
			}
			statement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void print(ResultSet resultSet) {
		System.out.printf("%3s", "");
		try {
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				System.out.printf("%15s", resultSet.getMetaData().getColumnName(i));
			}
			while (resultSet.next()) {
				System.out.println();
				System.out.printf("%3d", resultSet.getRow());
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					System.out.printf("%15s", resultSet.getString(resultSet.getMetaData().getColumnName(i)));
				}
			}
			System.out.println();
			resultSet.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Connection connect() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "PsyhoBelka", "admin");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}

