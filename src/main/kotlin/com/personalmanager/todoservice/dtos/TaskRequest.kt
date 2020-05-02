package com.personalmanager.todoservice.dtos

import com.personalmanager.todoservice.domain.Task
import com.personalmanager.todoservice.domain.TodoStatus
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TaskRequest(

        @get: NotBlank
        val title: String,

        @get: NotBlank
        val description: String,

        @get: NotNull
        val userId: Long,

        val createdAt: ZonedDateTime? = ZonedDateTime.now(ZoneOffset.UTC)
) {
    fun toDomain(): Task = Task(
            title = title,
            description = description,
            userId = userId,
            status = TodoStatus.PENDING,
            createdAt = createdAt!!
    )
}
