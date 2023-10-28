package com.example.test.model.entity;

import com.example.test.exceptions.ConstraintsException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts", schema = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "beneficiaryName", nullable = false)
    private String beneficiaryName;

    @Column(nullable = false)
    private Integer pin;

    @Column
    private BigDecimal balance;

    @OneToMany(mappedBy = "from",
            cascade = CascadeType.REMOVE)
    private List<Transaction> transactionsFrom;

    @OneToMany(mappedBy = "to",
            cascade = CascadeType.REMOVE)
    private List<Transaction> transactionsTo;

    @PrePersist
    private void checkingConstraints() {
        if (this.beneficiaryName == null) {
            throw new ConstraintsException("Beneficiary name can not be null on account create");
        }
        if (this.pin == null) {
            throw new ConstraintsException("PIN name can not be null on account create");
        }
        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", beneficiaryName='" + beneficiaryName + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    public void setTransactionsFrom(List<Transaction> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    public List<Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(List<Transaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }
}