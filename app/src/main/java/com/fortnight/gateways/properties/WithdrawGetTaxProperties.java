package com.fortnight.gateways.properties;

import com.fortnight.gateways.WithdrawGetTaxGateway;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("operations.withdraw")
public class WithdrawGetTaxProperties implements WithdrawGetTaxGateway {

    private double tax;

    @Override
    public double getTax() {
        return this.tax;
    }
}
