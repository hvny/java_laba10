package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/task";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8631";
    private static Session session;

    public static void main(String[] args) {
        session = HibernateUtil.getSessionFactory().openSession();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.print("Введите скрипт: ");
                String sql = scanner.nextLine();

                // Выполнение SQL-скрипта
                try (Statement statement = connection.createStatement()) {
                    boolean hasResultSet = statement.execute(sql);

                    if (hasResultSet) {

                        ResultSet resultSet = statement.getResultSet();
                        int columnCount = resultSet.getMetaData().getColumnCount();

                        // Выводим результаты
                        while (resultSet.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(resultSet.getString(i) + "\t");
                            }
                            System.out.println();
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}