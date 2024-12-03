package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {
    private ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDoItem addToDoItem(String description) {
        ToDoItem newItem = new ToDoItem(description);
        return toDoRepository.save(newItem);
    }

    public List<ToDoItem> getAllToDoItems() {
        return toDoRepository.findAll();
    }

    public Optional<ToDoItem> getToDoItemById(Long id) {
        return toDoRepository.findById(id);
    }

    public ToDoItem updateToDoItem(Long id, String newDescriprion, boolean done) {
        Optional<ToDoItem> existingItem = toDoRepository.findById(id);
        if (existingItem.isPresent()){
            ToDoItem item = existingItem.get();
            item.setDescription(newDescriprion);
            item.setDone(done);
            return toDoRepository.save(item);
        }
        return null;
    }

    public void deleteToDoItem(Long id) {
        toDoRepository.deleteById(id);
    }
}
