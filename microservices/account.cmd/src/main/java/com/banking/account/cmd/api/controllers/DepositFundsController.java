package com.banking.account.cmd.api.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.cmd.api.command.DepositFundsCommand;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.SneakyThrows;

@RestController
@RequestMapping(path = "/account-cmd/api/depositFunds")
public class DepositFundsController {

    @Autowired
    private CommandDispatcher commandDispatcher;
    
    @Autowired
    private Validator validator;

    @PutMapping(path = "/{id}")
    @SneakyThrows(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id, @RequestBody DepositFundsCommand command) {
    	Set<ConstraintViolation<DepositFundsCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(violations.iterator().next().getMessage());
        }
    	
        command.setId(id);

        commandDispatcher.send(command);
        return new ResponseEntity<>(new BaseResponse("Operación enviada"), HttpStatus.OK);
    }
}
