package com.example.test.service.abstracts;

import com.example.test.model.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountDto findById(Long id);

    List<AccountDto> findAllAccounts();

    void createAccount(String beneficiary, Integer pin);

    void deposit(AccountDto account, BigDecimal income);

    void withdraw(AccountDto account, BigDecimal outcome);

    void transfer(AccountDto from, AccountDto to, BigDecimal amount);
}
