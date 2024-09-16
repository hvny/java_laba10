package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Subject.class);
        configuration.addAnnotatedClass(Progress.class);
        // Создание фабрики сессий
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Создание данных в таблицах
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Добавление студентов
            Student student1 = new Student();
            student1.setName("John Doe");
            student1.setPassportSeries("AB");
            student1.setPassportNumber("123456");

            Student student2 = new Student();
            student2.setName("Jane Doe");
            student2.setPassportSeries("CD");
            student2.setPassportNumber("654321");

            // Добавление предметов
            Subject subject1 = new Subject();
            subject1.setSubjectName("Mathematics");

            Subject subject2 = new Subject();
            subject2.setSubjectName("Physics");

            // Добавление успеваемости
            Progress progress1 = new Progress();
            progress1.setStudent(student1);
            progress1.setSubject(subject1);
            progress1.setGrade(4);

            Progress progress2 = new Progress();
            progress2.setStudent(student2);
            progress2.setSubject(subject1);
            progress2.setGrade(5);

            Progress progress3 = new Progress();
            progress3.setStudent(student1);
            progress3.setSubject(subject2);
            progress3.setGrade(3);

            // Сохранение объектов
            session.save(student1);
            session.save(student2);
            session.save(subject1);
            session.save(subject2);
            session.save(progress1);
            session.save(progress2);
            session.save(progress3);

            transaction.commit();
        }

        // Выполнение SQL-запросов
        try (Session session = sessionFactory.openSession()) {
            // 1. Список студентов, сдавших определенный предмет на оценку выше 3
            List<String> studentsWithGoodGrades = session.createSQLQuery(
                            "SELECT s.name FROM students s JOIN progress p ON s.id = p.student_id WHERE p.subject_id = 1 AND p.grade > 3")
                    .list();
            System.out.println("Студенты с оценкой выше 3 по предмету:");
            studentsWithGoodGrades.forEach(System.out::println);

            // 2. Средний балл по определенному предмету
            Double avgGrade = (Double) session.createSQLQuery(
                            "SELECT AVG(grade) AS average_grade FROM progress WHERE subject_id = 1")
                    .uniqueResult();
            System.out.println("Средний балл по предмету: " + avgGrade);

            // 3. Средний балл по определенному студенту
            Double studentAvgGrade = (Double) session.createSQLQuery(
                            "SELECT AVG(grade) AS average_grade FROM progress WHERE student_id = 1")
                    .uniqueResult();
            System.out.println("Средний балл студента: " + studentAvgGrade);

            // 4. Три предмета, которые сдали наибольшее количество студентов
            List<Object[]> topSubjects = session.createSQLQuery(
                            "SELECT s.subject_name, COUNT(p.student_id) AS student_count FROM subjects s " +
                                    "JOIN progress p ON s.id = p.subject_id " +
                                    "GROUP BY s.subject_name ORDER BY student_count DESC LIMIT 3")
                    .list();
            System.out.println("Топ 3 предмета по количеству студентов:");
            topSubjects.forEach(row -> System.out.println(row[0] + ": " + row[1] + " студентов"));
        }

        // Закрытие фабрики сессий
        sessionFactory.close();
    }
}