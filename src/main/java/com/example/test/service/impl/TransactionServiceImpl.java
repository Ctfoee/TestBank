package com.example.test.service.impl;


import com.example.test.converters.TransactionConverter;
import com.example.test.model.dto.TransactionDto;
import com.example.test.model.entity.Account;
import com.example.test.model.entity.Transaction;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import com.example.test.repository.TransactionRepository;
import com.example.test.service.abstracts.TransactionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionConverter transactionConverter;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionConverter transactionConverter) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
    }


    @Override
    @Transactional
    public List<TransactionDto> findAllAccountTransactions(Long id) {
        return transactionRepository
                .findTransactionsByFromIdOrToId(id, id)
                .stream()
                .map(transactionConverter::transactionToTransactionDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(TransactionDto transactionDto, @Nullable Account from, @Nullable Account to) {
        Transaction transaction = transactionConverter.transactionDtoToTransaction(transactionDto);
        transaction.setFrom(from);
        transaction.setTo(to);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public TransactionDto findById(Long id) {
        return transactionConverter.transactionToTransactionDto(
                transactionRepository
                        .findById(id)
                        .orElseThrow(EntityNotFoundException::new)
        );
    }

}
