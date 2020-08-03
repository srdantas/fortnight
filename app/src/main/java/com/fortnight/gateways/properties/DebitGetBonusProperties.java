package com.fortnight.gateways.properties;

import com.fortnight.gateways.DebitGetBonusGateway;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("operations.debit")
public class DebitGetBonusProperties implements DebitGetBonusGateway {

    private double bonus;

    @Override
    public double getBonus() {
        return this.bonus;
    }
}
