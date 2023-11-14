package com.example.test.initDb;

import com.example.test.model.dto.AccountDto;
import com.example.test.service.abstracts.AccountService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InitDatabase {

    private final AccountService accountService;

    public InitDatabase(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostConstruct
    private void initDb() {
//        initAccounts();
//        initTransactions();
    }

    private void initAccounts() {
        AccountDto account1 = new AccountDto();
        account1.setBeneficiaryName("Beneficiary1");
        account1.setPin(1111);

        AccountDto account2 = new AccountDto();
        account2.setBeneficiaryName("Beneficiary2");
        account2.setPin(2222);

        accountService.createAccount(account1);
        accountService.createAccount(account2);
    }

    private void initTransactions() {
        AccountDto account1 = new AccountDto();
        account1.setId(1L);
        account1.setPin(1111);

        AccountDto account2 = new AccountDto();
        account2.setId(2L);
        account2.setPin(2222);

        accountService.deposit(account1, BigDecimal.valueOf(100000));
        accountService.deposit(account2, BigDecimal.valueOf(50000));
        accountService.withdraw(account1, BigDecimal.valueOf(65400));
        accountService.transfer(account2, account1, BigDecimal.valueOf(1345));
        accountService.transfer(account1, account2, BigDecimal.valueOf(13452));
        accountService.transfer(account2, account1, BigDecimal.valueOf(2345));
        accountService.transfer(account1, account2, BigDecimal.valueOf(7486));
    }
}
