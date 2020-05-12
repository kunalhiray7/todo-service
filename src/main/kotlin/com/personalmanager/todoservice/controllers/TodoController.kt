package com.personalmanager.todoservice.controllers

import com.personalmanager.todoservice.dtos.TaskRequest
import com.personalmanager.todoservice.services.TodoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.websocket.server.PathParam

@RestController
@RequestMapping
class TodoController(private val todoService: TodoService) {

    @PostMapping("/api/todos")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid taskRequest: TaskRequest) = todoService.create(taskRequest)

    @GetMapping("/api/todos/users/{userId}")
    fun getForUser(@PathVariable("userId") userId: Long) = todoService.getForUser(userId)

    @PutMapping("/api/todos/{todoId}/star")
    fun markStar(@PathVariable("todoId") todoId: Long) = todoService.markStar(todoId)
}
