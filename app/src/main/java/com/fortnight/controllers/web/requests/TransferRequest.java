package com.fortnight.controllers.web.requests;

import lombok.Data;

@Data
public class TransferRequest {

    private String correlation;
    private CreditorRequest creditor;
}
