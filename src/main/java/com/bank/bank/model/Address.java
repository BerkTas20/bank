package com.bank.bank.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false,nullable = false)
    private String id;

    private City city;
    private String postcode;
    private String addressDetails;
}
  //  Java Serializable ne demek?
  //    Java Serialization API sayesinde bir nesnenin birebir kopyasını, Java platformu dışında da depolayabiliriz