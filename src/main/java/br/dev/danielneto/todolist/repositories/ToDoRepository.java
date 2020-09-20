package br.dev.danielneto.todolist.repositories;

import br.dev.danielneto.todolist.models.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, Long> {

}
