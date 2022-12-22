package com.bank.bank.dto;

import com.bank.bank.model.City;
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
