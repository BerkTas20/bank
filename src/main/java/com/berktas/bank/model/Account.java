package com.berktas.bank.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Account implements Serializable {

    @Id
    private String id;

    private String customerId;
    private Double balance;
    private City city;
    private Currency currency;

}
