package com.berktas.bank.mapper;

import com.berktas.bank.dto.AccountDto;
import com.berktas.bank.model.Account;
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
