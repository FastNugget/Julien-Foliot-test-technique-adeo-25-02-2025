package com.yaourt.service.yaourt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class YaourtDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

}
