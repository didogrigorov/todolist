package org.example.todolist.controller;
import org.example.todolist.model.Todo;
import org.example.todolist.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public String listTodos(Model model) {
        model.addAttribute("todos", service.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String addTodoForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "add-todo";
    }

    @PostMapping
    public String saveTodo(@ModelAttribute Todo todo) {
        service.save(todo);
        return "redirect:/todos";
    }

    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable Long id, Model model) {
        model.addAttribute("todo", service.findById(id).orElseThrow());
        return "edit-todo";
    }

    @PostMapping("/{id}")
    public String updateTodo(@PathVariable Long id, @ModelAttribute Todo todo) {
        todo.setId(id);
        service.save(todo);
        return "redirect:/todos";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/todos";
    }

    @PostMapping("/toggle/{id}")
    public String toggleCompletion(@PathVariable Long id) {
        Todo todo = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
        todo.setCompleted(!todo.isCompleted()); // Toggle the completion status
        service.save(todo);
        return "redirect:/todos";
    }
}
