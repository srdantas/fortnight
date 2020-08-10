package com.fortnight.domains;

import lombok.Data;

@Data
public class Transfer {

    private Transaction transaction;
    private String debtor;
    private String creditor;
}
