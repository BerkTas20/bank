package com.bank.bank.dto;

import com.bank.bank.model.Account;
import org.springframework.stereotype.Component;

@Component //serviste enjekte etmek için
public class AccountDtoConverter {

    public AccountDto convert(Account account){
        return AccountDto.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .customerId(account.getCustomerId())
                .build();
    }
}
