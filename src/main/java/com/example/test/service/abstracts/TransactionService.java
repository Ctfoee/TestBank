package com.example.test.service.abstracts;

import com.example.test.model.dto.TransactionDto;
import com.example.test.model.entity.Account;
import org.springframework.lang.Nullable;

import java.util.List;

public interface TransactionService {

    List<TransactionDto> findAllAccountTransactions(Long id);
    void save(TransactionDto transaction, @Nullable Account from, Account to);

    TransactionDto findById(Long id);
}
