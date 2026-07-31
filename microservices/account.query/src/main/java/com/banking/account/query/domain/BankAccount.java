package com.banking.account.query.domain;

import java.util.Date;

import com.banking.account.common.dto.AccountType;
import com.banking.cqrs.core.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "bank_account")
@ToString
public class BankAccount extends BaseEntity {

    @Id
    private String id;

    @Getter
    @Setter
    private String accountHolder;

    @Getter
    @Setter
    private Date creationDate;

    @Getter
    @Setter
    private AccountType accountType;

    @Getter
    @Setter
    private Double balance;
}
