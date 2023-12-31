package com.example.test.service.impl;

import com.example.test.converters.AccountConverter;
import com.example.test.converters.TransactionConverter;
import com.example.test.exceptions.NotEnoughBalanceException;
import com.example.test.exceptions.WrongPinException;
import com.example.test.model.dto.AccountDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.example.test.model.entity.Account;
import com.example.test.model.entity.Transaction;
import com.example.test.model.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import com.example.test.repository.AccountRepository;
import com.example.test.service.abstracts.AccountService;
import com.example.test.service.abstracts.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountConverter accountConverter;

    private final TransactionConverter transactionConverter;

    private final TransactionService transactionService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountConverter accountConverter,
                              TransactionService transactionService,
                              TransactionConverter transactionConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
        this.transactionService = transactionService;
        this.transactionConverter = transactionConverter;
    }

    @Override
    @Transactional
    public AccountDto findById(Long id) {
        return accountConverter.accountToAccountDto(
                accountRepository
                        .findById(id)
                        .orElseThrow(EntityNotFoundException::new)
        );
    }

    @Override
    @Transactional
    public List<AccountDto> findAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountConverter::accountToAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createAccount(AccountDto accountDto) {
        accountRepository.save(accountConverter.accountDtoToAccount(accountDto));
    }

    @Override
    @Transactional
    public void deposit(AccountDto accountDto, BigDecimal income) {
        Account realAccount = accountRepository.findById(accountDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        realAccount.setBalance(realAccount.getBalance().add(income));

        transactionService.save(
                transactionConverter.transactionToTransactionDto(
                            assembleTransaction(
                                    TransactionType.Deposit,
                                    null,
                                    realAccount,
                                    income)
                ),
                null,
                realAccount
        );
    }

    @Override
    @Transactional
    public void withdraw(AccountDto accountDto, BigDecimal outcome) {
        Account realAccount = accountRepository.findById(accountDto.getId())
                .orElseThrow(EntityNotFoundException::new);

        if (realAccount.getPin() != accountDto.getPin().intValue()) {
            throw new WrongPinException("Wrong PIN");
        }

        if (realAccount.getBalance().compareTo(outcome) > -1) {
            realAccount.setBalance(realAccount.getBalance().subtract(outcome));
        } else {
            throw new NotEnoughBalanceException("Not enough cash to withdraw on account with id=" + accountDto.getId());
        }

        transactionService.save(
                transactionConverter.transactionToTransactionDto(
                        assembleTransaction(
                                TransactionType.Withdraw,
                                realAccount,
                                null,
                                outcome)
                ),
                realAccount,
                null
        );
    }

    @Override
    @Transactional
    public void transfer(AccountDto from, AccountDto to, BigDecimal amount) {
        Account realFrom = accountRepository.findById(from.getId())
                .orElseThrow(EntityNotFoundException::new);
        Account realTo;

        if (realFrom.getPin() != from.getPin().intValue()) {
            throw new WrongPinException("Wrong PIN");
        }

        if (realFrom.getBalance().compareTo(amount) > -1) {
            realTo = accountRepository.findById(to.getId())
                    .orElseThrow(EntityNotFoundException::new);

            realFrom.setBalance(realFrom.getBalance().subtract(amount));
            realTo.setBalance(realTo.getBalance().add(amount));
        } else {
            throw new NotEnoughBalanceException("Not enough cash to transfer on account with id=" + from.getId());
        }

        transactionService.save(
                transactionConverter.transactionToTransactionDto(
                        assembleTransaction(
                                TransactionType.Transfer,
                                realFrom,
                                realTo,
                                amount)
                ),
                realFrom,
                realTo
        );
    }

    private Transaction assembleTransaction(TransactionType transactionType,
                                     @Nullable Account from,
                                     @Nullable Account to,
                                     BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setFrom(from);
        transaction.setTo(to);
        transaction.setAmount(amount);
        return transaction;
    }
}
