package com.berktas.bank.controller.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountRequest extends BaseAccountRequest{

    private String id;
}
