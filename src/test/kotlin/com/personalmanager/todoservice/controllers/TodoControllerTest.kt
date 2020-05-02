package com.personalmanager.todoservice.controllers

import com.personalmanager.todoservice.controllers.MessageConverter.jacksonDateTimeConverter
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

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
}
