package com.banking.account.cmd.api.dto;

import com.banking.account.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAccountResponse extends BaseResponse {

    private String id;

    public OpenAccountResponse(String id, String message) {
        super(message);
        this.id = id;
    }
}
