package com.berktas.bank.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoneyTransferRequest {
    private String fromId;
    private String toId;
    private Double amount;
}
