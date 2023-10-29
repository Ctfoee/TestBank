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
public class OperationContext {

    private Integer pin;

    // idFrom not required: included in @PathVariable for each request it needed

    private Long idTo;

    private BigDecimal amount;
}
