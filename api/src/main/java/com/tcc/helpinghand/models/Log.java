package com.tcc.helpinghand.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Log {

    public Log(User user, LocalDateTime logDate) {
        this.user = user;
        this.logDate = logDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLog;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    private LocalDateTime logDate;

}
