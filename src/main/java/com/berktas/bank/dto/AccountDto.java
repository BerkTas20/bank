package com.berktas.bank.dto;

import com.berktas.bank.model.Currency;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder //builder komutunu kullanarak dtoyu convert edebiliriz
public class AccountDto  {

    private String id;
    private String customerId;
    private Double balance;
    private Currency currency;
}
