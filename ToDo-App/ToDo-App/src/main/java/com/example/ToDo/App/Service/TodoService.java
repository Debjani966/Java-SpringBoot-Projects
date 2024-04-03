package com.example.ToDo.App.Service;

import com.example.ToDo.App.Model.ToDo;
import com.example.ToDo.App.Repository.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    @Autowired
    ToDoRepo toDoRepo;

    public List<ToDo> getAllToDoItems() {
        ArrayList<ToDo> toDoList=new ArrayList<>();
        toDoRepo.findAll().forEach(toDo -> toDoList.add(toDo));
        return toDoList;
    }

    public ToDo getToDoItemById(Long id) {
        return toDoRepo.findById(id).get();
    }

    public boolean updateStatus(Long id) {
        ToDo toDo=getToDoItemById(id);
        toDo.setStatus("Completed");
        return saveOrUpdateToDoItem(toDo);
    }

    public boolean saveOrUpdateToDoItem(ToDo toDo) {
        ToDo toDo1=toDoRepo.save(toDo);
        if(getToDoItemById(toDo1.getId())!=null) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteToDoItem(Long id) {
        if(getToDoItemById(id)!=null) {
            toDoRepo.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }
}
