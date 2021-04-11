package com.tcc.helpinghand.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLog;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    private Date logDate;
}
