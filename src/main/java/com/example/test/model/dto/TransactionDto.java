package com.example.test.model.dto;

import com.example.test.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;

    private AccountDto from;

    private AccountDto to;

    private BigDecimal amount;

    private TransactionType transactionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountDto getFrom() {
        return from;
    }

    public void setFrom(AccountDto from) {
        this.from = from;
    }

    public AccountDto getTo() {
        return to;
    }

    public void setTo(AccountDto to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
