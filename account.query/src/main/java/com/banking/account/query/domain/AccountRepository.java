package com.banking.account.query.domain;

import com.banking.cqrs.core.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountHolder(String accountHolder);

    List<BaseEntity> findBalanceGreaterThan(Double balance);

    List<BaseEntity> findBalanceLessThan(Double balance);
}
