package com.fortnight.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Transfer {

    private Transaction transaction;
    private Customer debtor;
    private Customer creditor;
}
