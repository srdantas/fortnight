package com.fortnight.controllers.web.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreditorRequest {

    @NotBlank(message = "creditor document can not be blank")
    public String document;
}
