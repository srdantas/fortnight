package com.fortnight.controllers.web.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerResponse {

    private String name;
    private BigDecimal balance;
}
