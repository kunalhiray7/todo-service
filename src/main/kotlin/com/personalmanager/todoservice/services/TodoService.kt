package com.personalmanager.todoservice.services

import com.personalmanager.todoservice.dtos.TaskRequest
import com.personalmanager.todoservice.repositories.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository) {

    fun create(taskRequest: TaskRequest) = todoRepository.save(taskRequest.toDomain())

    fun getForUser(userId: Long) = todoRepository.findByUserId(userId)

}
