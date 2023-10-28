package com.example.test.repository;

import com.example.test.model.entity.Account;
import com.example.test.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
