package com.fortnight.controllers.web.requests;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
public class WithdrawRequest {

    private String correlation;
    @DecimalMin(value = "0")
    private BigDecimal amount;
}
