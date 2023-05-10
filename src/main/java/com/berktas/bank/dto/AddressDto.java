package com.berktas.bank.dto;

import com.berktas.bank.model.City;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AddressDto {
    private String id;
    private City city;
    private String postCode;
}
