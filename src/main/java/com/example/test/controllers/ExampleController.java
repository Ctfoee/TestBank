package com.example.test.controllers;

import com.example.test.model.bodies.AccountCredentials;
import com.example.test.model.bodies.OperationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


// API responses are example JSON bodies for registration and all financial operation requests
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping("/userCredentials")
    public ResponseEntity<AccountCredentials> getRegistrationContextExample() {
        AccountCredentials accountCredentials = new AccountCredentials();
        accountCredentials.setBeneficiaryName("Example Beneficiary name");
        accountCredentials.setPin(1234);
        return new ResponseEntity<>(accountCredentials, HttpStatus.OK);
    }


    // idFrom not required: included in @PathVariable for each request it needed
    @GetMapping("/operationContext")
    public ResponseEntity<OperationContext> getOperationContextExample() {
        OperationContext operationContext = new OperationContext();
        operationContext.setPin(1234);
        operationContext.setAmount(BigDecimal.TEN);
        return new ResponseEntity<>(operationContext, HttpStatus.OK);
    }

}
