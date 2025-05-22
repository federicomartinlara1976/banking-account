package com.banking.account.cmd.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.cmd.api.command.CloseAccountCommand;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping(path = "/api/v1/closeBankAccount")
public class CloseAccountController {

    @Autowired
    private CommandDispatcher commandDispatcher;
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value="id") String id) {
    	CloseAccountCommand command = new CloseAccountCommand(id);

        commandDispatcher.send(command);
        return new ResponseEntity<>(new BaseResponse("Cuenta cancelada"), HttpStatus.OK);
    }
}
