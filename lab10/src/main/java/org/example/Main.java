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

    private static final String URL = "jdbc:postgresql://localhost:5432/java_bd10";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8631";

    public static void main(String[] args) {

        // Конфигурируем Hibernate
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Устанавливаем соединение через JDBC
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Введите SQL-скрипт для выполнения:");

            while (true) {
                System.out.print("> ");
                String sql = scanner.nextLine();

                // Выполнение SQL-скрипта
                try (Statement statement = connection.createStatement()) {
                    boolean hasResultSet = statement.execute(sql);

                    if (hasResultSet) {
                        // Если запрос возвращает результат (например, SELECT)
                        ResultSet resultSet = statement.getResultSet();
                        int columnCount = resultSet.getMetaData().getColumnCount();

                        // Выводим результаты
                        while (resultSet.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(resultSet.getString(i) + "\t");
                            }
                            System.out.println();
                        }
                    } else {
                        // Если это не SELECT, выводим количество обновлённых строк
                        int updateCount = statement.getUpdateCount();
                        System.out.println("Обновлено строк: " + updateCount);
                    }
                } catch (SQLException e) {
                    System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}