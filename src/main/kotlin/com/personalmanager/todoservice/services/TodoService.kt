package com.personalmanager.todoservice.services

import com.personalmanager.todoservice.domain.Task
import com.personalmanager.todoservice.dtos.TaskRequest
import com.personalmanager.todoservice.repositories.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository) {

    fun create(taskRequest: TaskRequest) = todoRepository.save(taskRequest.toDomain())

    fun getForUser(userId: Long) = todoRepository.findByUserId(userId)

    fun markStar(todoId: Long): Task {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
