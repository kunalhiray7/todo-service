package com.personalmanager.todoservice.services

import com.personalmanager.todoservice.dtos.TaskRequest
import com.personalmanager.todoservice.repositories.TodoRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

@ExtendWith(MockitoExtension::class)
class TodoServiceTest {

    @Mock
    private lateinit var todoRepository: TodoRepository

    @InjectMocks
    private lateinit var todoService: TodoService

    @BeforeEach
    internal fun setUp() {
        ZonedDateTime.now(Clock.fixed(
                Instant.parse("2018-08-22T10:00:00Z"),
                ZoneOffset.UTC))
    }

    @Test
    fun `create() should save the task when its valid`() {
        val taskRequest = TaskRequest(
                title = "Pay phone bill",
                description = "Check and pay monthly phone bill",
                userId = 123L,
                createdAt = ZonedDateTime.now()
        )
        val task = taskRequest.toDomain()
        val savedTaskId = 234L
        val savedTask = task.copy(id = savedTaskId)
        doReturn(savedTask).`when`(todoRepository).save(task)

        val result = todoService.create(taskRequest)

        assertEquals(savedTask, result)
        verify(todoRepository, times(1)).save(task)
    }
}
