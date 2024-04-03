package com.example.ToDo.App.Repository;

import com.example.ToDo.App.Model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepo extends JpaRepository<ToDo,Long> {
}
