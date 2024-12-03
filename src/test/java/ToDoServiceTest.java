import org.example.ToDoItem;
import org.example.ToDoRepository;
import org.example.ToDoService;
import org.junit.jupiter.api.Test;

import java.util.List;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ToDoServiceTest {
    private final ToDoRepository toDoRepository = mock(ToDoRepository.class);
    private final ToDoService toDoService = new ToDoService(toDoRepository);

    @Test
    void getAllToDoItems() {
        ToDoItem item1 = new ToDoItem("Első példa");
        ToDoItem item2 = new ToDoItem("Második példa");
        when(toDoRepository.findAll()).thenReturn(List.of(item1, item2));

        List<ToDoItem> result = toDoService.getAllToDoItems();

        assertEquals(2, result.size());
        verify(toDoRepository, times(1)).findAll();
    }

    @Test
    void getToDoItemById() {
        ToDoItem item = new ToDoItem("Példa 1");
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(item));

        Optional<ToDoItem> result = toDoService.getToDoItemById(1L);

        assertTrue(result.isPresent());
        assertEquals("Példa 1", result.get().getDescription());
    }

    @Test
    void updateToDoItem() {
        ToDoItem existingItem = new ToDoItem("Példa 1");
        existingItem.setId(1L);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(existingItem));

        ToDoItem updateItem = new ToDoItem("Frissített példa");
        updateItem.setId(1L);
        updateItem.setDone(true);
        when(toDoRepository.save(any(ToDoItem.class))).thenReturn(updateItem);

        ToDoItem result = toDoService.updateToDoItem(1L, "Frissített példa", true);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Frissített példa", result.getDescription());
        assertTrue(result.isDone());

        verify(toDoRepository, times(1)).findById(1L);
        verify(toDoRepository, times(1)).save(any(ToDoItem.class));
    }

    @Test
    void deleteToDoItem() {
        doNothing().when(toDoRepository).deleteById(1L);

        toDoService.deleteToDoItem(1L);

        verify(toDoRepository, times(1)).deleteById(1L);
    }
}
