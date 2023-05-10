package com.berktas.bank.controller.requests;

import com.berktas.bank.model.City;
import com.berktas.bank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAccountRequest {
    private String customerId;
    private Double balance;
    private Currency currency;
    private City city;
}
