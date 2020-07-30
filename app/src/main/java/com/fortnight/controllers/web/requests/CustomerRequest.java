package com.fortnight.controllers.web.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomerRequest {

    @NotBlank(message = "Document can not be null")
    private String document;
    @NotBlank(message = "Name can not be null")
    private String name;
}
