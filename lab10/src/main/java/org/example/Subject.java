package org.example;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}