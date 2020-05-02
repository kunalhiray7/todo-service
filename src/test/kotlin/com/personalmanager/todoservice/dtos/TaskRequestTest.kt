package com.personalmanager.todoservice.dtos

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.Validator

class TaskRequestTest {

    private lateinit var validator: Validator

    private val taskRequest = TaskRequest(
            title = "Pay phone bill",
            description = "Check and pay monthly phone bill",
            userId = 123L
    )

    @BeforeEach
    internal fun setUp() {
        val validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.validator
    }

    @Test
    fun `should not return any validation error if request is valid`() {
        val validations = validator.validate(taskRequest)

        assertEquals(0, validations.size)
    }

    @Test
    fun `should have validation error if title is bank`() {
        val request = taskRequest.copy(title = "")

        val validations = validator.validate(request)

        assertEquals(1, validations.size)
    }

    @Test
    fun `should have validation error if description is bank`() {
        val request = taskRequest.copy(description = "")

        val validations = validator.validate(request)

        assertEquals(1, validations.size)
    }

}
