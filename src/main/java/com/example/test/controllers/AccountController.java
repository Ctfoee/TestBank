package com.example.test.controllers;

import com.example.test.exceptions.NotEnoughBalanceException;
import com.example.test.model.bodies.RegistrationContext;
import com.example.test.model.bodies.TransferContext;
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
    public ResponseEntity<HttpStatus> createNew(@RequestBody RegistrationContext registrationContext) {
        String name = registrationContext.getBeneficiaryName();
        Integer pin = registrationContext.getPin();
        accountService.createAccount(name, pin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<HttpStatus> deposit(@PathVariable Long id,
                                              @RequestBody TransferContext transferContext) {
        accountService.deposit(accountService.findById(id), transferContext.getAmount());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<HttpStatus> withdraw(@PathVariable Long id,
                                               @RequestBody TransferContext transferContext) {
        AccountDto account = accountService.findById(id);
        int pin = transferContext.getPin();
        BigDecimal outcome = transferContext.getAmount();

        if (account.getPin() == pin) {
            try {
                accountService.withdraw(account, outcome);
            } catch (NotEnoughBalanceException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("/{id}/transfer")
    public ResponseEntity<HttpStatus> transfer(@PathVariable Long id,
                                               @RequestBody TransferContext transferContext) {
        AccountDto from = accountService.findById(id);
        AccountDto to = accountService.findById(transferContext.getIdTo());

        int pin = transferContext.getPin();
        BigDecimal amount = transferContext.getAmount();

        if (from.getPin() == pin) {
            try {
                accountService.transfer(from, to, amount);
            } catch (NotEnoughBalanceException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/transferContextExample")
    public ResponseEntity<TransferContext> getTransferContextExample() {
        TransferContext transferContext = new TransferContext();
        transferContext.setPin(1234);
        transferContext.setAmount(BigDecimal.TEN);
        return new ResponseEntity<>(transferContext, HttpStatus.OK);
    }

    @GetMapping("/registrationContextExample")
    public ResponseEntity<RegistrationContext> getRegistrationContextExample() {
        RegistrationContext registrationContext = new RegistrationContext();
        registrationContext.setBeneficiaryName("Example Beneficiary name");
        registrationContext.setPin(1234);
        return new ResponseEntity<>(registrationContext, HttpStatus.OK);
    }
}
