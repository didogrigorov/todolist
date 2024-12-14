package org.example.todolist.service;
import org.example.todolist.model.Todo;
import org.example.todolist.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return repository.findById(id);
    }

    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Todo toggleCompletion(Long id) {
        return repository.findById(id)
                .map(todo -> {
                    todo.setCompleted(!todo.isCompleted()); // Toggle the completion status
                    return repository.save(todo); // Save the updated task
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
    }
}
