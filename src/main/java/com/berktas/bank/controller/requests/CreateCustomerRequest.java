package com.berktas.bank.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//apinin parametresi için oluşturuldu
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest extends BaseCustomerRequest{
    private String id;
}
