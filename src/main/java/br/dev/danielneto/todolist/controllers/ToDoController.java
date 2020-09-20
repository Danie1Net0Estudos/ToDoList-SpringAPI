package br.dev.danielneto.todolist.controllers;

import br.dev.danielneto.todolist.models.ToDo;
import br.dev.danielneto.todolist.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/todos")
@CrossOrigin("*")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("")
    public @ResponseBody Iterable<ToDo> index() {
        return toDoRepository.findAll();
    }

    @PostMapping("")
    public @ResponseBody ResponseEntity<ToDo> store(@Valid @RequestBody ToDo toDo) {
        toDoRepository.save(toDo);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDo);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<ToDo> show(@PathVariable(name = "id") long id) {
        return toDoRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<ToDo> update(@PathVariable(name = "id") long id, @RequestBody ToDo ToDo) {
        Optional<ToDo> oldToDo = toDoRepository.findById(id);

        if (oldToDo.isPresent()) {
            oldToDo.get().setDescription(ToDo.getDescription());
            oldToDo.get().setComplete(ToDo.isComplete());

            return ResponseEntity.ok(toDoRepository.save(oldToDo.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<ToDo> delete(@PathVariable(name = "id") long id) {
        Optional<ToDo> ToDo = toDoRepository.findById(id);

        if (ToDo.isPresent()) {
            toDoRepository.delete(ToDo.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
