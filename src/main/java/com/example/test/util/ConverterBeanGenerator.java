package com.example.test.util;

import com.example.test.converters.AccountConverter;
import com.example.test.converters.AccountConverterImpl;
import com.example.test.converters.TransactionConverter;
import com.example.test.converters.TransactionConverterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConverterBeanGenerator {

    @Bean
    public AccountConverter accountConverter() {
        return new AccountConverterImpl();
    }

    @Bean
    public TransactionConverter transactionConverter() {
        return new TransactionConverterImpl();
    }
}
