package org.example;

import java.sql.*;

public class Main {
    static Connection connection;
    static Statement statement;

    public static void main(String[] args) throws SQLException {
        Connect();

        printStudentsPassedSubject();
        printAverageGradeForSubject();
        printAverageGradeForStudent();
        printTop3Subjects();

        Disconnect();
    }

    public static void printStudentsPassedSubject() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT s.name FROM students s JOIN progress p ON s.id = p.student_id WHERE p.subject_id = 7 AND p.grade > 3;");
        System.out.println("Студенты, сдавшие предмет на оценку выше 3:");
        while (rs.next()) {
            System.out.println("- " + rs.getString(1));  // Выводим имя студента
        }
        System.out.println();
    }

    public static void printAverageGradeForSubject() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT round(AVG(grade), 1) AS average_grade FROM progress WHERE subject_id = 5;");
        if (rs.next()) {
            System.out.println("Средний балл по предмету: " + rs.getDouble(1));
        }
        System.out.println();
    }

    public static void printAverageGradeForStudent() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT round(AVG(grade), 1) AS average_grade FROM progress WHERE student_id = 7;");
        if (rs.next()) {
            System.out.println("Средний балл студента: " + rs.getDouble(1));
        }
        System.out.println();
    }

    public static void printTop3Subjects() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT s.subject_name, COUNT(p.student_id) AS student_count FROM subjects s JOIN progress p ON s.id = p.subject_id GROUP BY s.subject_name ORDER BY student_count DESC LIMIT 3;");
        System.out.println("Три предмета, которые сдали наибольшее количество студентов:");
        while (rs.next()) {
            System.out.println("- Предмет: " + rs.getString(1) + ", Количество студентов: " + rs.getInt(2));
        }
        System.out.println();
    }

    // Подключение к БД
    public static void Connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/task",
                    "postgres",
                    "8631"
            );
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    // Отключение от БД
    public static void Disconnect() throws SQLException {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
