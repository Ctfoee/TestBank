package com.example.test.model.entity;

import com.example.test.exceptions.ConstraintsException;
import com.example.test.model.enums.TransactionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions", schema = "Transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_from")
    private Account from;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_to")
    private Account to;

    @Column
    private BigDecimal amount;

    @Column
    private TransactionType transactionType;

    @PrePersist
    private void checkingConstraints() {
        if (this.transactionType == null) {
            throw new ConstraintsException("Transaction type can not be null");
        }
        if (this.from == null && transactionType != TransactionType.Deposit) {
            throw new ConstraintsException("From can not be null if deposit");
        }
        if (this.to == null && transactionType != TransactionType.Withdraw) {
            throw new ConstraintsException("To can not be null if withdraw");
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
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
