package com.example.test.controllers;

import com.example.test.model.bodies.RegistrationContext;
import com.example.test.model.bodies.TransferContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


// API responses contains example JSON bodies for registration and all financial operation requests
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping("/registrationContext")
    public ResponseEntity<RegistrationContext> getRegistrationContextExample() {
        RegistrationContext registrationContext = new RegistrationContext();
        registrationContext.setBeneficiaryName("Example Beneficiary name");
        registrationContext.setPin(1234);
        return new ResponseEntity<>(registrationContext, HttpStatus.OK);
    }

    @GetMapping("/transferContext")
    public ResponseEntity<TransferContext> getTransferContextExample() {
        TransferContext transferContext = new TransferContext();
        transferContext.setPin(1234);
        transferContext.setAmount(BigDecimal.TEN);
        return new ResponseEntity<>(transferContext, HttpStatus.OK);
    }

}
