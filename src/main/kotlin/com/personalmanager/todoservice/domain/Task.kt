package com.personalmanager.todoservice.domain

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Task(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @get: NotBlank
        val title: String = "",

        val description: String = "",

        @get: NotNull
        val userId: Long = -1L,

        @get: NotNull
        val status: TodoStatus = TodoStatus.PENDING,

        @get: NotNull
        val createdAt: ZonedDateTime = ZonedDateTime.now()
)
