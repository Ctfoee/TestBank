package com.example.test.converters;

import com.example.test.model.dto.AccountDto;
import com.example.test.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public abstract class AccountConverter {

    public abstract AccountDto accountToAccountDto (Account account);

    public abstract Account accountDtoToAccount (AccountDto accountDto);

}
