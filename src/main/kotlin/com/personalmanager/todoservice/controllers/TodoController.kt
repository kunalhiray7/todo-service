package com.personalmanager.todoservice.controllers

import com.personalmanager.todoservice.dtos.TaskRequest
import com.personalmanager.todoservice.services.TodoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid taskRequest: TaskRequest) = todoService.create(taskRequest)
}
