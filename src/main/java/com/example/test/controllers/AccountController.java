package com.example.test.controllers;

import com.example.test.exceptions.NotEnoughBalanceException;
import com.example.test.exceptions.WrongPinException;
import com.example.test.model.bodies.AccountCredentials;
import com.example.test.model.bodies.OperationContext;
import com.example.test.model.dto.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.test.service.abstracts.AccountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
    }

    @PostMapping("/createNew")
    public ResponseEntity<HttpStatus> createNew(@RequestBody AccountCredentials accountCredentials) {
        AccountDto accountDto = new AccountDto();
        accountDto.setBeneficiaryName(accountCredentials.getBeneficiaryName());
        accountDto.setPin(accountCredentials.getPin());
        accountService.createAccount(accountDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/{id}/deposit")
    public ResponseEntity<HttpStatus> deposit(@PathVariable Long id,
                                              @RequestBody OperationContext operationContext) {
        accountService.deposit(accountService.findById(id), operationContext.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<HttpStatus> withdraw(@PathVariable Long id,
                                               @RequestBody OperationContext operationContext) {
        AccountDto account = new AccountDto();
        account.setId(id);
        account.setPin(operationContext.getPin());

        BigDecimal outcome = operationContext.getAmount();

        try {
            accountService.withdraw(account, outcome);
        } catch (WrongPinException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotEnoughBalanceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/transfer")
    public ResponseEntity<HttpStatus> transfer(@PathVariable Long id,
                                               @RequestBody OperationContext operationContext) {
        AccountDto from = new AccountDto();
        from.setId(id);
        from.setPin(operationContext.getPin());

        AccountDto to = new AccountDto();
        to.setId(operationContext.getIdTo());

        BigDecimal amount = operationContext.getAmount();

        try {
            accountService.transfer(from, to, amount);
        } catch (WrongPinException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (NotEnoughBalanceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
