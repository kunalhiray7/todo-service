package com.personalmanager.todoservice.controllers

import com.personalmanager.todoservice.controllers.MessageConverter.jacksonDateTimeConverter
import com.personalmanager.todoservice.domain.Task
import com.personalmanager.todoservice.domain.TodoStatus
import com.personalmanager.todoservice.dtos.TaskRequest
import com.personalmanager.todoservice.services.TodoService
import com.personalmanager.todoservice.utils.ObjectMapperUtil.getObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.ZoneOffset.UTC
import java.time.ZonedDateTime

@SpringBootTest
class TodoControllerTest {

    private val mapper = getObjectMapper()

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var todoService: TodoService

    @InjectMocks
    private lateinit var todoController: TodoController

    @BeforeEach
    internal fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).setMessageConverters(jacksonDateTimeConverter()).build()
    }

    @Test
    fun `POST should save a todo task for the user`() {
        val taskRequest = TaskRequest(
                title = "Pay phone bill",
                description = "Check and pay monthly phone bill",
                userId = 123L
        )
        val task = taskRequest.toDomain().copy(id = 234L)
        doReturn(task).`when`(todoService).create(taskRequest)

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated)
                .andExpect(content().string(mapper.writeValueAsString(task)))

        verify(todoService, times(1)).create(taskRequest)
    }

    @Test
    fun `POST should return BAD_REQUEST when task request is not valid`() {
        val taskRequest = TaskRequest(
                title = "",
                description = "Check and pay monthly phone bill",
                userId = 123L
        )

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskRequest)))
                .andExpect(status().isBadRequest)

        verifyNoMoreInteractions(todoService)
    }

    @Test
    fun `GET should return all the todos for the given user`() {
        // given
        val userId = 123L

        val todos = listOf(Task(
                id = 1234L,
                title = "pay bill",
                description = "pay phone bill",
                userId = userId,
                status = TodoStatus.PENDING,
                createdAt = ZonedDateTime.now(UTC)
        ),
                Task(
                        id = 1235L,
                        title = "learn Go",
                        description = "watch videos and perform exercise",
                        userId = userId,
                        status = TodoStatus.PENDING,
                        createdAt = ZonedDateTime.now(UTC).plusDays(1)
                ))
        doReturn(todos).`when`(todoService).getForUser(userId)

        // when
        mockMvc.perform(get("/api/todos/users/$userId"))
                .andExpect(status().isOk)
                .andExpect(content().string(mapper.writeValueAsString(todos)))

        verify(todoService, times(1)).getForUser(userId)
    }

}
