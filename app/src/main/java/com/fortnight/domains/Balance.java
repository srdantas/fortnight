package com.fortnight.domains;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Balance {

    private BigDecimal amount = BigDecimal.ZERO;
}
