package com.personalmanager.todoservice.controllers

import com.personalmanager.todoservice.utils.ObjectMapperUtil
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

object MessageConverter {

    fun jacksonDateTimeConverter(): MappingJackson2HttpMessageConverter {
        val converter = MappingJackson2HttpMessageConverter()
        converter.objectMapper = ObjectMapperUtil.getObjectMapper()
        return converter
    }

}
