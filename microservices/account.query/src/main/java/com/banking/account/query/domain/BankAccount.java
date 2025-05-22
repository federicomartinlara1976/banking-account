package com.banking.account.query.domain;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "bank_account")
public class BankAccount extends BaseEntity {

    @Id
    private String id;

    private String accountHolder;

    private Date creationDate;

    private AccountType accountType;

    private Double balance;
}
