package com.banking.account.cmd.api.controllers;

import com.banking.account.common.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> illegalStateException(IllegalStateException ex) {
        log.error("ERROR: {}", ex.getMessage());

        BaseResponse baseResponse = new BaseResponse(ex.getMessage());

        return ResponseEntity.badRequest().body(baseResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse> exception(Exception ex) {
        log.error("ERROR: {}", ex.getMessage());

        BaseResponse baseResponse = new BaseResponse(ex.getMessage());

        return ResponseEntity.internalServerError().body(baseResponse);
    }
}
