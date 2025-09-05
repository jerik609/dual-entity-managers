package com.example.dual_entity_managers.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class First {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String data;
    String extended_data;
    LocalDateTime modified;

    public void setData(String data) {
        this.data = data;
    }

    public void setExtended_data(String extended_data) {
        this.extended_data = extended_data;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
}
