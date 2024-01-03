package com.example.demo.Exceptions;

import com.example.demo.Exceptions.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DataNotFoundAdvice {

    /**
     * Render not found error into response body
     * @param ex not found exception
     * @return  not found exception response body
     */
    @ResponseBody
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(DataNotFoundException ex) {
        return ex.getMessage();
    }
}