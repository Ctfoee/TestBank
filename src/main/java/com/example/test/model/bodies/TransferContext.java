package com.example.test.model.bodies;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferContext {

    private Integer pin;

    private Long idTo;

    private BigDecimal amount;
}
