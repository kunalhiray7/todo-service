package com.personalmanager.todoservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.personalmanager.todoservice.utils.ObjectMapperUtil.getObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@SpringBootApplication
class TodoServiceApplication {

	@Bean
	@Primary
	fun serializingObjectMapper(): ObjectMapper {
		return getObjectMapper()
	}

}

fun main(args: Array<String>) {
	runApplication<TodoServiceApplication>(*args)
}
