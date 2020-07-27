package com.fortnight.controllers.web.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceResponse {

    private String name;
    private BigDecimal amount;
}
