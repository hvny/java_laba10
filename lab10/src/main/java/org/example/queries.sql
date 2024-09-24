-- Получить студентов, сдавших предмет на оценку выше 3
SELECT round(AVG(grade), 1) AS average_grade FROM progress WHERE subject_id = 5;

-- Посчитать средний балл по предмету
SELECT round(AVG(grade), 1) AS average_grade FROM progress WHERE subject_id = 5;

-- Посчитать средний балл по студенту
SELECT round(AVG(grade), 1) AS average_grade FROM progress WHERE student_id = 7;

-- Три предмета, которые сдали наибольшее количество студентов
SELECT s.subject_name, COUNT(p.student_id) AS student_count FROM subjects s JOIN progress p ON s.id = p.subject_id GROUP BY s.subject_name ORDER BY student_count DESC LIMIT 3;