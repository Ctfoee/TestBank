package com.example.test.converters;

import com.example.test.model.dto.TransactionDto;
import com.example.test.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class TransactionConverter {
    public abstract TransactionDto transactionToTransactionDto (Transaction transaction);
    public abstract Transaction transactionDtoToTransaction (TransactionDto transactionDto);
}
