package com.example.ToDo.App.Controller;

import com.example.ToDo.App.Model.ToDo;
import com.example.ToDo.App.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping({"/","viewToDoList"})
    public String viewAllToDoItems(Model model, @ModelAttribute("message") String message){
    model.addAttribute("list",todoService.getAllToDoItems());
    model.addAttribute("msg",message);
    return "ViewToDoList";
    }

    @GetMapping("/updateToDoStatus/{id}")
    public String updateToDoStatus(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if(todoService.updateStatus(id))
        {
            redirectAttributes.addFlashAttribute("message","Update Success");
            return "redirect:/viewToDoList";
        }

        redirectAttributes.addFlashAttribute("message","Update Failure");
        return "redirect:/viewToDoList";
    }

    @GetMapping("/addToDoItem")
    public String addToDoItem(Model model){
        model.addAttribute("todo",new ToDo());
        return "AddToDoItem";
    }

    @PostMapping("/saveToDoItem")
    public String saveToDoItem(ToDo todo,RedirectAttributes redirectAttributes){
        if(todoService.saveOrUpdateToDoItem(todo)){
            redirectAttributes.addFlashAttribute("message","Save Success");
            return "redirect:/viewToDoList";
        }
        redirectAttributes.addFlashAttribute("message","Save Failure");
        return "redirect:/addToDoItem";
    }

    @GetMapping("/editToDoItem/{id}")
    public String editToDoItem(@PathVariable Long id,Model model){
        model.addAttribute("todo",todoService.getToDoItemById(id));
        return "EditToDoItem";
    }

    @PostMapping("/editSaveToDoItem")
    public String editSaveToDoItem(ToDo todo, RedirectAttributes redirectAttributes){
        if(todoService.saveOrUpdateToDoItem(todo))
        {
            redirectAttributes.addFlashAttribute("message","Edit Success");
            return "redirect:/viewToDoList";
        }
        redirectAttributes.addFlashAttribute("message","Edit Failure");
        return "redirect:/editToDoItem/"+todo.getId();
    }

    @GetMapping("/deleteToDoItem/{id}")
    public String deleteToDoItem(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if(todoService.deleteToDoItem(id))
        {
            redirectAttributes.addFlashAttribute("message","Delete Success");
            return "redirect:/viewToDoList";
        }

        redirectAttributes.addFlashAttribute("message","Delete Failure");
        return "redirect:/viewToDoList";

    }
}
