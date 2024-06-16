package com.banking.account.cmd.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.cmd.api.command.WithdrawFundsCommand;
import com.banking.account.common.dto.BaseResponse;
import com.banking.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping(path = "/api/v1/withdrawFunds")
public class WithdrawFundsController {

    @Autowired
    private CommandDispatcher commandDispatcher;
    
    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value="id") String id, @RequestBody WithdrawFundsCommand command) {
        command.setId(id);

        commandDispatcher.send(command);
        return new ResponseEntity<>(new BaseResponse("Dinero retirado"), HttpStatus.OK);
    }
}
