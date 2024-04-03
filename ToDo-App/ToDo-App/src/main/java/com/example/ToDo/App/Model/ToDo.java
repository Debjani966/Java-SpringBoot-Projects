package com.example.ToDo.App.Model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "todo")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @Nonnull
    private Long id;
    @Column
    @Nonnull
    private String title;
    @Column
    private String date;
    @Column
    @Nonnull
    private String status;

    public ToDo() {
    }

    public ToDo(Long id, String title, String date, String status) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
