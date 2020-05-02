package com.personalmanager.todoservice.repositories

import com.personalmanager.todoservice.domain.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: JpaRepository<Task, Long>
