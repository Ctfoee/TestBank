package com.example.test.service.impl;


import com.example.test.converters.TransactionConverter;
import com.example.test.model.dto.TransactionDto;
import com.example.test.model.entity.Account;
import com.example.test.model.entity.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionConverter transactionConverter,
                                  EntityManager entityManager) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public List<TransactionDto> findAllAccountTransactions(Long id) {
        return entityManager
                .createQuery("SELECT t FROM Transaction t WHERE t.from.id=:id OR t.to.id=:id", Transaction.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .map(transactionConverter::transactionToTransactionDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(TransactionDto transactionDto, @Nullable Account from, @Nullable Account to) {
        transactionRepository.save(assembleTransaction(transactionDto, from, to));
    }

    @Override
    public TransactionDto findById(Long id) {
        return transactionConverter.transactionToTransactionDto(
                transactionRepository
                        .findById(id)
                        .orElseThrow(EntityNotFoundException::new)
        );
    }

    private Transaction assembleTransaction(TransactionDto transactionDto,
                                            @Nullable Account from,
                                            @Nullable Account to) {
        Transaction transaction = transactionConverter.transactionDtoToTransaction(transactionDto);
        transaction.setFrom(from);
        transaction.setTo(to);
        return transaction;
    }
}
