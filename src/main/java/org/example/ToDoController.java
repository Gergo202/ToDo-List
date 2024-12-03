package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {
    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public List<ToDoItem> getAllToDoItems() {
        return toDoService.getAllToDoItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoItem> getToDoItemById(@PathVariable Long id) {
        Optional<ToDoItem> toDoItem = toDoService.getToDoItemById(id);
        return toDoItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoItem> updateToDoItem(
            @PathVariable Long id,
            @RequestBody ToDoItem updateItem ) {
        ToDoItem update = toDoService.updateToDoItem(id, updateItem.getDescription(), updateItem.isDone());
        if (update != null) {
            return ResponseEntity.ok(update);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToDoItem(@PathVariable Long id) {
        toDoService.deleteToDoItem(id);
        return ResponseEntity.noContent().build();
    }
}
