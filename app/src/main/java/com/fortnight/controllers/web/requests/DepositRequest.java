package com.fortnight.controllers.web.requests;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class DepositRequest {

    @NotBlank(message = "Correlation can not be null")
    private String correlation;
    @DecimalMin(value = "0")
    private BigDecimal amount;
}
