package org.example;

import javax.persistence.*;

@Entity
@Table(name = "progress")
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "fk_student"))
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false, foreignKey = @ForeignKey(name = "fk_subject"))
    private Subject subject;

    @Column(name = "grade", nullable = false)
    private int grade;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}