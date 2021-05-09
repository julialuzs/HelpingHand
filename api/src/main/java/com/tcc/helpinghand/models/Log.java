package com.tcc.helpinghand.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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
