package com.berktas.bank.dto;
//hicbirzama veritabanımızdaki bilgileri isteğe cevaben geri dönmeyiz. Ara class olarak dto oluştururuz

import com.berktas.bank.model.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {

    private String id;
    private String name;
    private Integer dateOfBirth;
    private CityDto city;
    private Address address;
}
