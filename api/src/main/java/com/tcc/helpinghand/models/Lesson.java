package com.tcc.helpinghand.models;

import com.tcc.helpinghand.converters.DifficultyConverter;
import com.tcc.helpinghand.converters.StatusConverter;
import com.tcc.helpinghand.enums.Difficulty;
import com.tcc.helpinghand.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLesson;

    private long points;

    @Column(length = 15, nullable = false)
    @Convert(converter = DifficultyConverter.class)
    private Difficulty difficulty;

    private String module;

    @Transient
    private Status status;

    private String imageName;

}
